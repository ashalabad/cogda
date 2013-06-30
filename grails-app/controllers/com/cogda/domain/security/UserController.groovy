package com.cogda.domain.security

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*
import com.cogda.domain.security.User

/**
 * UserController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a UserService class that will handle the business logic behind the CRUD actions.
 */
@Secured(["hasAnyRole('ROLE_ADMINISTRATOR')"])
class UserController {

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    /**
     * Passes back a JSON list of Users
     *
     * @return
     */
    def dlist() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List userInstanceList = User.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        userInstanceList.each { User user ->
            Map map = [:]
            map.DT_RowId = "row_"+user.id
            map.version = user.version
            map.username = user.username
            map.enabled = user.enabled
            map.accountExpired = user.accountExpired
            map.accountLocked = user.accountLocked
            map.passwordExpired = user.passwordExpired
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, User.count()
        render dataToRender as GSON
        return
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, User.count()
        List users = User.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(users)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        User userInstance = new User(request.GSON)
        if (userInstance.save(flush: true)) {
            respondCreated userInstance
        } else {
            respondUnprocessableEntity userInstance
        }
    }

    def get() {
        def userInstance = User.get(params.id)
        if (userInstance) {
            respondFound userInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def userInstance = User.get(params.id)
        if (!userInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (userInstance.version > params.long('version')) {
                respondConflict(userInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        userInstance.properties = jsonElement

        if (userInstance.save(flush: true)) {
            respondUpdated userInstance
        } else {
            respondUnprocessableEntity userInstance
        }
    }

    def delete() {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            respondNotFound params.id
            return
        }

        try {
            userInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(User userInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userInstance);
        render gsonRetString
    }

    private void respondCreated(User userInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: userInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userInstance);
        render gsonRetString
    }

    private void respondUpdated(User userInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(User userInstance) {
        def responseBody = [:]
        responseBody.errors = userInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(User userInstance) {
        userInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'user.label', default: 'User')] as Object[],
                'Another user has updated this User while you were editing')
        def responseBody = [:]
        responseBody.errors = userInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
        response.status = SC_INTERNAL_SERVER_ERROR  // 500
        render responseBody as GSON
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }

}




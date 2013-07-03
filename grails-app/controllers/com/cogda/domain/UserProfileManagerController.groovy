package com.cogda.domain

import com.cogda.domain.security.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*
/**
 * UserProfileManagerController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class UserProfileManagerController {

    SpringSecurityService springSecurityService
    GsonBuilder gsonBuilder


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static defaultAction = "manage"

    def manage() {
        String username = springSecurityService.currentUser.username
        User userInstance = User.findByUsername(username)
        UserProfile userProfileInstance = UserProfile.findByUser(userInstance)

        if (!userProfileInstance) {
            flash.message = message(code: 'userProfile.not.found')
            userProfileInstance = new UserProfile()
            return
        }

        [userProfileInstance: userProfileInstance]
    }

    def showForm(){
        def userProfileInstance = UserProfile.get(params.id)
        if (userProfileInstance) {
            response.status = SC_OK
            render(template:"userProfileShowForm", model:[userProfileInstance:userProfileInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    def editForm(){
        def userProfileInstance = UserProfile.get(params.id)
        if (userProfileInstance) {
            response.status = SC_OK
            render(template:"userProfileEditForm", model:[userProfileInstance:userProfileInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def userProfileInstance = UserProfile.get(params.id)
        if (!userProfileInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (userProfileInstance.version > params.long('version')) {
                respondConflict(userProfileInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        userProfileInstance.properties = jsonElement

        if (userProfileInstance.save(flush: true)) {
            respondUpdated userProfileInstance
            return
        } else {
            respondUnprocessableEntity userProfileInstance
            return
        }
    }

    private void respondUpdated(UserProfile userProfileInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userProfileInstance);
        render gsonRetString
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfile.label', default: 'User Profile'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }

    private void respondConflict(UserProfile userProfileInstance) {
        userProfileInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'userProfile.label')] as Object[])
        def responseBody = [:]
        responseBody.errors = userProfile.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondUnprocessableEntity(UserProfile userProfileInstance) {
        def responseBody = [:]
        responseBody.errors = userProfileInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }
}

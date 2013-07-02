package com.cogda.domain

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * UserProfileAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UserProfileAddressController {
    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, UserProfileAddress.count()
        List userProfileAddresses = UserProfileAddress.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(userProfileAddresses)
        render jsonTree
        return
    }

    /**
     * Creates a new UserProfileAddress
     * @return JSON
     */
    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        UserProfileAddress userProfileAddressInstance = new UserProfileAddress(request.GSON)
        if (userProfileAddressInstance.save(flush: true)) {
            respondCreated userProfileAddressInstance
        } else {
            respondUnprocessableEntity userProfileAddressInstance
        }
    }

    def get() {
        def userProfileAddressInstance = UserProfileAddress.get(params.id)
        if (userProfileAddressInstance) {
            respondFound userProfileAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def userProfileAddressInstance = UserProfileAddress.get(params.id)
        if (!userProfileAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (userProfileAddressInstance.version > params.long('version')) {
                respondConflict(userProfileAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        userProfileAddressInstance.properties = jsonElement

        if (userProfileAddressInstance.save(flush: true)) {
            respondUpdated userProfileAddressInstance
        } else {
            respondUnprocessableEntity userProfileAddressInstance
        }
    }

    def delete() {
        def userProfileAddressInstance = UserProfileAddress.get(params.id)
        if (!userProfileAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            userProfileAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def show(){
        UserProfileAddress userProfileAddressInstance = UserProfileAddress.get(params.id)
        if(!userProfileAddressInstance){
            respondNotFound params.id
            return
        }

        render(template:"/userProfileAddress/showUserProfileAddress", model:[userProfileAddressInstance: userProfileAddressInstance])
        return

    }

    def form(){
        def userProfileAddressInstance = UserProfileAddress.get(params.id)
        if (userProfileAddressInstance) {
            response.status = SC_OK
            render(template:"userProfileAddressForm", model:[userProfileAddressInstance:userProfileAddressInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    def formAdd(){
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        UserProfileAddress userProfileAddressInstance = new UserProfileAddress(request.GSON)

        if(!userProfileAddressInstance.userProfile){
            respondUserProfileNotFound()
        }

        Map uProfile = [
                id:null,
                userProfile: [id:userProfileAddressInstance.userProfile.id]
        ]

        userProfileAddressInstance.userProfile.discard()
        userProfileAddressInstance.userProfile = null
        userProfileAddressInstance.discard()
        userProfileAddressInstance = null

        response.status = SC_OK
        render(template:"userProfileAddressForm", model:[userProfileAddressInstance:uProfile])
        return
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(UserProfileAddress userProfileAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userProfileAddressInstance);
        render gsonRetString
    }

    private void respondCreated(UserProfileAddress userProfileAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: userProfileAddressInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userProfileAddressInstance);
        render gsonRetString
    }

    private void respondUpdated(UserProfileAddress userProfileAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userProfileAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(UserProfileAddress userProfileAddressInstance) {
        def responseBody = [:]
        responseBody.errors = userProfileAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondUserProfileNotFound(id){
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfile.label', default: 'UserProfile'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfileAddress.label', default: 'UserProfileAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(UserProfileAddress userProfileAddressInstance) {
        userProfileAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'userProfileAddress.label', default: 'UserProfileAddress')] as Object[],
                'Another user has updated this UserProfileAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = userProfileAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'userProfileAddress.label', default: 'UserProfileAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'userProfileAddress.label', default: 'UserProfileAddress'), id])
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

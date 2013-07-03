package com.cogda.domain

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

import javax.annotation.PostConstruct

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * UserProfileEmailAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class UserProfileEmailAddressController {

    GsonBuilder gsonBuilder

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        UserProfileEmailAddress userProfileEmailAddressInstance = new UserProfileEmailAddress(request.GSON)
        if (userProfileEmailAddressInstance.save(flush: true)) {
            respondCreated userProfileEmailAddressInstance
        } else {
            respondUnprocessableEntity userProfileEmailAddressInstance
        }
    }

    def get() {
        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (userProfileEmailAddressInstance) {
            respondFound userProfileEmailAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (!userProfileEmailAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (userProfileEmailAddressInstance.version > params.long('version')) {
                respondConflict(userProfileEmailAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        userProfileEmailAddressInstance.properties = jsonElement

        if (userProfileEmailAddressInstance.save(flush: true)) {
            respondUpdated userProfileEmailAddressInstance
        } else {
            respondUnprocessableEntity userProfileEmailAddressInstance
        }
    }

    def delete() {
        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (!userProfileEmailAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            userProfileEmailAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def formAdd(){
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        UserProfileEmailAddress userProfileEmailAddressInstance = new UserProfileEmailAddress(request.GSON)

        if(!userProfileEmailAddressInstance.userProfile){
            respondUserProfileNotFound()
        }

        Map uProfile = [
                id:null,
                userProfile: [id:userProfileEmailAddressInstance.userProfile.id]
        ]

        userProfileEmailAddressInstance.userProfile.discard()
        userProfileEmailAddressInstance.userProfile = null
        userProfileEmailAddressInstance.discard()
        userProfileEmailAddressInstance = null

        response.status = SC_OK
        render(template:"userProfileEmailAddressForm", model:[userProfileEmailAddressInstance:uProfile])
        return
    }

    def form(){
        def userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if (userProfileEmailAddressInstance) {
            response.status = SC_OK
            render(template:"userProfileEmailAddressForm", model:[userProfileEmailAddressInstance:userProfileEmailAddressInstance])
            return
        } else {
            respondNotFound params.id
        }
    }


    def show(){
        UserProfileEmailAddress userProfileEmailAddressInstance = UserProfileEmailAddress.get(params.id)
        if(!userProfileEmailAddressInstance){
            respondNotFound params.id
            return
        }

        render(template:"/userProfileEmailAddress/showUserProfileEmailAddress", model:[userProfileEmailAddressInstance: userProfileEmailAddressInstance])
        return
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(UserProfileEmailAddress userProfileEmailAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userProfileEmailAddressInstance);
        render gsonRetString
    }

    private void respondCreated(UserProfileEmailAddress userProfileEmailAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: userProfileEmailAddressInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(userProfileEmailAddressInstance)
        render gsonRetString
    }

    private void respondUpdated(UserProfileEmailAddress userProfileEmailAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(userProfileEmailAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(UserProfileEmailAddress userProfileEmailAddressInstance) {
        def responseBody = [:]
        responseBody.errors = userProfileEmailAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfileEmailAddress.label', default: 'UserProfileEmailAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(UserProfileEmailAddress userProfileEmailAddressInstance) {
        userProfileEmailAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'userProfileEmailAddress.label', default: 'UserProfileEmailAddress')] as Object[],
                'Another user has updated this UserProfileEmailAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = userProfileEmailAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'userProfileEmailAddress.label', default: 'UserProfileEmailAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'userProfileEmailAddress.label', default: 'UserProfileEmailAddress'), id])
        response.status = SC_INTERNAL_SERVER_ERROR  // 500
        render responseBody as GSON
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }

    private void respondUserProfileNotFound(id){
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfile.label', default: 'UserProfile'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }
}

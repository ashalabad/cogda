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
 * UserProfilePhoneNumberController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class UserProfilePhoneNumberController {

    GsonBuilder gsonBuilder

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        UserProfilePhoneNumber userProfilePhoneNumberInstance = new UserProfilePhoneNumber(request.GSON)
        if (userProfilePhoneNumberInstance.save(flush: true)) {
            respondCreated userProfilePhoneNumberInstance
        } else {
            respondUnprocessableEntity userProfilePhoneNumberInstance
        }
    }

    def get() {
        def userProfilePhoneNumberInstance = UserProfilePhoneNumber.get(params.id)
        if (userProfilePhoneNumberInstance) {
            respondFound userProfilePhoneNumberInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def userProfilePhoneNumberInstance = UserProfilePhoneNumber.get(params.id)
        if (!userProfilePhoneNumberInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (userProfilePhoneNumberInstance.version > params.long('version')) {
                respondConflict(userProfilePhoneNumberInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        userProfilePhoneNumberInstance.properties = jsonElement

        if (userProfilePhoneNumberInstance.save(flush: true)) {
            respondUpdated userProfilePhoneNumberInstance
        } else {
            respondUnprocessableEntity userProfilePhoneNumberInstance
        }
    }

    def delete() {
        def userProfilePhoneNumberInstance = UserProfilePhoneNumber.get(params.id)
        if (!userProfilePhoneNumberInstance) {
            respondNotFound params.id
            return
        }

        try {
            userProfilePhoneNumberInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(UserProfilePhoneNumber userProfilePhoneNumberInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(userProfilePhoneNumberInstance);
        render gsonRetString
    }

    private void respondCreated(UserProfilePhoneNumber userProfilePhoneNumberInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: userProfilePhoneNumberInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(userProfilePhoneNumberInstance)
        render gsonRetString
    }

    private void respondUpdated(UserProfilePhoneNumber userProfilePhoneNumberInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(userProfilePhoneNumberInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(UserProfilePhoneNumber userProfilePhoneNumberInstance) {
        def responseBody = [:]
        responseBody.errors = userProfilePhoneNumberInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'userProfilePhoneNumber.label', default: 'UserProfilePhoneNumber'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(UserProfilePhoneNumber userProfilePhoneNumberInstance) {
        userProfilePhoneNumberInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'userProfilePhoneNumber.label', default: 'UserProfilePhoneNumber')] as Object[],
                'Another user has updated this UserProfilePhoneNumber while you were editing')
        def responseBody = [:]
        responseBody.errors = userProfilePhoneNumberInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'userProfilePhoneNumber.label', default: 'UserProfilePhoneNumber'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'userProfilePhoneNumber.label', default: 'UserProfilePhoneNumber'), id])
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
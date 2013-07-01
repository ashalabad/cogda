package com.cogda.domain

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * ContactPhoneNumberController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a ContactPhoneNumberService class that will handle the business logic behind the CRUD actions.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class ContactPhoneNumberController {

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, ContactPhoneNumber.count()
        List contactPhoneNumbers = ContactPhoneNumber.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(contactPhoneNumbers)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        ContactPhoneNumber contactPhoneNumberInstance = new ContactPhoneNumber(request.GSON)
        if (contactPhoneNumberInstance.save(flush: true)) {
            respondCreated contactPhoneNumberInstance
        } else {
            respondUnprocessableEntity contactPhoneNumberInstance
        }
    }

    def get() {
        def contactPhoneNumberInstance = ContactPhoneNumber.get(params.id)
        if (contactPhoneNumberInstance) {
            respondFound contactPhoneNumberInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def contactPhoneNumberInstance = ContactPhoneNumber.get(params.id)
        if (!contactPhoneNumberInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (contactPhoneNumberInstance.version > params.long('version')) {
                respondConflict(contactPhoneNumberInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        contactPhoneNumberInstance.properties = jsonElement

        if (contactPhoneNumberInstance.save(flush: true)) {
            respondUpdated contactPhoneNumberInstance
        } else {
            respondUnprocessableEntity contactPhoneNumberInstance
        }
    }

    def delete() {
        def contactPhoneNumberInstance = ContactPhoneNumber.get(params.id)
        if (!contactPhoneNumberInstance) {
            respondNotFound params.id
            return
        }

        try {
            contactPhoneNumberInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(ContactPhoneNumber contactPhoneNumberInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactPhoneNumberInstance);
        render gsonRetString
    }

    private void respondCreated(ContactPhoneNumber contactPhoneNumberInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: contactPhoneNumberInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(contactPhoneNumberInstance);
        render gsonRetString
    }

    private void respondUpdated(ContactPhoneNumber contactPhoneNumberInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(contactPhoneNumberInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(ContactPhoneNumber contactPhoneNumberInstance) {
        def responseBody = [:]
        responseBody.errors = contactPhoneNumberInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'contactPhoneNumber.label', default: 'ContactPhoneNumber'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(ContactPhoneNumber contactPhoneNumberInstance) {
        contactPhoneNumberInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'contactPhoneNumber.label', default: 'ContactPhoneNumber')] as Object[],
                'Another user has updated this ContactPhoneNumber while you were editing')
        def responseBody = [:]
        responseBody.errors = contactPhoneNumberInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'contactPhoneNumber.label', default: 'ContactPhoneNumber'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'contactPhoneNumber.label', default: 'ContactPhoneNumber'), id])
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



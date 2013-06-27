package com.cogda.domain

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * ContactEmailAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a ContactEmailAddressService class that will handle the business logic behind the CRUD actions.
 */
class ContactEmailAddressController {

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, ContactEmailAddress.count()
        List contactEmailAddresses = ContactEmailAddress.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(contactEmailAddresses)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        ContactEmailAddress contactEmailAddressInstance = new ContactEmailAddress(request.GSON)
        if (contactEmailAddressInstance.save(flush: true)) {
            respondCreated contactEmailAddressInstance
        } else {
            respondUnprocessableEntity contactEmailAddressInstance
        }
    }

    def get() {
        def contactEmailAddressInstance = ContactEmailAddress.get(params.id)
        if (contactEmailAddressInstance) {
            respondFound contactEmailAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def contactEmailAddressInstance = ContactEmailAddress.get(params.id)
        if (!contactEmailAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (contactEmailAddressInstance.version > params.long('version')) {
                respondConflict(contactEmailAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        contactEmailAddressInstance.properties = jsonElement

        if (contactEmailAddressInstance.save(flush: true)) {
            respondUpdated contactEmailAddressInstance
        } else {
            respondUnprocessableEntity contactEmailAddressInstance
        }
    }

    def delete() {
        def contactEmailAddressInstance = ContactEmailAddress.get(params.id)
        if (!contactEmailAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            contactEmailAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(ContactEmailAddress contactEmailAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactEmailAddressInstance);
        render gsonRetString
    }

    private void respondCreated(ContactEmailAddress contactEmailAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: contactEmailAddressInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(contactEmailAddressInstance);
        render gsonRetString
    }

    private void respondUpdated(ContactEmailAddress contactEmailAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(contactEmailAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(ContactEmailAddress contactEmailAddressInstance) {
        def responseBody = [:]
        responseBody.errors = contactEmailAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'contactEmailAddress.label', default: 'ContactEmailAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(ContactEmailAddress contactEmailAddressInstance) {
        contactEmailAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'contactEmailAddress.label', default: 'ContactEmailAddress')] as Object[],
                'Another user has updated this ContactEmailAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = contactEmailAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'contactEmailAddress.label', default: 'ContactEmailAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'contactEmailAddress.label', default: 'ContactEmailAddress'), id])
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



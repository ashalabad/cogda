package com.cogda.domain

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException

import javax.annotation.PostConstruct

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * ContactAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a ContactAddressService class that will handle the business logic behind the CRUD actions.
 */
class ContactAddressController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, ContactAddress.count()
        List contactAddresses = ContactAddress.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(contactAddresses)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        ContactAddress contactAddressInstance = new ContactAddress(request.GSON)
        if (contactAddressInstance.save(flush: true)) {
            respondCreated contactAddressInstance
        } else {
            respondUnprocessableEntity contactAddressInstance
        }
    }

    def get() {
        def contactAddressInstance = ContactAddress.get(params.id)
        if (contactAddressInstance) {
            respondFound contactAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def contactAddressInstance = ContactAddress.get(params.id)
        if (!contactAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (contactAddressInstance.version > params.long('version')) {
                respondConflict(contactAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        contactAddressInstance.properties = jsonElement

        if (contactAddressInstance.save(flush: true)) {
            respondUpdated contactAddressInstance
        } else {
            respondUnprocessableEntity contactAddressInstance
        }
    }

    def delete() {
        def contactAddressInstance = ContactAddress.get(params.id)
        if (!contactAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            contactAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(ContactAddress contactAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactAddressInstance);
        render gsonRetString
    }

    private void respondCreated(ContactAddress contactAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: contactAddressInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(contactAddressInstance)
        render gsonRetString
    }

    private void respondUpdated(ContactAddress contactAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(contactAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(ContactAddress contactAddressInstance) {
        def responseBody = [:]
        responseBody.errors = contactAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'contactAddress.label', default: 'ContactAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(ContactAddress contactAddressInstance) {
        contactAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'contactAddress.label', default: 'ContactAddress')] as Object[],
                'Another user has updated this ContactAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = contactAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'contactAddress.label', default: 'ContactAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'contactAddress.label', default: 'ContactAddress'), id])
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


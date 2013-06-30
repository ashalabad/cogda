package com.cogda.domain.lead.contact

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.cogda.multitenant.LeadContactAddress
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException

import static grails.plugin.gson.http.HttpConstants.SC_UNPROCESSABLE_ENTITY
import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION

/**
 * LeadContactAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LeadContactAddressController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LeadContactAddress.count()
        List leadContactAddresses = LeadContactAddress.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(leadContactAddresses)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LeadContactAddress leadContactAddressInstance = new LeadContactAddress(request.GSON)
        if (leadContactAddressInstance.save(flush: true)) {
            respondCreated leadContactAddressInstance
        } else {
            respondUnprocessableEntity leadContactAddressInstance
        }
    }

    def get() {
        def leadContactAddressInstance = LeadContactAddress.get(params.id)
        if (leadContactAddressInstance) {
            respondFound leadContactAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadContactAddressInstance = LeadContactAddress.get(params.id)
        if (!leadContactAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadContactAddressInstance.version > params.long('version')) {
                respondConflict(leadContactAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadContactAddressInstance.properties = jsonElement

        if (leadContactAddressInstance.save(flush: true)) {
            respondUpdated leadContactAddressInstance
        } else {
            respondUnprocessableEntity leadContactAddressInstance
        }
    }

    def delete() {
        def leadContactAddressInstance = LeadContactAddress.get(params.id)
        if (!leadContactAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            leadContactAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(LeadContactAddress leadContactAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadContactAddressInstance);
        render gsonRetString
    }

    private void respondCreated(LeadContactAddress leadContactAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: leadContactAddressInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(leadContactAddressInstance)
        render gsonRetString
    }

    private void respondUpdated(LeadContactAddress leadContactAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(leadContactAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(LeadContactAddress leadContactAddressInstance) {
        def responseBody = [:]
        responseBody.errors = leadContactAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'leadContactAddress.label', default: 'LeadContactAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(LeadContactAddress leadContactAddressInstance) {
        leadContactAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'leadContactAddress.label', default: 'LeadContactAddress')] as Object[],
                'Another user has updated this LeadContactAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = leadContactAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'leadContactAddress.label', default: 'LeadContactAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadContactAddress.label', default: 'LeadContactAddress'), id])
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


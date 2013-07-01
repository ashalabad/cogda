package com.cogda.domain.lead.contact

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.cogda.multitenant.LeadContactEmailAddress
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
 * LeadContactEmailAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadContactEmailAddressController {

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LeadContactEmailAddress.count()
        List leadContactEmailAddresses = LeadContactEmailAddress.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(leadContactEmailAddresses)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LeadContactEmailAddress leadContactEmailAddressInstance = new LeadContactEmailAddress(request.GSON)
        if (leadContactEmailAddressInstance.save(flush: true)) {
            respondCreated leadContactEmailAddressInstance
        } else {
            respondUnprocessableEntity leadContactEmailAddressInstance
        }
    }

    def get() {
        def leadContactEmailAddressInstance = LeadContactEmailAddress.get(params.id)
        if (leadContactEmailAddressInstance) {
            respondFound leadContactEmailAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadContactEmailAddressInstance = LeadContactEmailAddress.get(params.id)
        if (!leadContactEmailAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadContactEmailAddressInstance.version > params.long('version')) {
                respondConflict(leadContactEmailAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadContactEmailAddressInstance.properties = jsonElement

        if (leadContactEmailAddressInstance.save(flush: true)) {
            respondUpdated leadContactEmailAddressInstance
        } else {
            respondUnprocessableEntity leadContactEmailAddressInstance
        }
    }

    def delete() {
        def leadContactEmailAddressInstance = LeadContactEmailAddress.get(params.id)
        if (!leadContactEmailAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            leadContactEmailAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(LeadContactEmailAddress leadContactEmailAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadContactEmailAddressInstance);
        render gsonRetString
    }

    private void respondCreated(LeadContactEmailAddress leadContactEmailAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: leadContactEmailAddressInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(leadContactEmailAddressInstance);
        render gsonRetString
    }

    private void respondUpdated(LeadContactEmailAddress leadContactEmailAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(leadContactEmailAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(LeadContactEmailAddress leadContactEmailAddressInstance) {
        def responseBody = [:]
        responseBody.errors = leadContactEmailAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'leadContactEmailAddress.label', default: 'LeadContactEmailAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(LeadContactEmailAddress leadContactEmailAddressInstance) {
        leadContactEmailAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'leadContactEmailAddress.label', default: 'LeadContactEmailAddress')] as Object[],
                'Another user has updated this LeadContactEmailAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = leadContactEmailAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'leadContactEmailAddress.label', default: 'LeadContactEmailAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadContactEmailAddress.label', default: 'LeadContactEmailAddress'), id])
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



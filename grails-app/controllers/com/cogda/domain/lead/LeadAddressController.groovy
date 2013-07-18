package com.cogda.domain.lead

import com.cogda.multitenant.LeadAddress
import com.cogda.multitenant.LeadService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.XML
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

import static grails.plugin.gson.http.HttpConstants.SC_UNPROCESSABLE_ENTITY
import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION

/**
 * LeadAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadAddressController {

    GsonBuilder gsonBuilder

    LeadService leadService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LeadAddress.count()
        List leadAddresses = LeadAddress.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(leadAddresses)
        render jsonTree
        return
    }

    def create() {
        LeadAddress leadAddressInstance = new LeadAddress()

        withFormat {
            json { render leadAddressInstance as GSON }
            xml { render leadAddressInstance as XML }
        }

    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LeadAddress leadAddressInstance = new LeadAddress(request.GSON)
        if (leadAddressInstance.save(flush: true)) {
            respondCreated leadAddressInstance
        } else {
            respondUnprocessableEntity leadAddressInstance
        }
    }

    def get() {
        def leadAddressInstance = LeadAddress.get(params.id)
        if (leadAddressInstance) {
            respondFound leadAddressInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadAddressInstance = LeadAddress.get(params.id)
        if (!leadAddressInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadAddressInstance.version > params.long('version')) {
                respondConflict(leadAddressInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadAddressInstance.properties = jsonElement

        if (leadService.saveLeadAddress(leadAddressInstance)) {
            respondUpdated leadAddressInstance
        } else {
            respondUnprocessableEntity leadAddressInstance
        }
    }

    def delete() {
        def leadAddressInstance = LeadAddress.get(params.id)
        if (!leadAddressInstance) {
            respondNotFound params.id
            return
        }

        try {
            leadAddressInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showPartial() {
        render(template:'/lead/leadAddress/partials/showPartial')
    }

    def editPartial() {
        render(template:'/lead/leadAddress/partials/editPartial')
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(LeadAddress leadAddressInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadAddressInstance);
        render gsonRetString
    }

    private void respondCreated(LeadAddress leadAddressInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: leadAddressInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadAddressInstance)
        render gsonRetString
    }

    private void respondUpdated(LeadAddress leadAddressInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadAddressInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(LeadAddress leadAddressInstance) {
        def responseBody = [:]
        responseBody.errors = leadAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'leadAddress.label', default: 'LeadAddress'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(LeadAddress leadAddressInstance) {
        leadAddressInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'leadAddress.label', default: 'LeadAddress')] as Object[],
                'Another user has updated this LeadAddress while you were editing')
        def responseBody = [:]
        responseBody.errors = leadAddressInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'leadAddress.label', default: 'LeadAddress'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadAddress.label', default: 'LeadAddress'), id])
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


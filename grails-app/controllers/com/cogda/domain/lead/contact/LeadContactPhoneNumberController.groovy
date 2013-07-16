package com.cogda.domain.lead.contact

import com.cogda.common.marshallers.JavaUtilSetExclusionStrategy
import com.cogda.multitenant.LeadContactPhoneNumber
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
 * LeadContactPhoneNumberController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a LeadContactPhoneNumberService class that will handle the business logic behind the CRUD actions.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadContactPhoneNumberController {

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LeadContactPhoneNumber.count()
        List leadContactPhoneNumbers = LeadContactPhoneNumber.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(leadContactPhoneNumbers)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LeadContactPhoneNumber leadContactPhoneNumberInstance = new LeadContactPhoneNumber(request.GSON)
        if (leadContactPhoneNumberInstance.save(flush: true)) {
            respondCreated leadContactPhoneNumberInstance
        } else {
            respondUnprocessableEntity leadContactPhoneNumberInstance
        }
    }

    def get() {
        def leadContactPhoneNumberInstance = LeadContactPhoneNumber.get(params.id)
        if (leadContactPhoneNumberInstance) {
            respondFound leadContactPhoneNumberInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadContactPhoneNumberInstance = LeadContactPhoneNumber.get(params.id)
        if (!leadContactPhoneNumberInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadContactPhoneNumberInstance.version > params.long('version')) {
                respondConflict(leadContactPhoneNumberInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadContactPhoneNumberInstance.properties = jsonElement

        if (leadContactPhoneNumberInstance.save(flush: true)) {
            respondUpdated leadContactPhoneNumberInstance
        } else {
            respondUnprocessableEntity leadContactPhoneNumberInstance
        }
    }

    def delete() {
        def leadContactPhoneNumberInstance = LeadContactPhoneNumber.get(params.id)
        if (!leadContactPhoneNumberInstance) {
            respondNotFound params.id
            return
        }

        try {
            leadContactPhoneNumberInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showPartial() {
        render(template:"/lead/leadContactPhoneNumber/partials/showPartial")
    }

    def editPartial() {
        render(template:"/lead/leadContactPhoneNumber/partials/editPartial")
    }

    def indexPartial() {
        render(template: "/lead/leadContactPhoneNumber/partials/indexPartial")
    }

    def addPartial() {
        render(template:"/lead/leadContactPhoneNumber/partials/addPartial")
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(LeadContactPhoneNumber leadContactPhoneNumberInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadContactPhoneNumberInstance);
        render gsonRetString
    }

    private void respondCreated(LeadContactPhoneNumber leadContactPhoneNumberInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: leadContactPhoneNumberInstance.id)
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(leadContactPhoneNumberInstance);
        render gsonRetString
    }

    private void respondUpdated(LeadContactPhoneNumber leadContactPhoneNumberInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.addSerializationExclusionStrategy(new JavaUtilSetExclusionStrategy()).create()
        def gsonRetString = gson.toJsonTree(leadContactPhoneNumberInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(LeadContactPhoneNumber leadContactPhoneNumberInstance) {
        def responseBody = [:]
        responseBody.errors = leadContactPhoneNumberInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'leadContactPhoneNumber.label', default: 'LeadContactPhoneNumber'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(LeadContactPhoneNumber leadContactPhoneNumberInstance) {
        leadContactPhoneNumberInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'leadContactPhoneNumber.label', default: 'LeadContactPhoneNumber')] as Object[],
                'Another user has updated this LeadContactPhoneNumber while you were editing')
        def responseBody = [:]
        responseBody.errors = leadContactPhoneNumberInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'leadContactPhoneNumber.label', default: 'LeadContactPhoneNumber'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadContactPhoneNumber.label', default: 'LeadContactPhoneNumber'), id])
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

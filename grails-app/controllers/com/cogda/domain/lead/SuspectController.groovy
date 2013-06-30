package com.cogda.domain.lead

import com.cogda.BaseController
import com.cogda.common.LeadType
import com.cogda.multitenant.Lead
import com.cogda.multitenant.LeadAddress
import com.cogda.multitenant.LeadContact
import com.cogda.multitenant.LeadContactAddress
import com.cogda.multitenant.LeadContactEmailAddress
import com.cogda.multitenant.LeadContactPhoneNumber
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException

import static grails.plugin.gson.http.HttpConstants.SC_UNPROCESSABLE_ENTITY
import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION

/**
 * SuspectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class SuspectController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    GsonBuilder gsonBuilder
    def errorMessageResolverService

    def index() {
    }

    def detail() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'suspect.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        [suspectInstance: suspectInstance]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        List suspectInstanceList = Lead.list()

        def dataToRender = [:]
        dataToRender.aaData = []
        suspectInstanceList.each { Lead suspect ->
            Map map = [:]
            map.DT_RowId = "row_" + suspect.id
            map.version = suspect.version
            map.clientId = suspect.clientId
            map.businessType = suspect.businessType.description
            map.owner = suspect.ownerName
            map.createdOn = suspect.dateCreated
            map.clientName = suspect.clientName
            map.contactName = suspect.primaryLeadContactName
            map.phoneNumber = suspect.primaryLeadContactPhoneNumber?.phoneNumber
            map.email = suspect.primaryEmailAddress
            map.details = remoteLink([controller: 'suspect', action: 'show', id: suspect.id, onSuccess: 'modalDialogHandler(data)', method: 'GET'], 'Details')
            map.edit = remoteLink([controller: 'suspect', action: 'edit', id: suspect.id, onSuccess: 'modalDialogHandler(data)', method: 'GET'], 'Edit')
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1
        render dataToRender as JSON
        return
    }

    def create() {
        def suspectInstance = new Lead(leadType: LeadType.SUSPECT)
                .addToLeadAddresses(new LeadAddress(primaryAddress: true))
                .addToLeadContacts(new LeadContact(primaryContact: true)
                                    .addToLeadContactAddresses(new LeadContactAddress(primaryAddress: true))
                                    .addToLeadContactEmailAddresses(new LeadContactEmailAddress(primaryEmailAddress: true))
                                    .addToLeadContactPhoneNumbers(new LeadContactPhoneNumber(primaryPhoneNumber: true)));
        [suspectInstance: suspectInstance]
    }

    def save() {
        def suspectInstance = new Lead(params)
        suspectInstance.leadType = LeadType.SUSPECT
        if (!suspectInstance.save(flush: true)) {
            render(view: "create", model: [suspectInstance: suspectInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'suspect.label', default: 'Lead'), suspectInstance.id])
        String redirectLink = generateRedirectLink("suspect", "show", [id: suspectInstance.id])
        redirect(url: redirectLink)
    }

    def show() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'suspect.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        render(template: '/_common/modals/suspect/showSuspect', model: [suspectInstance: suspectInstance])
    }

    def get() {
        def leadInstance = Lead.get(params.id)
        if (leadInstance) {
            respondFound leadInstance
        } else {
            respondNotFound params.id
        }
    }

    def edit() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        render(template: '/_common/modals/suspect/editSuspect', model: [suspectInstance: suspectInstance])
    }

    def update() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {
            respondNotFound(params.id)
        }

        if (params.version != null) {
            if (suspectInstance.version > params.long('version')) {
                respondConflict()
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        suspectInstance.properties = jsonElement

        if (suspectInstance.save(flush: true)) {
            respondUpdated(suspectInstance)
        } else {
            respondUnprocessableEntity(suspectInstance)
        }
    }

    def delete() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {

            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }

        try {
            suspectInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, Lead.count()
        render Lead.listOrderById(params) as GSON
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(Lead leadInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadInstance);
        render gsonRetString
    }

    private void respondCreated(Lead leadInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: leadInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadInstance);
        render gsonRetString
    }

    private void respondUpdated(Lead leadInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(leadInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(Lead leadInstance) {
        def responseBody = [:]
        responseBody.errors = leadInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Lead'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(Lead leadInstance) {
        leadInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'contact.label', default: 'Contact')] as Object[],
                'Another user has updated this Contact while you were editing')
        def responseBody = [:]
        responseBody.errors = leadInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'lead.label', default: 'Lead'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'lead.label', default: 'Lead'), id])
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
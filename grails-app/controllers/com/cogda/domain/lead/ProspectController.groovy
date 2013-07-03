package com.cogda.domain.lead

import com.cogda.BaseController
import com.cogda.common.LeadType
import com.cogda.multitenant.Lead
import com.cogda.multitenant.LeadAddress
import com.cogda.multitenant.LeadContact
import com.cogda.multitenant.LeadContactAddress
import com.cogda.multitenant.LeadContactEmailAddress
import com.cogda.multitenant.LeadContactPhoneNumber
import com.cogda.multitenant.LeadNote
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

import static grails.plugin.gson.http.HttpConstants.SC_UNPROCESSABLE_ENTITY
import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION

/**
 * ProspectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class ProspectController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    GsonBuilder gsonBuilder

    def index() {
    }

    def detail() {
        def prospectInstance = Lead.get(params.id)
        if (!prospectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'prospect.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        [prospectInstance: prospectInstance]
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def query = Lead.where { leadType == LeadType.PROSPECT }
        List prospectInstanceList = query.list()
        def dataToRender = [:]
        dataToRender.aaData = []
        prospectInstanceList.each { Lead prospect ->
            Map map = [:]
            map.DT_RowId = "row_" + prospect.id
            map.version = prospect.version
            map.clientId = prospect.clientId
            map.businessType = prospect.businessType?.description
            map.clientName = prospect.clientName
            map.createdOn = prospect.dateCreated
            map.contactName = prospect.primaryLeadContactName
            map.phoneNumber = prospect.primaryLeadContactPhoneNumber?.phoneNumber
            map.email = prospect.primaryEmailAddress
            map.details = remoteLink([controller: 'prospect', action: 'show', id: prospect.id, onSuccess: 'modalDialogHandler(data)', method: 'GET'], 'Details')
            map.edit = remoteLink([controller: 'prospect', action: 'edit', id: prospect.id, onSuccess: 'modalDialogHandler(data)', method: 'GET'], 'Edit')
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1
        render dataToRender as JSON
        return
    }

    def create() {
        def prospectInstance = new Lead(leadType: LeadType.PROSPECT)
                .addToNotes(new LeadNote())
                .addToLeadAddresses(new LeadAddress(primaryAddress: true))
                .addToLeadContacts(new LeadContact(primaryContact: true)
                .addToLeadContactEmailAddresses(new LeadContactEmailAddress(primaryEmailAddress: true))
                .addToLeadContactPhoneNumbers(new LeadContactPhoneNumber(primaryPhoneNumber: true)));
        [prospectInstance: prospectInstance]
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        Lead prospectInstance = new Lead(request.GSON)
        prospectInstance.leadType = LeadType.PROSPECT
        if (!prospectInstance.save(flush: true)) {
            respondUnprocessableEntity(prospectInstance)
            return
        } else {
            respondCreated prospectInstance
            return
        }
    }

    def show() {
        def prospectInstance = Lead.get(params.id)
        if (!prospectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'prospect.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        render(template: '/_common/modals/prospect/showProspect', model: [prospectInstance: prospectInstance])
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
        def prospectInstance = Lead.get(params.id)
        if (!prospectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        render(template: '/_common/modals/prospect/editProspect', model: [prospectInstance: prospectInstance])
    }

    def update() {
        def prospectInstance = Lead.get(params.id)
        if (!prospectInstance) {
            respondNotFound(params.id)
        }

        if (params.version != null) {
            if (prospectInstance.version > params.long('version')) {
                respondConflict prospectInstance
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        prospectInstance.properties = jsonElement

        if (prospectInstance.save(flush: true)) {
            respondUpdated(prospectInstance)
        } else {
            respondUnprocessableEntity(prospectInstance)
        }
    }

    def delete() {
        def prospectInstance = Lead.get(params.id)
        if (!prospectInstance) {

            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }

        try {
            prospectInstance.delete(flush: true)
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
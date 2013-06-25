package com.cogda.multitenant

import com.cogda.BaseController
import com.cogda.common.LeadType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

    def index() {
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
            map.businessType = suspect.businessType
            map.owner = suspect.ownerName
            map.createdOn = suspect.dateCreated
            if (suspect.account) {
                def primaryContact = suspect.account.accountContacts.find {it.primaryContact}
                map.clientName = suspect.account.accountName
                map.contactName = primaryContact.getFullName()
                map.phoneNumber = primaryContact.getAccountContactPhoneNumbers().find { it.primaryPhoneNumber }
                map.email = primaryContact.getAccountContactEmailAddresses().find { it.primaryEmailAddress }
            } else {
                map.clientName = suspect.clientName
                map.contactName = suspect.getFullName()
                map.phoneNumber = suspect.phoneNumber
                map.email = suspect.emailAddress
            }
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1
        render dataToRender as JSON
        return
    }

    def create() {
        [suspectInstance: new Lead(params)]
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
        redirect(url:redirectLink)
    }

    def show() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'suspect.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }

        [suspectInstance: suspectInstance]
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

        [suspectInstance: suspectInstance]
    }

    def update() {
        def suspectInstance = Lead.get(params.id)
        if (!suspectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (suspectInstance.version > version) {
                suspectInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'suspect.label', default: 'Lead')] as Object[],
                        "Another user has updated this Lead while you were editing")
                render(view: "edit", model: [suspectInstance: suspectInstance])
                return
            }
        }

        suspectInstance.properties = params

        if (!suspectInstance.save(flush: true)) {
            render(view: "edit", model: [suspectInstance: suspectInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'lead.label', default: 'Lead'), suspectInstance.id])
        redirect(action: "show", id: suspectInstance.id)
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

package com.cogda.domain.lead

import com.cogda.BaseController
import com.cogda.common.LeadType
import com.cogda.common.marshallers.HibernateProxyTypeAdapter
import com.cogda.multitenant.Lead
import com.cogda.multitenant.LeadAddress
import com.cogda.multitenant.LeadContact
import com.cogda.multitenant.LeadContactAddress
import com.cogda.multitenant.LeadContactEmailAddress
import com.cogda.multitenant.LeadContactPhoneNumber
import com.cogda.multitenant.LeadNote
import com.cogda.multitenant.LeadService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.hibernate.FetchMode
import org.springframework.dao.DataIntegrityViolationException

import java.lang.reflect.Modifier

import static grails.plugin.gson.http.HttpConstants.SC_UNPROCESSABLE_ENTITY
import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.LOCATION

/**
 * SuspectController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SuspectController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    GsonBuilder gsonBuilder
    LeadService leadService

    def index() {
    }

    def detail() {
//        def suspectInstance = Lead.get(params.id)
        def suspectInstance = Lead.findById(params.id, [fetch: [businessType: "eager"]])
        if (!suspectInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'suspect.label', default: 'Lead'), params.id])
            redirect(action: "list")
            return
        }
        [suspectInstance: suspectInstance]
    }

    def listPartial(){
        render (view:'listPartial')
    }

    def editPartial() {
        render (view:'tabbedEditPartial')
    }

    def createPartial() {
        render (view:'createPartial')
    }

    def showPartial() {
        render (view:'showPartial')
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        response.addIntHeader X_PAGINATION_TOTAL, Lead.countByLeadType(LeadType.SUSPECT) as int
        def criteria = Lead.createCriteria()
        def suspectInstanceList = criteria.list(params) {
            eq('leadType', LeadType.SUSPECT)
        }
        def dataToRender = []
        suspectInstanceList.each { Lead suspect ->
            Map map = [:]
            map.id = suspect.id
            map.version = suspect.version
            map.clientId = suspect.clientId
            map.businessType = suspect.businessType?.description
            map.owner = suspect.clientName
            map.createdOn = suspect.dateCreated
            map.clientName = suspect.clientName
            map.contactName = suspect.primaryLeadContactName
            map.phoneNumber = suspect.primaryLeadContactPhoneNumber?.phoneNumber
            map.email = suspect.primaryEmailAddress
            dataToRender.add(map)
        }
        render dataToRender as GSON
    }

    def create() {
        def suspectInstance = new Lead(leadType: LeadType.SUSPECT)
                .addToLeadNotes(new LeadNote())
                .addToLeadAddresses(new LeadAddress(primaryAddress: true))
                .addToLeadContacts(new LeadContact(primaryContact: true)
                .addToLeadContactEmailAddresses(new LeadContactEmailAddress(primaryEmailAddress: true))
                .addToLeadContactPhoneNumbers(new LeadContactPhoneNumber(primaryPhoneNumber: true)));
        [suspectInstance: suspectInstance]
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        Lead suspectInstance = new Lead(request.GSON)
        suspectInstance.leadType = LeadType.SUSPECT
        if (!suspectInstance.save(flush: true)) {
            respondUnprocessableEntity(suspectInstance)
            return
        } else {
            respondCreated suspectInstance
            return
        }
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
        def leadInstance = leadService.getLead(params.id);
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
        Gson gson = gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create()
        def gsonRetString = gson.toJsonTree(leadInstance);
        render gsonRetString
//        render leadInstance as GSON
    }

    private void respondCreated(Lead leadInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: leadInstance.id)
        Gson gson = gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create()
        def gsonRetString = gson.toJsonTree(leadInstance);
        render gsonRetString
    }

    private void respondUpdated(Lead leadInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create()
        def gsonRetString = gson.toJsonTree(leadInstance);
        render gsonRetString
    }

    private Map getErrorStringsByField(instance){
        Map stringsByField = [:].withDefault { [] }
        for(fieldErrors in instance.errors){
            for(error in fieldErrors.allErrors){
                String message = message(error:error)
                stringsByField[error.field] << message
            }
        }
        return stringsByField
    }

    private void respondUnprocessableEntity(instance) {
        def responseBody = [:]
        responseBody.errors = getErrorStringsByField(instance)
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
        return
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
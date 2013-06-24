package com.cogda.domain

import com.cogda.BaseController
import com.cogda.common.web.AjaxResponseDto
import com.cogda.util.ErrorMessageResolverService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class ContactController extends BaseController{

    ErrorMessageResolverService errorMessageResolverService

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    /**
     * Passes back a JSON list of Contacts
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List contactInstanceList = Contact.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        contactInstanceList.each { Contact contact ->
            Map map = [:]
            map.DT_RowId = "row_"+contact.id
            map.version = contact.version
            map.companyName = contact.companyName
            map.lastName = contact.lastName
            map.firstName = contact.firstName
            map.jobTitle = contact.jobTitle
            map.primaryEmailAddress = contact.primaryEmailAddress
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }

    /**
     * Inserts a new Contact into the database based on the passed in
     * JSON.
     * @return AjaxResponseDto as JSON
     */
    def saveOld() {
        def contactInstance = new Contact(JSON.parse(params.contact))

        contactInstance.save(flush: true)

        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if(contactInstance.hasErrors()){
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(contactInstance)
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.modelObject = contactInstance
        }else{
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.addMessage(message(code:'contact.save.successful'))
            ajaxResponseDto.modelObject = contactInstance
        }

        render ajaxResponseDto as JSON
        return
    }

    /**
     * Retrieves the Contact instance and parses it to JSON.
     * @return AjaxResponseDto as JSON
     */
    def getOld(){
        def contactInstance = Contact.get(params.id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if(!contactInstance){

            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'contact.not.found'))
            ajaxResponseDto.modelObject = null

        }else{

            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.modelObject = contactInstance

        }

        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
        return
    }

    /**
     * Updates an existing Contact
     * @return AjaxResponseDto as JSON
     */
    def updateOld() {

        def jsonProperties = JSON.parse(params.contact)

        Contact contactInstance = Contact.get(params.id)

        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        println "contactInstance was not found? " + !contactInstance
        if (!contactInstance) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'contact.not.found'))
            ajaxResponseDto.modelObject = null
            render ajaxResponseDto as JSON
            return
        }

        if (jsonProperties.version) {
            def version = jsonProperties.version.toLong()
            if (contactInstance.version > version) {
                ajaxResponseDto.errors.put("version", message(code:"default.optimistic.locking.failure",
                        args: [message(code: 'contact.label', default: 'Contact')] as Object[]))

                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.modelObject = null
                render ajaxResponseDto as JSON
                return
            }
        }

        // copy the properties from jsonProperties into the contactInstance

        println contactInstance

        contactInstance.save()

        if(contactInstance.hasErrors()){
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(contactInstance)
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.modelObject = contactInstance
        }else{
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.addMessage(message(code:'contact.save.successful'))
            ajaxResponseDto.modelObject = contactInstance
        }

        render ajaxResponseDto as JSON
        return
    }

    /**
     * Deletes a Contact
     * @return AjaxResponseDto as JSON
     */
    def deleteOld() {
        def jsonProperties = JSON.parse(params.contact)

        Contact contactInstance = Contact.get(jsonProperties.id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()

        if (!contactInstance) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'contact.not.found'))
            ajaxResponseDto.modelObject = null
        }
        try {
            contactInstance.delete(flush: true)
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.addMessage(message(code:'contact.delete.successful'))
        }
        catch (DataIntegrityViolationException e) {
            ajaxResponseDto.errors.put("id", message(code: 'default.not.deleted.message', args: [message(code: 'contact.label', default: 'Contact')]))
            ajaxResponseDto.success = Boolean.FALSE
        }

        render ajaxResponseDto as JSON
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def contactInstance = new Contact(request.GSON)
        if (contactInstance.save(flush: true)) {
            respondCreated contactInstance
        } else {
            respondUnprocessableEntity contactInstance
        }
    }

    def get() {
        def contactInstance = Contact.get(params.id)
        if (contactInstance) {
            respondFound contactInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (contactInstance.version > params.long('version')) {
                respondConflict(contactInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        contactInstance.properties = jsonElement

        if (contactInstance.save(flush: true)) {
            respondUpdated contactInstance
        } else {
            respondUnprocessableEntity contactInstance
        }
    }

    def delete() {
        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            respondNotFound params.id
            return
        }

        try {
            contactInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showHtml(){
        def contactInstance = Contact.get(params.id)
        if (contactInstance) {
            response.status = SC_OK

            render(template:"/_common/modals/contactModal", model:[contactInstance:contactInstance])
            return
        } else {
            respondNotFound params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(Contact contactInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
    }

    private void respondCreated(Contact contactInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: contactInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
    }

    private void respondUpdated(Contact contactInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(Contact contactInstance) {
        def responseBody = [:]
        responseBody.errors = contactInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(Contact contactInstance) {
        contactInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'contact.label', default: 'Contact')] as Object[],
                'Another user has updated this Contact while you were editing')
        def responseBody = [:]
        responseBody.errors = contactInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
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

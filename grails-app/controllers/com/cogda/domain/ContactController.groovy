package com.cogda.domain

import com.cogda.BaseController
import com.cogda.common.web.AjaxResponseDto
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class ContactController extends BaseController{

    ErrorMessageResolverService errorMessageResolverService

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
            map.id = contact.id
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
    def save() {
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
    def get(){
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
        render ajaxResponseDto as JSON
        return
    }

    def update() {
        def contactProperties = JSON.parse(params.contact)
        Contact contactInstance = Contact.get(contactProperties.id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()

        if (!contactInstance) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'contact.not.found'))
            ajaxResponseDto.modelObject = null
        }

        if (contactProperties.version) {
            def version = contactProperties.version.toLong()
            if (contactInstance.version > version) {
                ajaxResponseDto.errors.put("version", message(code:"default.optimistic.locking.failure",
                        args: [message(code: 'contact.label', default: 'Contact')] as Object[]))

                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.modelObject = null
            }
        }

        contactInstance.properties = contactProperties.properties

        if (!contactInstance.save(flush: true)) {
            render(view: "edit", model: [contactInstance: contactInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'contact.label', default: 'Contact'), contactInstance.id])
        generateRedirectLink("contact", "show", [id:contactInstance.id])
    }

    def delete() {
        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
            generateRedirectLink("contact", "index")
            return
        }

        try {
            contactInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
            generateRedirectLink("contact", "index")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
            generateRedirectLink("contact", "show", [id:contactInstance.id])
        }
    }
}

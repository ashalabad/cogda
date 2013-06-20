package com.cogda.domain

import com.cogda.BaseController
import com.cogda.common.GenderEnum
import com.cogda.common.web.AjaxResponseDto
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import org.apache.commons.beanutils.BeanUtils
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

    /**
     * Updates an existing Contact
     * @return AjaxResponseDto as JSON
     */
    def update() {

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
    def delete() {
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
       
}

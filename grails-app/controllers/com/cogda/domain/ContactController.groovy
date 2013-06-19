package com.cogda.domain

import com.cogda.BaseController
import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class ContactController extends BaseController{

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

        List contactInstanceList = Contact.displayContactList(params)

        def dataToRender = [:]

        dataToRender.iTotalRecords = contactInstanceList.size()
        dataToRender.iTotalDisplayRecords = dataToRender.iTotalRecords
        dataToRender.aaData = contactInstanceList

        render dataToRender as JSON

        return
    }

    def create() {
        [contactInstance: new Contact(params)]
    }

    def save() {
        def contactInstance = new Contact(params)
        if (!contactInstance.save(flush: true)) {
            render(view: "create", model: [contactInstance: contactInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'contact.label', default: 'Contact'), contactInstance.id])
        generateRedirectLink("contact", "show", [id:contactInstance.id])
    }

    def show() {
        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
            generateRedirectLink("contact", "index")
            return
        }

        [contactInstance: contactInstance]
    }

    def edit() {
        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
            generateRedirectLink("contact", "index")
            return
        }

        [contactInstance: contactInstance]
    }

    def update() {
        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
            generateRedirectLink("contact", "index")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (contactInstance.version > version) {
                contactInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'contact.label', default: 'Contact')] as Object[],
                        "Another user has updated this Contact while you were editing")
                render(view: "edit", model: [contactInstance: contactInstance])
                return
            }
        }

        contactInstance.properties = params

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

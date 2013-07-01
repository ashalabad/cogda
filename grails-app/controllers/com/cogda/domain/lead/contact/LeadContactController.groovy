package com.cogda.domain.lead.contact

import com.cogda.multitenant.LeadContact
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

/**
 * LeadContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadContactController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leadContactInstanceList: LeadContact.list(params), leadContactInstanceTotal: LeadContact.count()]
    }

    def create() {
        [leadContactInstance: new LeadContact(params)]
    }

    def save() {
        def leadContactInstance = new LeadContact(params)
        if (!leadContactInstance.save(flush: true)) {
            render(view: "create", model: [leadContactInstance: leadContactInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), leadContactInstance.id])
        redirect(action: "show", id: leadContactInstance.id)
    }

    def show() {
        def leadContactInstance = LeadContact.get(params.id)
        if (!leadContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), params.id])
            redirect(action: "list")
            return
        }

        [leadContactInstance: leadContactInstance]
    }

    def edit() {
        def leadContactInstance = LeadContact.get(params.id)
        if (!leadContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), params.id])
            redirect(action: "list")
            return
        }

        [leadContactInstance: leadContactInstance]
    }

    def update() {
        def leadContactInstance = LeadContact.get(params.id)
        if (!leadContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (leadContactInstance.version > version) {
                leadContactInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'leadContact.label', default: 'LeadContact')] as Object[],
                        "Another user has updated this LeadContact while you were editing")
                render(view: "edit", model: [leadContactInstance: leadContactInstance])
                return
            }
        }

        leadContactInstance.properties = params

        if (!leadContactInstance.save(flush: true)) {
            render(view: "edit", model: [leadContactInstance: leadContactInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), leadContactInstance.id])
        redirect(action: "show", id: leadContactInstance.id)
    }

    def delete() {
        def leadContactInstance = LeadContact.get(params.id)
        if (!leadContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), params.id])
            redirect(action: "list")
            return
        }

        try {
            leadContactInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadContact.label', default: 'LeadContact'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}

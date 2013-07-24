package com.cogda.multitenant.lead

import com.cogda.multitenant.LeadFileReference
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

/**
 * LeadFileReferenceController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadFileReferenceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leadFileReferenceInstanceList: LeadFileReference.list(params), leadFileReferenceInstanceTotal: LeadFileReference.count()]
    }

    def create() {
        [leadFileReferenceInstance: new LeadFileReference(params)]
    }

    def save() {
        def leadFileReferenceInstance = new LeadFileReference(params)
        if (!leadFileReferenceInstance.save(flush: true)) {
            render(view: "create", model: [leadFileReferenceInstance: leadFileReferenceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), leadFileReferenceInstance.id])
        redirect(action: "show", id: leadFileReferenceInstance.id)
    }

    def show() {
        def leadFileReferenceInstance = LeadFileReference.get(params.id)
        if (!leadFileReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), params.id])
            redirect(action: "list")
            return
        }

        [leadFileReferenceInstance: leadFileReferenceInstance]
    }

    def edit() {
        def leadFileReferenceInstance = LeadFileReference.get(params.id)
        if (!leadFileReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), params.id])
            redirect(action: "list")
            return
        }

        [leadFileReferenceInstance: leadFileReferenceInstance]
    }

    def update() {
        def leadFileReferenceInstance = LeadFileReference.get(params.id)
        if (!leadFileReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (leadFileReferenceInstance.version > version) {
                leadFileReferenceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'leadFileReference.label', default: 'LeadFileReference')] as Object[],
                        "Another user has updated this LeadFileReference while you were editing")
                render(view: "edit", model: [leadFileReferenceInstance: leadFileReferenceInstance])
                return
            }
        }

        leadFileReferenceInstance.properties = params

        if (!leadFileReferenceInstance.save(flush: true)) {
            render(view: "edit", model: [leadFileReferenceInstance: leadFileReferenceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), leadFileReferenceInstance.id])
        redirect(action: "show", id: leadFileReferenceInstance.id)
    }

    def delete() {
        def leadFileReferenceInstance = LeadFileReference.get(params.id)
        if (!leadFileReferenceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), params.id])
            redirect(action: "list")
            return
        }

        try {
            leadFileReferenceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadFileReference.label', default: 'LeadFileReference'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}

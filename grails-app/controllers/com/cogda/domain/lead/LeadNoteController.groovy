package com.cogda.domain.lead

import com.cogda.GsonBaseController
import com.cogda.multitenant.Lead
import com.cogda.multitenant.LeadNote
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

/**
 * LeadNoteController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadNoteController extends GsonBaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [leadNoteInstanceList: LeadNote.list(params), leadNoteInstanceTotal: LeadNote.count()]
    }

    def create() {
        [leadNoteInstance: new LeadNote(params)]
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadNoteInstance = new LeadNote(request.GSON)
        if (leadNoteInstance.save(flush: true)) {
            respondCreated leadNoteInstance
        } else {
            respondUnprocessableEntity leadNoteInstance
        }
    }

    def show() {
        def leadNoteInstance = LeadNote.get(params.id)
        if (!leadNoteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), params.id])
            redirect(action: "list")
            return
        }

        [leadNoteInstance: leadNoteInstance]
    }

    def edit() {
        def leadNoteInstance = LeadNote.get(params.id)
        if (!leadNoteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), params.id])
            redirect(action: "list")
            return
        }

        [leadNoteInstance: leadNoteInstance]
    }

    def update() {
        def leadNoteInstance = LeadNote.get(params.id)
        if (!leadNoteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (leadNoteInstance.version > version) {
                leadNoteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'leadNote.label', default: 'LeadNote')] as Object[],
                        "Another user has updated this LeadNote while you were editing")
                render(view: "edit", model: [leadNoteInstance: leadNoteInstance])
                return
            }
        }

        leadNoteInstance.properties = params

        if (!leadNoteInstance.save(flush: true)) {
            render(view: "edit", model: [leadNoteInstance: leadNoteInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), leadNoteInstance.id])
        redirect(action: "show", id: leadNoteInstance.id)
    }

    def delete() {
        def leadNoteInstance = LeadNote.get(params.id)
        if (!leadNoteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), params.id])
            redirect(action: "list")
            return
        }

        try {
            leadNoteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'leadNote.label', default: 'LeadNote'), params.id])
            redirect(action: "show", id: params.id)
        }
    }

    def editPartial() {
        render(template: "/lead/leadNote/partials/editPartial")
    }

    def addPartial() {
        render(template: "/lead/leadNote/partials/addPartial")
    }
}

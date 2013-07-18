package com.cogda.domain.lead

import com.cogda.GsonBaseController
import com.cogda.multitenant.Lead
import com.cogda.multitenant.LeadNote
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

/**
 * LeadNoteController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadNoteController extends GsonBaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static LEAD_NOTE_LABEL = "leadNote.label"

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
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadNoteInstance = LeadNote.get(params.id)
        if (!leadNoteInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadNoteInstance.version > params.long('version')) {
                respondConflict(leadNoteInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadNoteInstance.properties = jsonElement

        if (leadNoteInstance.save(flush: true)) {
            respondUpdated leadNoteInstance
        } else {
            respondUnprocessableEntity leadNoteInstance
        }
    }

    def delete() {
        def leadNoteInstance = LeadNote.get(params.id)
        if (!leadNoteInstance) {
            respondNotFound LEAD_NOTE_LABEL, params.id
            return
        }

        try {
            leadNoteInstance.delete(flush: true)
            respondDeleted LEAD_NOTE_LABEL, params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted LEAD_NOTE_LABEL
        }
    }

    def editPartial() {
        render(template: "/lead/leadNote/partials/editPartial")
    }

    def addPartial() {
        render(template: "/lead/leadNote/partials/addPartial")
    }
}

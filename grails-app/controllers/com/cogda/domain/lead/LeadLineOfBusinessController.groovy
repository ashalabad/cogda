package com.cogda.domain.lead

import com.cogda.GsonBaseController
import com.cogda.multitenant.LeadLineOfBusiness
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException

/**
 * LeadLineOfBusinessController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LeadLineOfBusinessController extends GsonBaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    static LEAD_LINE_OF_BUSINESS_LABEL = "leadLineOfBusiness.label"

    def editPartial() {
        render(template: "/lead/leadLineOfBusiness/partials/editPartial")
    }

    def addPartial() {
        render(template: "/lead/leadLineOfBusiness/partials/addPartial")
    }

    def createPartial() {
        render(template:"/lead/leadLineOfBusiness/partials/createPartial")
    }

    def showPartial() {
        render(template:"/lead/leadLineOfBusiness/partials/showPartial")
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadLineOfBusinsessInstance = new LeadLineOfBusiness(request.GSON)
        if (leadLineOfBusinsessInstance.save(flush: true)) {
            respondCreated leadLineOfBusinsessInstance
        } else {
            respondUnprocessableEntity leadLineOfBusinsessInstance
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadLineOfBusinsessInstance = LeadLineOfBusiness.get(params.id)
        if (!leadLineOfBusinsessInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadLineOfBusinsessInstance.version > params.long('version')) {
                respondConflict(leadLineOfBusinsessInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadLineOfBusinsessInstance.properties = jsonElement

        if (leadLineOfBusinsessInstance.save(flush: true)) {
            respondUpdated leadLineOfBusinsessInstance
        } else {
            respondUnprocessableEntity leadLineOfBusinsessInstance
        }
    }

    def delete() {
        def leadLineOfBusinsessInstance = LeadLineOfBusiness.get(params.id)
        if (!leadLineOfBusinsessInstance) {
            respondNotFound LEAD_LINE_OF_BUSINESS_LABEL, params.id
            return
        }

        try {
            leadLineOfBusinsessInstance.delete(flush: true)
            respondDeleted LEAD_LINE_OF_BUSINESS_LABEL, params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted LEAD_LINE_OF_BUSINESS_LABEL
        }
    }
}

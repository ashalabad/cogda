package com.cogda.multitenant.lead

import com.cogda.GsonBaseController
import com.cogda.multitenant.Lead
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON

/**
 * LeadController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LeadController extends GsonBaseController {

    final static String LEAD_LABEL = "lead.label"

    def showPartial() {
        render(template:'/lead/partials/showPartial')
    }

    def editPartial() {
        render(template:'/lead/partials/editPartial')
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        Lead leadInstance = new Lead(request.GSON)
        if (!leadInstance.save(flush: true)) {
            respondUnprocessableEntity(leadInstance)
            return
        } else {
            respondCreated leadInstance
            return
        }
    }

    def update() {
        def leadInstance = Lead.get(params.id)
        if (!leadInstance) {
            respondNotFound(LEAD_LABEL, params.id)
        }

        if (params.version != null) {
            if (leadInstance.version > params.long('version')) {
                respondConflict leadInstance
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadInstance.properties = jsonElement

        if (leadInstance.save(flush: true)) {
            respondUpdated(leadInstance)
        } else {
            respondUnprocessableEntity(leadInstance)
        }
    }
}

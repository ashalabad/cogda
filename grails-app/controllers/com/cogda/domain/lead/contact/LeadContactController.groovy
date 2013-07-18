package com.cogda.domain.lead.contact

import com.cogda.GsonBaseController
import com.cogda.multitenant.LeadContact
import com.google.gson.Gson
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.SC_OK

/**
 * LeadContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class LeadContactController extends GsonBaseController {

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, LeadContact.count()
        List leadContacts = LeadContact.list(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(leadContacts)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        LeadContact leadContactInstance = new LeadContact(request.GSON)
        if (leadContactInstance.save(flush: true)) {
            respondCreated leadContactInstance
        } else {
            respondUnprocessableEntity leadContactInstance
        }
    }

    def get() {
        def leadContactInstance = LeadContact.get(params.id)
        if (leadContactInstance) {
            respondFound leadContactInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def leadContactInstance = LeadContact.get(params.id)
        if (!leadContactInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (leadContactInstance.version > params.long('version')) {
                respondConflict(leadContactInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        leadContactInstance.properties = jsonElement

        if (leadContactInstance.save(flush: true)) {
            respondUpdated leadContactInstance
        } else {
            respondUnprocessableEntity leadContactInstance
        }
    }

    def delete() {
        def leadContactInstance = LeadContact.get(params.id)
        if (!leadContactInstance) {
            respondNotFound params.id
            return
        }

        try {
            leadContactInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showPartial() {
        render(template:'/lead/leadContact/partials/showPartial')
    }

    def indexPartial() {
        render(template:'/lead/leadContact/partials/indexPartial')
    }

    def editPartial() {
        render(template:"/lead/leadContact/partials/editPartial")
    }
}

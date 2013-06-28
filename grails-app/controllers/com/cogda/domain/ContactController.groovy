package com.cogda.domain

import com.cogda.BaseController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a ContactService class that will handle the business logic behind the CRUD actions.
 */
class ContactController extends BaseController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    def create() {

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

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, Contact.count()
        List contacts = Contact.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(contacts)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        Contact contactInstance = new Contact(request.GSON)
        if (contactInstance.save(flush: true)) {
            respondCreated contactInstance
        } else {
            respondUnprocessableEntity contactInstance
        }
    }

    def get() {
        def contactInstance = Contact.get(params.id)
        if (contactInstance) {
            respondFound contactInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (contactInstance.version > params.long('version')) {
                respondConflict(contactInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        contactInstance.properties = jsonElement

        if (contactInstance.save(flush: true)) {
            respondUpdated contactInstance
        } else {
            respondUnprocessableEntity contactInstance
        }
    }

    def delete() {
        def contactInstance = Contact.get(params.id)
        if (!contactInstance) {
            respondNotFound params.id
            return
        }

        try {
            contactInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showForm(){
        def contactInstance = Contact.get(params.id)
        if (contactInstance) {
            response.status = SC_OK
            render(template:"/_common/contact/modalForm", model:[contactInstance:contactInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    def showNewForm(){
        def contactInstance = new Contact()
        if (contactInstance) {
            response.status = SC_OK
            render(template:"/_common/contact/modalForm", model:[contactInstance:contactInstance])
            return
        } else {
            respondNotFound params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(Contact contactInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
    }

    private void respondCreated(Contact contactInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: contactInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
    }

    private void respondUpdated(Contact contactInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(contactInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(Contact contactInstance) {
        def responseBody = [:]
        responseBody.errors = contactInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(Contact contactInstance) {
        contactInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'contact.label', default: 'Contact')] as Object[],
                'Another user has updated this Contact while you were editing')
        def responseBody = [:]
        responseBody.errors = contactInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'contact.label', default: 'Contact'), id])
        response.status = SC_INTERNAL_SERVER_ERROR  // 500
        render responseBody as GSON
    }

    private void respondNotAcceptable() {
        response.status = SC_NOT_ACCEPTABLE  // 406
        response.contentLength = 0
        response.outputStream.flush()
        response.outputStream.close()
    }    
    
}

package com.cogda.multitenant

import com.cogda.BaseController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * AccountNoteController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a AccountNoteService class that will handle the business logic behind the CRUD actions.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountNoteController extends BaseController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    def create() {

    }

    /**
     * Passes back a JSON list of AccountNotes
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List accountNoteInstanceList = AccountNote.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        accountNoteInstanceList.each { AccountNote accountNote ->
            Map map = [:]
            map.DT_RowId = "row_"+accountNote.id
            map.version = accountNote.version
            map.companyName = accountNote.companyName
            map.lastName = accountNote.lastName
            map.firstName = accountNote.firstName
            map.jobTitle = accountNote.jobTitle
            map.primaryEmailAddress = accountNote.primaryEmailAddress
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, AccountNote.count()
        List accountNotes = AccountNote.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(accountNotes)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        AccountNote accountNoteInstance = new AccountNote(request.GSON)
        if (accountNoteInstance.save(flush: true)) {
            respondCreated accountNoteInstance
        } else {
            respondUnprocessableEntity accountNoteInstance
        }
    }

    def get() {
        def accountNoteInstance = AccountNote.get(params.id)
        if (accountNoteInstance) {
            respondFound accountNoteInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountNoteInstance = AccountNote.get(params.id)
        if (!accountNoteInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (accountNoteInstance.version > params.long('version')) {
                respondConflict(accountNoteInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        accountNoteInstance.properties = jsonElement

        if (accountNoteInstance.save(flush: true)) {
            respondUpdated accountNoteInstance
        } else {
            respondUnprocessableEntity accountNoteInstance
        }
    }

    def delete() {
        def accountNoteInstance = AccountNote.get(params.id)
        if (!accountNoteInstance) {
            respondNotFound params.id
            return
        }

        try {
            accountNoteInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showForm(){
        def accountNoteInstance = AccountNote.get(params.id)
        if (accountNoteInstance) {
            response.status = SC_OK
            render(template:"/_common/accountNote/modalForm", model:[accountNoteInstance:accountNoteInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    def showNewForm(){
        def accountNoteInstance = new AccountNote()
        if (accountNoteInstance) {
            response.status = SC_OK
            render(template:"/_common/accountNote/modalForm", model:[accountNoteInstance:accountNoteInstance])
            return
        } else {
            respondNotFound params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(AccountNote accountNoteInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountNoteInstance);
        render gsonRetString
    }

    private void respondCreated(AccountNote accountNoteInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: accountNoteInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountNoteInstance);
        render gsonRetString
    }

    private void respondUpdated(AccountNote accountNoteInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountNoteInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(AccountNote accountNoteInstance) {
        def responseBody = [:]
        responseBody.errors = accountNoteInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'accountNote.label', default: 'AccountNote'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(AccountNote accountNoteInstance) {
        accountNoteInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'accountNote.label', default: 'AccountNote')] as Object[],
                'Another user has updated this AccountNote while you were editing')
        def responseBody = [:]
        responseBody.errors = accountNoteInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'accountNote.label', default: 'AccountNote'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'accountNote.label', default: 'AccountNote'), id])
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

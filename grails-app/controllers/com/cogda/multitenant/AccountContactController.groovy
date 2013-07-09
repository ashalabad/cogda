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
 * AccountContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 * TODO: Add a AccountContactService class that will handle the business logic behind the CRUD actions.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountContactController extends BaseController{

    GsonBuilder gsonBuilder

    static allowedMethods = [save: "POST", update: "POST", delete: "POST",add:"GET"]

    def index() {

    }

    def create() {

    }
    def add() {
        def accountInstance = Account.get(params.id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])
            return
        }
        render(template: '/_common/modals/accountContactTabs/add', model: [accountInstance: accountInstance])
    }

    def addExisting() {
        //TODO:  implement this when contact gets owner
        render(template: '/_common/modals/accountContactTabs/addExisting')
    }

    def show() {
        def accountContactInstance = AccountContact.get(params.id)
        if (!accountContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountContact.label', default: 'Account Contact'), params.id])
            return
        }
        render(template: '/_common/modals/accountContactTabs/show', model: [accountContactInstance: accountContactInstance])
    }

    def edit() {
        def accountContactInstance = AccountContact.get(params.id)
        if (!accountContactInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountContact.label', default: 'Account Contact'), params.id])
            return
        }
        render(template: '/_common/modals/accountContactTabs/edit', model: [accountContactInstance: accountContactInstance])
    }

    /**
     * Passes back a JSON list of AccountContacts
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        List accountContactInstanceList = AccountContact.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        accountContactInstanceList.each { AccountContact accountContact ->
            Map map = [:]
            map.DT_RowId = "row_"+accountContact.id
            map.version = accountContact.version
            map.companyName = accountContact.companyName
            map.lastName = accountContact.lastName
            map.firstName = accountContact.firstName
            map.jobTitle = accountContact.jobTitle
            map.primaryEmailAddress = accountContact.primaryEmailAddress
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }

    def jlist(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        response.status = SC_OK
        response.addIntHeader X_PAGINATION_TOTAL, AccountContact.count()
        List accountContacts = AccountContact.listOrderById(params)
        Gson gson = gsonBuilder.create()
        def jsonTree = gson.toJsonTree(accountContacts)
        render jsonTree
        return
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }
        AccountContact accountContactInstance = new AccountContact(request.GSON)
        if (accountContactInstance.save(flush: true)) {
            respondCreated accountContactInstance
        } else {
            respondUnprocessableEntity accountContactInstance
        }
    }

    def get() {
        def accountContactInstance = AccountContact.get(params.id)
        if (accountContactInstance) {
            respondFound accountContactInstance
        } else {
            respondNotFound params.id
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountContactInstance = AccountContact.get(params.id)
        if (!accountContactInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (accountContactInstance.version > params.long('version')) {
                respondConflict(accountContactInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        accountContactInstance.properties = jsonElement

        if (accountContactInstance.save(flush: true)) {
            respondUpdated accountContactInstance
        } else {
            respondUnprocessableEntity accountContactInstance
        }
    }

    def delete() {
        def accountContactInstance = AccountContact.get(params.id)
        if (!accountContactInstance) {
            respondNotFound params.id
            return
        }

        try {
            accountContactInstance.delete(flush: true)
            respondDeleted params.id
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted params.id
        }
    }

    def showForm(){
        def accountContactInstance = AccountContact.get(params.id)
        if (accountContactInstance) {
            response.status = SC_OK
            render(template:"/_common/accountContact/modalForm", model:[accountContactInstance:accountContactInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    def showNewForm(){
        def accountContactInstance = new AccountContact()
        if (accountContactInstance) {
            response.status = SC_OK
            render(template:"/_common/accountContact/modalForm", model:[accountContactInstance:accountContactInstance])
            return
        } else {
            respondNotFound params.id
        }
    }


    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(AccountContact accountContactInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountContactInstance);
        render gsonRetString
    }

    private void respondCreated(AccountContact accountContactInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: accountContactInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountContactInstance);
        render gsonRetString
    }

    private void respondUpdated(AccountContact accountContactInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountContactInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(AccountContact accountContactInstance) {
        def responseBody = [:]
        responseBody.errors = accountContactInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'accountContact.label', default: 'Account Contact'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(AccountContact accountContactInstance) {
        accountContactInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'accountContact.label', default: 'Account Contact')] as Object[],
                'Another user has updated this Account Contact while you were editing')
        def responseBody = [:]
        responseBody.errors = accountContactInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'accountContact.label', default: 'Account Contact'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'accountContact.label', default: 'Account Contact'), id])
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

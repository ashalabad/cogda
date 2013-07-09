package com.cogda.multitenant

import com.cogda.BaseController
import com.cogda.common.web.AjaxResponseDto
import com.cogda.util.ErrorMessageResolverService
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
 * AccountController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountController extends BaseController{

    ErrorMessageResolverService errorMessageResolverService
    GsonBuilder gsonBuilder
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {

    }

    /**
     * Passes back a JSON list of Accounts
     *
     * @return
     */
    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        List accountInstanceList = Account.list()

        def dataToRender = [:]

        dataToRender.aaData = []
        accountInstanceList.each { Account account ->
            Map map = [:]
            map.DT_RowId = "row_"+account.id
            map.version = account.version
            map.accountName = account.accountName
            map.accountCode = account.accountCode
            map.accountType = account.accountType
            map.primaryContact= account.primaryAccountContactName +
                    "<br>"+account.primaryEmailAddress +
                    "<br>"+account.primaryAccountContactPhoneNumberString
            map.action = ""
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON

        return
    }
    def show() {
        def accountInstance = Account.get(params.id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), params.id])
            redirect(action: "list")
            return
        }
        render(template: '/_common/modals/account/show', model: [accountInstance: accountInstance])
    }

    def edit() {
        def accountInstance = Account.get(params.id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'lead.label', default: 'Account'), params.id])
            redirect(action: "list")
            return
        }
        render(template: '/_common/modals/account/edit', model: [accountInstance: accountInstance])
    }

    /**
     * Inserts a new Account into the database based on the passed in
     * JSON.
     * @return AjaxResponseDto as JSON
     */
    def save() {
        def accountInstance = new Account(JSON.parse(params.account))

        accountInstance.save(flush: true)

        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if(accountInstance.hasErrors()){
            ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(accountInstance)
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.modelObject = accountInstance
        }else{
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.addMessage(message(code:'account.save.successful'))
            ajaxResponseDto.modelObject = accountInstance
        }

        render ajaxResponseDto as JSON
        return
    }

    /**
     * Retrieves the Account instance and parses it to JSON.
     * @return AjaxResponseDto as JSON
     */
    def get(){
        def accountInstance = Account.get(params.id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()
        if(!accountInstance){

            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'account.not.found'))
            ajaxResponseDto.modelObject = null

        }else{

            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.modelObject = accountInstance

        }

        //JSON.use('deep')
        println ajaxResponseDto as JSON
        render ajaxResponseDto as JSON
        return
    }

    /**
     * Retrieves the Account Contacts from the Account instance and parses it to JSON.
     * @return AjaxResponseDto as JSON
     */
    def getAccountContacts(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def accountInstance = Account.get(params.id)

        def dataToRender = [:]

        dataToRender.aaData = []
        accountInstance.accountContacts.each { AccountContact accountContact ->
            Map map = [:]
            map.DT_RowId = "row_" + accountContact.id
            map.accountContactName = accountContact.fullName + (accountContact.primaryContact ? ' (Primary)': '')
            map.accountContactEmail = accountContact.primaryAccountEmailAddress
            map.accountContactPhone = accountContact.primaryAccountPhoneNumber
            map.action = ""
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON
    }

    /**
     * Retrieves Account Notes from the Account instance and parses it to JSON.
     * @return AjaxResponseDto as JSON
     */
    def getAccountNotes(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def accountInstance = Account.get(params.id)

        def dataToRender = [:]

        dataToRender.aaData = []
        accountInstance.accountNotes.each { AccountNote accountNote ->
            Map map = [:]
            map.DT_RowId = "row_" + accountNote.id
            map.accountNote = accountNote.note.notes
            map.accountNoteType = accountNote.noteType ? accountNote.noteType.toString() : ""
            map.accountNoteDate = accountNote.dateCreated
            map.action = ""
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as JSON
    }

    /**
     * Retrieves Account Submissions from the Account instance and parses it to JSON.
     * @return AjaxResponseDto as JSON
     */
    def getSubmissions(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def accountInstance = Account.get(params.id)

        def dataToRender = [:]

        dataToRender.aaData = [] //TODO:implement submissions on Account Domain
//        accountInstance.submissions.each { AccountNote accountNote ->
        Map map = [:]
        map.DT_RowId = "row_" + 1
        map.submissionDate = "date submitted"
        map.submissionLead = "prospect or suspect"
        map.submissionLOB = "LOB"
        map.submissionxDate = "xDate"
        map.action = ""
        dataToRender.aaData.add(map)
//        }
        dataToRender.sEcho = 1

        render dataToRender as JSON
    }



    /**
     * Updates an existing Account
     * @return AjaxResponseDto as JSON
     */
    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountInstance = Account.get(params.id)
        if (!accountInstance) {
            respondNotFound params.id
            return
        }

        if (params.version != null) {
            if (accountInstance.version > params.long('version')) {
                respondConflict(accountInstance)
                return
            }
        }

        JsonElement jsonElement = GSON.parse(request)
        accountInstance.properties = jsonElement

        if (accountInstance.save(flush: true)) {
            respondUpdated accountInstance
        } else {
            respondUnprocessableEntity accountInstance
        }
    }

    /**
     * Deletes a Account
     * @return AjaxResponseDto as JSON
     */
    def delete() {
        def jsonProperties = JSON.parse(params.account)

        Account accountInstance = Account.get(jsonProperties.id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()

        if (!accountInstance) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'account.not.found'))
            ajaxResponseDto.modelObject = null
        }
        try {
            accountInstance.delete(flush: true)
            ajaxResponseDto.success = Boolean.TRUE
            ajaxResponseDto.addMessage(message(code:'account.delete.successful'))
        }
        catch (DataIntegrityViolationException e) {
            ajaxResponseDto.errors.put("id", message(code: 'default.not.deleted.message', args: [message(code: 'account.label', default: 'Account')]))
            ajaxResponseDto.success = Boolean.FALSE
        }

        render ajaxResponseDto as JSON
        return
    }

    def showForm(){
        def accountInstance = Account.get(params.id)
        if (accountInstance) {
            response.status = SC_OK
            render(template:"/_common/modals/account/edit", model:[accountInstance:accountInstance])
            return
        } else {
            respondNotFound params.id
        }
    }

    private boolean requestIsJson() {
        GSON.isJson(request)
    }

    private void respondFound(Account accountInstance) {
        response.status = SC_OK
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountInstance);
        render gsonRetString
    }

    private void respondCreated(Account accountInstance) {
        response.status = SC_CREATED // 201
        response.addHeader LOCATION, createLink(action: 'get', id: accountInstance.id)
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountInstance);
        render gsonRetString
    }

    private void respondUpdated(Account accountInstance) {
        response.status = SC_OK // 200
        Gson gson = gsonBuilder.create()
        def gsonRetString = gson.toJsonTree(accountInstance);
        render gsonRetString
    }

    private void respondUnprocessableEntity(Account accountInstance) {
        def responseBody = [:]
        responseBody.errors = accountInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

    private void respondConflict(Account accountInstance) {
        accountInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                [message(code: 'account.label', default: 'Account')] as Object[],
                'Another user has updated this Account while you were editing')
        def responseBody = [:]
        responseBody.errors = accountInstance.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_CONFLICT // 409
        render responseBody as GSON
    }

    private void respondDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.deleted.message', args: [message(code: 'account.label', default: 'Account'), id])
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondNotDeleted(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.deleted.message', args: [message(code: 'account.label', default: 'Account'), id])
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


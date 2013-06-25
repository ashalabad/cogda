package com.cogda.multitenant

import com.cogda.BaseController
import com.cogda.common.web.AjaxResponseDto
import com.cogda.util.ErrorMessageResolverService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import org.springframework.dao.DataIntegrityViolationException

/**
 * AccountController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
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
            map.primaryContactName = account.primaryAccountContactName
            map.primaryEmailAddress = account.primaryEmailAddress
            map.primaryPhoneNumber = account.primaryAccountContactPhoneNumberString
            map.dateCreated = account.dateCreated

            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as GSON

        return
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

        JSON.use('deep')
        //println ajaxResponseDto as JSON
        render ajaxResponseDto as JSON
        return
    }

    /**
     * Retrieves the Account instance and parses it to JSON.
     * @return AjaxResponseDto as JSON
     */
    def contactList(){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def accountInstance = Account.get(params.id)

        def dataToRender = [:]

        dataToRender.aaData = []
        accountInstance.accountContacts.each { AccountContact accountContact ->
            Map map = [:]
            map.DT_RowId = "row_"+accountContact.id
            map.accountContactName = accountContact.fullName + (accountContact.primaryContact ? ' (Primary)': '')
            map.accountContactEmail = accountContact.primaryAccountEmailAddress
            map.accountContactPhone = accountContact.primaryAccountPhoneNumber
            dataToRender.aaData.add(map)
        }
        dataToRender.sEcho = 1

        render dataToRender as GSON

        return
    }

    /**
     * Updates an existing Account
     * @return AjaxResponseDto as JSON
     */
    def update() {
        def jsonProperties = JSON.parse(params.account)
        Account accountInstance = Account.get(jsonProperties.id)
        AjaxResponseDto ajaxResponseDto = new AjaxResponseDto()

        if (!accountInstance) {
            ajaxResponseDto.success = Boolean.FALSE
            ajaxResponseDto.errors.put("id", message(code: 'account.not.found'))
            ajaxResponseDto.modelObject = null
        }

        if (jsonProperties.version) {
            def version = jsonProperties.version.toLong()
            if (accountInstance.version > version) {
                ajaxResponseDto.errors.put("version", message(code:"default.optimistic.locking.failure",
                        args: [message(code: 'account.label', default: 'Account')] as Object[]))

                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.modelObject = null
            }
        }

        accountInstance.properties = jsonProperties.properties

        if (!accountInstance.save(flush: true)) {
            if(accountInstance.hasErrors()){
                ajaxResponseDto.errors = errorMessageResolverService.retrieveErrorStrings(accountInstance)
                ajaxResponseDto.success = Boolean.FALSE
                ajaxResponseDto.modelObject = accountInstance
            }else{
                ajaxResponseDto.success = Boolean.TRUE
                ajaxResponseDto.addMessage(message(code:'account.save.successful'))
                ajaxResponseDto.modelObject = accountInstance
            }
        }

        render ajaxResponseDto as JSON
        return
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
}


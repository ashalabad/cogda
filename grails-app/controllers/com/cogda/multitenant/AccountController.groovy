package com.cogda.multitenant
import com.cogda.GsonBaseController
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*
/**
 * AccountController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountController extends GsonBaseController {

    final static String ACCOUNT_LABEL = "account.label"

    def index(){

    }

    def list() {
        //println params.dump()
        List accountInstanceList = Account.findAllByActive(true,params)

        def dataToRender = []

        accountInstanceList.each { Account account ->
            Map map = [:]
            map.ngRowId = account.id
            map.accountName = account.accountName
            map.accountCode = account.accountCode
            map.accountType = account.accountType?.code
            map.isMarket = account.isMarket ? "Yes": "No"
            map.favorite = account.favorite
            map.primaryContact= account.primaryAccountContact?.getFullName()
            dataToRender.add(map)
        }
        response.addIntHeader X_PAGINATION_TOTAL, Account.count()
        render dataToRender as GSON
    }

    def listPartial(){
        render (view:'listPartial')
    }

    def editPartial(){
        render(view:'editPartial')
    }

    def createPartial(){
        render(view:'createPartial')
    }

    def showPartial(){
        render(view:'showPartial')
    }

    def createAccountContactPartial(){
        render(view:'createAccountContactPartial')
    }

    def createAccountAddressPartial(){
        render(view:'createAccountAddressPartial')
    }

    def createAccountNotePartial(){
        render(view:'createAccountNotePartial')
    }

    def showAccountContactPartial(){
        render(view:'showAccountContactPartial')
    }

    def editAccountAddressPartial(){
        render(view:'editAccountAddressPartial')
    }

    def editAccountNotePartial(){
        render(view:'editAccountNotePartial')
    }

    def editAccountContactPartial(){
        render(view:'editAccountContactPartial')
    }

    def createAccountContactEmailAddressPartial(){
        render(view:'createAccountContactEmailAddressPartial')
    }

    def editAccountContactEmailAddressPartial(){
        render(view:'editAccountContactEmailAddressPartial')
    }

    def createAccountContactPhoneNumberPartial(){
        render(view:'createAccountContactPhoneNumberPartial')
    }

    def editAccountContactPhoneNumberPartial(){
        render(view:'editAccountContactPhoneNumberPartial')
    }

    def createAccountContactAddressPartial(){
        render(view:'createAccountContactAddressPartial')
    }

    def editAccountContactAddressPartial(){
        render(view:'editAccountContactAddressPartial')
    }



    def get() {
        // TODO: filter active == false
        def accountInstance = Account.get(params.id)
//        if (accountInstance) {
//            respondFound accountInstance
//        } else {
//            respondNotFound ACCOUNT_LABEL
//        }
        JSON.use('deep')
        render accountInstance as JSON
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountInstance = new Account(request.GSON)
        if (accountInstance.save(flush: true)) {
            respondCreated accountInstance
        } else {
            respondUnprocessableEntity accountInstance
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountInstance = Account.get(params.id)
        if (!accountInstance) {
            respondNotFound ACCOUNT_LABEL
            return
        }

        if (params.version != null) {
            if (accountInstance.version > params.long('version')) {
                respondConflict(accountInstance)
                return
            }
        }
//        println request.GSON
        accountInstance.properties = request.GSON

        if (accountInstance.save(flush: true)) {
            respondUpdated accountInstance
        } else {
            respondUnprocessableEntity accountInstance
        }
    }

    def delete() {
        def accountInstance = Account.get(params.id)
        if (!accountInstance) {
            respondNotFound ACCOUNT_LABEL
            return
        }

        try {
            accountInstance.delete(flush: true)
            respondDeleted()
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted ACCOUNT_LABEL
        }
    }
}

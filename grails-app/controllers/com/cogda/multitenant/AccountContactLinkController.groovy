package com.cogda.multitenant
import com.cogda.GsonBaseController
import com.cogda.domain.security.UserRole
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*


@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountContactLinkController extends GsonBaseController {

    final static String LABEL = "accountContactLink.label"

    def accounts() {
        def returnObject = new Expando()
        returnObject.accounts = AccountContactLink.findAllByAccountContact(AccountContact.get(params.id)).collect {it.account}
        render returnObject as JSON
    }

    def availableAccounts() {
        def accountList = Account.findAllByActive(true)
        def memberships = AccountContactLink.findAllByAccountContact(AccountContact.get(params.id)).collect {it.account}
        def availableAccounts =  accountList.minus(memberships).sort{it.accountName}
        def dataToRender = []
        availableAccounts.each {
            def map = [:]
            map.id = it.id
            map.accountName = it.accountName
            dataToRender.add(map)
        }
        render dataToRender as JSON
    }

    def primaryContact() {
        AccountContact primaryContact = AccountContactLink.findByAccountAndPrimaryContact(Account.get(params.id),true)?.accountContact
        if (primaryContact) {
            respondFound primaryContact
        } else {
            respondNotFound "accountContact.primaryContact.label"
        }
    }

    def designatePrimary(){
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def jsonObject = request.GSON
        Long accountId = jsonObject?.account?.id?.getAsLong()
        Long accountContactId = jsonObject?.accountContact?.id?.getAsLong()

        def accountInstance = Account.get(accountId)
        def accountContactInstance = AccountContact.get(accountContactId)
        if(accountInstance && accountContactInstance){
            List accountContactLinkList = AccountContactLink.findAllByAccount(accountInstance)
            accountContactLinkList.each { AccountContactLink accountContactLink ->
                accountContactLink.primaryContact = Boolean.FALSE
                accountContactLink.save()
            }
            def accountContactLinkInstance = AccountContactLink.findByAccountAndAccountContact(accountInstance,accountContactInstance)
            accountContactLinkInstance.primaryContact = Boolean.TRUE
            if (accountContactLinkInstance.save(flush: true)) {
                respondUpdated accountContactLinkInstance
            } else {
                respondUnprocessableEntity accountContactLinkInstance
            }
        }

    }


    def accountContacts() {
        def returnObject = new Expando()
        returnObject.contacts = AccountContactLink.findAllByAccount(Account.get(params.id)).collect {it.accountContact}
        render returnObject as JSON
    }

    def allMarketAccountContactLinks() {
        accountList(Account.findAllByActiveAndIsMarket(true,true))

    }

    def favoriteMarketAccountContactLinks() {
        accountList(Account.findAllByActiveAndIsMarketAndFavorite(true,true,true))
    }

    def accountList(accountInstanceList){
        def dataToRender = []
        accountInstanceList.each { Account account ->
            def accountContactLinkList = AccountContactLink.findAllByAccount(account)
            accountContactLinkList.each { AccountContactLink accountContactLinkInstance ->
                def map = [:]
                map.linkId = accountContactLinkInstance?.linkId
                map.accountName = accountContactLinkInstance?.account?.accountName
                map.accountContactName = accountContactLinkInstance?.accountContact?.getFullName()
                map.accountContactEmail = accountContactLinkInstance?.accountContact?.getPrimaryEmailAddress()
                map.accountContactPrimary = accountContactLinkInstance?.primaryContact
                map.accountContactFavorite = accountContactLinkInstance?.accountContact?.favorite
                map.accountContactId = accountContactLinkInstance?.accountContact?.id
                map.accountContactUserProfile = accountContactLinkInstance?.accountContact?.userProfile
                dataToRender.add(map)
            }

        }
        render dataToRender as GSON
    }

    def allAccountContactMarketLinks() {
        accountContactList(AccountContact.findAllByActiveAndDisplayAsMarketOnBuilder(true,true,params))
    }

    def accountContactList(accountContactInstanceList){
        def dataToRender = []
        accountContactInstanceList.each { AccountContact accountContact ->
            def accountContactLinkList = AccountContactLink.findAllByAccountContact(accountContact)
            accountContactLinkList.each { AccountContactLink accountContactLinkInstance ->
                def map = [:]
                map.linkId = accountContactLinkInstance?.linkId
                map.accountName = accountContactLinkInstance?.account?.accountName
                map.accountContactName = accountContactLinkInstance?.accountContact?.getFullName()
                dataToRender.add(map)
            }

        }
        render dataToRender as GSON
    }



    def availableAccountContacts() {
        def accountContactList = AccountContact.findAllByActive(true)
        def memberships = AccountContactLink.findAllByAccount(Account.get(params.id)).collect {it.accountContact}
        def availableAccounts =  accountContactList.minus(memberships).sort{it.lastName}
        def dataToRender = []
        availableAccounts.each {
            def map = [:]
            map.id = it.id
            map.accountContact = it.getFullName()
            dataToRender.add(map)
        }
        render dataToRender as JSON
    }


    def get() {
        def accountInstance = Account.get(params.account)
        def accountContactInstance = AccountContact.get(params.accountContact)
        if(accountInstance && accountContactInstance){
            def accountContactLinkInstance = AccountContactLink.findByAccountAndAccountContact(accountInstance,accountContactInstance)
            if (accountContactLinkInstance) {
                respondFound accountContactLinkInstance
            }
        }else {
            respondNotFound LABEL
        }

    }

    def create() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def jsonObject = request.GSON
        Long accountId = jsonObject?.account?.getAsLong()
        Long accountContactId = jsonObject?.accountContact?.getAsLong()

        def accountInstance = Account.get(accountId)
        def accountContactInstance = AccountContact.get(accountContactId)
        if(accountInstance && accountContactInstance){
            def accountContactLinkInstance = AccountContactLink.create(accountInstance,accountContactInstance,false)
            if(accountContactLinkInstance){
                respondCreated accountContactLinkInstance
            } else {
                respondUnprocessableEntity accountContactLinkInstance
            }
        }

    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountContactLinkInstance = new AccountContactLink(request.GSON)
        if (accountContactLinkInstance.save(flush: true)) {
            respondCreated accountContactLinkInstance
        } else {
            respondUnprocessableEntity accountContactLinkInstance
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountContactLinkInstance = AccountContactLink.get(params.id)
        if (!accountContactLinkInstance) {
            respondNotFound LABEL
            return
        }

        if (params.version != null) {
            if (accountContactLinkInstance.version > params.long('version')) {
                respondConflict(accountContactLinkInstance)
                return
            }
        }
        accountContactLinkInstance.properties = request.GSON

        if (accountContactLinkInstance.save(flush: true)) {
            respondUpdated accountContactLinkInstance
        } else {
            respondUnprocessableEntity accountContactLinkInstance
        }
    }

    def delete() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def jsonObject = request.GSON
        Long accountId = jsonObject?.account?.id?.getAsLong()
        Long accountContactId = jsonObject?.accountContact?.id?.getAsLong()

        def accountInstance = Account.get(accountId)
        def accountContactInstance = AccountContact.get(accountContactId)
        if(accountInstance && accountContactInstance){
            def accountContactLinkInstance = AccountContactLink.findByAccountAndAccountContact(accountInstance,accountContactInstance)
            if (!accountContactLinkInstance) {
                respondNotFound LABEL
                return
            }


            try {
                accountContactLinkInstance.delete(flush: true)
                respondDeleted LABEL
            } catch (DataIntegrityViolationException e) {
                respondNotDeleted LABEL
            }
        }
        else {
            respondNotFound LABEL
        }
    }
}

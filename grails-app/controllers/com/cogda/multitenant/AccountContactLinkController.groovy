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

    final static String LABEL = "accountContact.label"


    def primaryContact() {
        AccountContact primaryContact = AccountContactLink.findByAccountAndPrimaryContact(Account.get(params.id),true)?.accountContact
        if (primaryContact) {
            respondFound primaryContact
        } else {
            respondNotFound "Primary Contact"
        }
    }

    def accountContacts() {
        def returnObject = new Expando()
        returnObject.contacts = AccountContactLink.findAllByAccount(Account.get(params.id)).collect {it.accountContact}
        render returnObject as JSON
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
//        println request.GSON
        accountContactLinkInstance.properties = request.GSON

        if (accountContactLinkInstance.save(flush: true)) {
            respondUpdated accountContactLinkInstance
        } else {
            respondUnprocessableEntity accountContactLinkInstance
        }
    }

}

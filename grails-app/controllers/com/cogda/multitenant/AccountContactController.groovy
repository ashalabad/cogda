package com.cogda.multitenant
import com.cogda.GsonBaseController
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException
import static grails.plugin.gson.http.HttpConstants.*
/**
 * AccountContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountContactController extends GsonBaseController {

    final static String LABEL = "accountContact.label"

    def get() {
        def accountContactInstance = AccountContact.get(params.id)
//        if (accountContactInstance) {
//            respondFound accountContactInstance
//        } else {
//            respondNotFound LABEL
//        }
        JSON.use('deep')
        render accountContactInstance as JSON
    }

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountContactInstance = new AccountContact(request.GSON)
        if (accountContactInstance.save(flush: true)) {
            respondCreated accountContactInstance
        } else {
            respondUnprocessableEntity accountContactInstance
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountContactInstance = AccountContact.get(params.id)
        if (!accountContactInstance) {
            respondNotFound LABEL
            return
        }

        if (params.version != null) {
            if (accountContactInstance.version > params.long('version')) {
                respondConflict(accountContactInstance)
                return
            }
        }
        accountContactInstance.properties = request.GSON

        if (accountContactInstance.save(flush: true)) {
            respondUpdated accountContactInstance
        } else {
            respondUnprocessableEntity accountContactInstance
        }
    }

    def delete() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def accountContactInstance = AccountContact.get(params.id)
        if (!accountContactInstance) {
            respondNotFound LABEL
            return
        }

        if (params.version != null) {
            if (accountContactInstance.version > params.long('version')) {
                respondConflict(accountContactInstance)
                return
            }
        }
        AccountContactLink.removeAll(accountContactInstance)
        accountContactInstance.active = Boolean.FALSE

        if (accountContactInstance.save(flush: true)) {
            respondDeleted LABEL
        } else {
            respondNotDeleted LABEL
        }
    }
}

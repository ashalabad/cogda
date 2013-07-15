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

    def get() {
        def accountContactInstance = AccountContact.get(params.id)
//        if (accountContactInstance) {
//            respondFound accountContactInstance
//        } else {
//            respondNotFound ACCOUNT_LABEL
//        }
        JSON.use('deep')
        render accountContactInstance as JSON
    }
}

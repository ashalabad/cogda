package com.cogda.multitenant

import com.cogda.GsonBaseController
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountContactEmailAddressController extends GsonBaseController {

    final static String LABEL = "accountContact.accountContactEmailAddress.emailAddress.label"

    def delete() {
        def accountContactEmailAddressInstance = AccountContactEmailAddress.get(params.id)
        if (!accountContactEmailAddressInstance) {
            respondNotFound LABEL
            return
        }

        try {
            accountContactEmailAddressInstance.delete(flush: true)
            respondDeleted LABEL
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted LABEL
        }
    }
}

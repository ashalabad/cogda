package com.cogda.multitenant

import com.cogda.GsonBaseController
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountContactPhoneNumberController extends GsonBaseController {

    final static String LABEL = "accountContact.accountContactPhoneNumber.phoneNumber.label"

    def delete() {
        def accountContactPhoneNumberInstance = AccountContactPhoneNumber.get(params.id)
        if (!accountContactPhoneNumberInstance) {
            respondNotFound LABEL
            return
        }

        try {
            accountContactPhoneNumberInstance.delete(flush: true)
            respondDeleted LABEL
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted LABEL
        }
    }
}

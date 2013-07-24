package com.cogda.multitenant

import com.cogda.GsonBaseController
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(['IS_AUTHENTICATED_FULLY'])
class AccountContactAddressController extends GsonBaseController {

    final static String LABEL = "accountContact.accountContactAddress.address.label"

    def delete() {
        def accountContactAddressInstance = AccountContactAddress.get(params.id)
        if (!accountContactAddressInstance) {
            respondNotFound LABEL
            return
        }

        try {
            accountContactAddressInstance.delete(flush: true)
            respondDeleted LABEL
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted LABEL
        }
    }
}
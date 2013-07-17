package com.cogda.domain

import com.cogda.GsonBaseController
import org.springframework.dao.DataIntegrityViolationException
/**
 * CompanyProfilePhoneNumberController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CompanyProfilePhoneNumberController extends GsonBaseController {
    final static String INSTANCE_LABEL = "companyProfilePhoneNumber.label"

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfilePhoneNumberInstance = new CompanyProfilePhoneNumber(request.GSON)
        if (companyProfilePhoneNumberInstance.save(flush: true)) {
            respondCreated companyProfilePhoneNumberInstance
        } else {
            respondUnprocessableEntity companyProfilePhoneNumberInstance
        }
    }

    def get() {
        CompanyProfilePhoneNumber companyProfilePhoneNumberInstance = CompanyProfilePhoneNumber.get(params.id)
        if (companyProfilePhoneNumberInstance) {
            respondFound companyProfilePhoneNumberInstance
        } else {
            respondNotFound INSTANCE_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfilePhoneNumberInstance = CompanyProfilePhoneNumber.get(params.id)
        if (!companyProfilePhoneNumberInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        if (params.version != null) {
            if (companyProfilePhoneNumberInstance.version > params.long('version')) {
                respondConflict(companyProfilePhoneNumberInstance)
                return
            }
        }

        companyProfilePhoneNumberInstance.properties = request.GSON

        if (companyProfilePhoneNumberInstance.save(flush: true)) {
            respondUpdated companyProfilePhoneNumberInstance
        } else {
            respondUnprocessableEntity companyProfilePhoneNumberInstance
        }
    }

    def delete() {
        def companyProfilePhoneNumberInstance = CompanyProfilePhoneNumber.get(params.id)
        if (!companyProfilePhoneNumberInstance) {
            respondNotFound INSTANCE_LABEL
            return
        }

        try {
            companyProfilePhoneNumberInstance.delete(flush: true)
            respondDeleted(INSTANCE_LABEL)
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted INSTANCE_LABEL
        }
    }
}

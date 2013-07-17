package com.cogda.domain

import com.cogda.GsonBaseController
import org.springframework.dao.DataIntegrityViolationException

/**
 * CompanyProfileAddressController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CompanyProfileAddressController extends GsonBaseController{
    final static String COMPANY_PROFILE_ADDRESS_LABEL = "companyProfileAddress.label"

    def save() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfileAddressInstance = new CompanyProfileAddress(request.GSON)
        if (companyProfileAddressInstance.save(flush: true)) {
            respondCreated companyProfileAddressInstance
        } else {
            respondUnprocessableEntity companyProfileAddressInstance
        }
    }

    def get() {
        CompanyProfileAddress companyProfileAddressInstance = CompanyProfileAddress.get(params.id)
        if (companyProfileAddressInstance) {
            respondFound companyProfileAddressInstance
        } else {
            respondNotFound COMPANY_PROFILE_ADDRESS_LABEL
        }
    }

    def update() {
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def companyProfileAddressInstance = CompanyProfileAddress.get(params.id)
        if (!companyProfileAddressInstance) {
            respondNotFound COMPANY_PROFILE_ADDRESS_LABEL
            return
        }

        if (params.version != null) {
            if (companyProfileAddressInstance.version > params.long('version')) {
                respondConflict(companyProfileAddressInstance)
                return
            }
        }

        companyProfileAddressInstance.properties = request.GSON

        if (companyProfileAddressInstance.save(flush: true)) {
            respondUpdated companyProfileAddressInstance
        } else {
            respondUnprocessableEntity companyProfileAddressInstance
        }
    }

    def delete() {
        def companyProfileAddressInstance = CompanyProfileAddress.get(params.id)
        if (!companyProfileAddressInstance) {
            respondNotFound COMPANY_PROFILE_ADDRESS_LABEL
            return
        }

        try {
            companyProfileAddressInstance.delete(flush: true)
            respondDeleted(COMPANY_PROFILE_ADDRESS_LABEL)
        } catch (DataIntegrityViolationException e) {
            respondNotDeleted COMPANY_PROFILE_ADDRESS_LABEL
        }
    }
}

package com.cogda.domain.admin

import com.cogda.domain.onboarding.RegisterService
import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.CustomerAccount
import org.springframework.transaction.annotation.Transactional

/**
 * AdminService
 * A service class encapsulates the core business logic of a Grails application
 */
class AdminService {

    RegisterService registerService


    static transactional = true

    @Transactional(readOnly = true)
    def listRegistrations(params) {
        CustomerAccount.withoutTenantRestriction {
            params.fetch = [emailConfirmationLogs: "eager"]
            return registerService.list(params)
        }
    }

    @Transactional(readOnly = true)
    def findRegistrationById(Long id) {
        def params = [fetch: [emailConfirmationLogs: "eager"]]
        CustomerAccount.withoutTenantRestriction {
            return registerService.findById(id, params)
        }
    }

    def saveRegistration(Registration registration) {
        CustomerAccount.withoutTenantRestriction {
            return registerService.save(registration)
        }
    }

    def updateRegistration(Registration registration) {
        CustomerAccount.withoutTenantRestriction {
            return registerService.update(registration)
        }
    }

    def approve(long id) {
        CustomerAccount.withoutTenantRestriction {
            registerService.approve(id)
        }
    }
}

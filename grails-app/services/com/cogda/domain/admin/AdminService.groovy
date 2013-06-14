package com.cogda.domain.admin

import com.cogda.domain.onboarding.RegisterService
import com.cogda.domain.onboarding.Registration
import com.cogda.errors.RegistrationException
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import org.springframework.transaction.annotation.Transactional

/**
 * AdminService
 * A service class encapsulates the core business logic of a Grails application
 */
class AdminService {

    RegisterService registerService
    CustomerAccountService customerAccountService


    static transactional = true

    @Transactional(readOnly = true)
    List<Registration> listRegistrations(params) {
        CustomerAccount.withoutTenantRestriction {
            params.fetch = [emailConfirmationLogs: "eager"]
            return registerService.list(params)
        }
    }

    @Transactional(readOnly = true)
    Registration findRegistrationById(Long id) {
        def params = [fetch: [emailConfirmationLogs: "eager"]]
        CustomerAccount.withoutTenantRestriction {
            return registerService.findById(id, params)
        }
    }

    @Transactional(readOnly = true)
    Registration findRegistrationByToken(String token) {
        def params = [fetch: [emailConfirmationLogs: "eager"]]
        CustomerAccount.withoutTenantRestriction {
            return registerService.findByToken(token, params)
        }
    }

    Registration saveRegistration(Registration registration) {
        CustomerAccount.withoutTenantRestriction {
            return registerService.save(registration)
        }
    }

    def updateRegistration(long id, Registration registration) {
        CustomerAccount.withoutTenantRestriction {
            return registerService.update(id, registration)
        }
    }

    def approveRegistration(long id) {
        CustomerAccount.withoutTenantRestriction {
            registerService.approve(id)
        }
    }

    def updateSubdomain(Long id, String subDomain) {
        CustomerAccount.withoutTenantRestriction {
            Registration registration = registerService.get(id)
            if (!registration)
                throw new RegistrationException("Registration not found.")
            registration.subDomain = subDomain
            registration = registerService.update(id, registration)
            if (registration)
                return registration.subDomain
        }
    }
}

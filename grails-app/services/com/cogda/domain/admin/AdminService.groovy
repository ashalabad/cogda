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


    static transactional = true

    @Transactional(readOnly = true)
    List<Registration> listRegistrations(params) {
        params.fetch = [emailConfirmationLogs: "eager"]
        return registerService.list(params)
    }

    @Transactional(readOnly = true)
    Registration findRegistrationById(Long id) {
        def params = [fetch: [emailConfirmationLogs: "eager"]]
        return registerService.findById(id, params)
    }

    @Transactional(readOnly = true)
    Registration findRegistrationByToken(String token) {
        def params = [fetch: [emailConfirmationLogs: "eager"]]
        return registerService.findByToken(token, params)
    }

    Registration saveRegistration(Registration registration) {
        return registerService.save(registration)
    }

    def updateRegistration(long id, Registration registration) {
        return registerService.update(id, registration)
    }

    def approveRegistration(long id) {
        registerService.approve(id)
    }

    def updateSubdomain(Long id, String subDomain) {
        Registration registration = registerService.get(id)
        if (!registration)
            throw new RegistrationException("Registration not found.")
        registration.subDomain = subDomain
        registration = registerService.update(id, registration)
        if (registration)
            return registration.subDomain
    }
}

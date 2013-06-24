package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.errors.RegistrationException
import com.cogda.multitenant.CustomerAccountService

/**
 * RegisterService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegisterService {

    CustomerAccountService customerAccountService
    def messageSource

    /**
     * Save a com.cogda.domain.onboarding.Registration object
     */
    def save(Registration registration) {
        return registration.save(flush: true)
    }

    def list(params) {
        Registration.list(params)
    }

    def findById(Long id) {
        def registration = Registration.findById(id)
        return registration
    }

    def findById(long id, Map params) {
        def registration = Registration.findById(id, params)
        return registration
    }

    def approve(Registration registrationInstance) {
        if (registrationInstance.registrationStatus != RegistrationStatus.AWAITING_ADMIN_APPROVAL) {
            registrationInstance.errors.rejectValue('registrationStatus', 'registration.status.incorrectstate',
                    [messageSource.getMessage('registration.label', null, 'Registration', Locale.default)] as Object[],
                    'Registration status must be Awaiting Admin Approval.')
        } else if (!registrationInstance.subDomain) {
            registrationInstance.errors.rejectValue('subDomain', 'registration.subdomain.approval',
                    [messageSource.getMessage('registration.label', null, 'Registration', Locale.default)] as Object[],
                    'Registration status must have valid subdomain before approval.')
        } else {
            registrationInstance.registrationStatus = RegistrationStatus.APPROVED
            if (!registrationInstance.save(flush: true)) {
                registrationInstance.errors.each {
                    log.error(it)
                }
            } else {
                customerAccountService.onboardCustomerAccount(registrationInstance)
            }
        }
    }

    def findByToken(String token, Map params) {
        def registration = Registration.findByToken(token, params)
        return registration
    }

    def get(long id) {
        def registration = Registration.get(id)
        return registration
    }

    int count() {
        return Registration.count
    }
}

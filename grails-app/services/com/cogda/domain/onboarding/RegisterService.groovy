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

    /**
     * Save a com.cogda.domain.onboarding.Registration object
     */
    def save(Registration registration) {
        if (!registration.save(flush: true, failOnError: true)) {
            registration.errors.each {
                log.error(it)
            }
        }
        return registration
    }

    def list(params) {
        Registration.list(params)
    }

    def update(long id, Registration registration) {
        def instance = Registration.get(id)
        if (!instance) {
            throw new RegistrationException("Registration not found.")
        } else {
            instance.setProperties(registration.properties)
            if (!instance.validate()) {
                instance.errors.each {
                    log.error(it)
                }
            }
            if (!instance.save(flush: true, failOnError: true)) {
                instance.errors.each {
                    log.error(it)
                }
            }
            return instance
        }
    }

    def findById(Long id) {
        def registration = Registration.findById(id)
        return registration
    }

    def findById(long id, Map params) {
        def registration = Registration.findById(id, params)
        return registration
    }

    def approve(long id) {
        Registration registration = Registration.get(id)
        if (!registration) {
            throw new RegistrationException("Registration not found.")
        } else if (!registration.registrationStatus) {
            throw new RegistrationException("Registration status is empty")
        } else if (registration.registrationStatus != RegistrationStatus.AWAITING_ADMIN_APPROVAL) {
            throw new RegistrationException("Registration status is not in correct status")
        } else if (!registration.subDomain) {
            throw new RegistrationException("Registration must have sub domain before approval")
        } else {
            registration.registrationStatus = RegistrationStatus.APPROVED
            if (!registration.save(flush: true)) {
                registration.errors.each {
                    log.error(it)
                }
            }
            else {
                customerAccountService.onboardCustomerAccount(registration)
            }
        }
    }

    def findByToken(String token, Map params) {
        def registration = Registration.findByToken(token, params)
        return registration
    }

    def get(long id){
        def registration = Registration.get(id)
        return registration
    }
}

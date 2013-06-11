package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.errors.RegistrationException


/**
 * RegisterService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegisterService {

    /**
     * Save a com.cogda.domain.onboarding.Registration object
     */
    def save(Registration registration) {
        registration.save()
    }

    def list(params) {
        Registration.list(params)
    }

    def update(Registration registration) {
        def instance = Registration.get(registration.id)
        if (!instance) {
            throw new RegistrationException("Registration not found.", null, null)
        } else {
            instance.setProperties(registration.properties)
            if (!instance.save()) {
                instance.errors.each {
                    log.error(it)
                }
                throw new RegistrationException("Registration update failed.", instance.errors, instance)
            }
            return instance
        }
    }

    def findById(id, params) {
        def registration = Registration.findById(id, params)
        return registration
    }

    def approve(long id) {
        def registration = Registration.get(id)
        if (!registration) {
            throw new RegistrationException("Registration not found.", null, null)
        } else if (!registration.registrationStatus) {
            throw new RegistrationException("Registration status is empty.", registration.getErrors(), registration)
        } else if (registration.registrationStatus != RegistrationStatus.AWAITING_ADMIN_APPROVAL) {
            throw new RegistrationException("Registration status is not in correct status",null, null)
        } else {
            registration.registrationStatus = RegistrationStatus.APPROVED
            if (!registration.save()) {
                registration.errors.each {
                    log.error(it)
                }
                throw new RegistrationException("Registration approve failed", registration.errors, registration)
            }
        }
    }
}

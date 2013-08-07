package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.multitenant.CustomerAccountService

/**
 * RegistrationService
 * A service class encapsulates the core business logic of a Grails application
 */
class RegistrationService {
    /**
     * Injects CustomerAccountService into the RegistrationService class
     */
    CustomerAccountService customerAccountService

    /**
     * Rejects a Registration
     */
    public void rejectRegistration(Registration registration) {
        registration.registrationStatus = RegistrationStatus.REJECTED
        registration.save()
    }

    /**
     * Approves a Registration and then triggers the onboarding process
     * via the CustomerAccountService.onboardCustomerAccount method.
     *
     * @see CustomerAccountService
     */
    public void approveRegistration(Registration registration) {
        registration.registrationStatus = RegistrationStatus.APPROVED
        if(registration.save()){
            // Approval Triggers the onboardCustomerAccount method
            customerAccountService.onboardCustomerAccount(registration)
        }
    }
}

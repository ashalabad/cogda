package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.multitenant.CustomerAccountService
import org.grails.datastore.mapping.query.api.Criteria

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

    /**
     * Registration search method
     * ATTENTION: Testing is ongoing
     * TODO: Complete testing of the search method and refine this method to search for RegistrationStatus as well.
     */
    public List search(params){
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  // always pass in max so you get a PagedResultSet returned.
        params.offset = params.offset ? params.int('offset') : 0
        params.sort = params.sort ?: 'dateCreated'
        params.order  = params.order  ?: 'desc'

        String q = params.q?.trim()
        if(!q){
            return Registration.list(params)
        }else{
            q += "%"
        }

        def c = Registration.createCriteria()
        List registrations = c.list(params.max, params.offset){
            or {
                ilike("firstName", q)
                ilike("lastName", q)
                ilike("username", q)
                ilike("emailAddress", q)
                ilike("companyName", q)
                companyType {
                    ilike("code", q)
                }
            }
            order(params.sort, params.order)
        }

        return registrations
    }
}

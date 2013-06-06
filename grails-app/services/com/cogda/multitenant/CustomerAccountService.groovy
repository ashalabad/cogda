package com.cogda.multitenant

import com.cogda.errors.CustomerAccountCreationException

/**
 * CustomerAccountService
 * A service class encapsulates the core business logic of a Grails application
 */
class CustomerAccountService {

    static transactional = true

    /**
     * Creates a new CustomerAccount
     * @param customerAccount
     */
    def create(CustomerAccount customerAccount){
        if(!customerAccount.save(insert:true)){
            throw new CustomerAccountCreationException("Error creating a new CustomerAccount", customerAccount.getErrors(), customerAccount)
        }
    }
}

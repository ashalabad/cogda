package com.cogda.domain.onboarding

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

    def list(params){
        Registration.list(params)
    }


}

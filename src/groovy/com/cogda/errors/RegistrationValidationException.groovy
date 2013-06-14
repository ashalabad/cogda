package com.cogda.errors

import com.cogda.domain.onboarding.Registration
import grails.validation.ValidationException
import org.springframework.validation.Errors

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/11/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
class RegistrationValidationException extends ValidationException{
    Registration registration

    RegistrationValidationException(String msg, Errors e, Registration registration) {
        super(msg, e)
        this.registration = registration
    }
}

package com.cogda.errors

import com.cogda.domain.onboarding.Registration
import grails.validation.ValidationException
import org.springframework.validation.Errors

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 6/11/13
 * Time: 1:52 AM
 * To change this template use File | Settings | File Templates.
 */
class RegistrationException extends ValidationException {
    Registration registration
    Errors errors
    String fullMessage

    RegistrationException(String msg, Errors e, Registration r) {
        super(msg, e)
        registration = r
    }
}

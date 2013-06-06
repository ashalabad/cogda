package com.cogda.errors

import com.cogda.multitenant.CustomerAccount
import grails.validation.ValidationException
import org.springframework.validation.Errors

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/5/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
class CustomerAccountCreationException extends ValidationException{
    CustomerAccount customerAccount
    Errors errors
    String fullMessage

    CustomerAccountCreationException(String msg, Errors e, CustomerAccount c) {
        super(msg, e)
        customerAccount = c
    }
}

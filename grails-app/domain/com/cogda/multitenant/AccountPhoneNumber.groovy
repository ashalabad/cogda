package com.cogda.multitenant

import com.cogda.domain.PhoneNumber
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountPhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountPhoneNumber {
    Account account

    boolean primaryPhoneNumber

    String description

    PhoneNumber phoneNumber

    static embedded = ['phoneNumber']

    static belongsTo	= [account:Account]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static constraints = {
        primaryPhoneNumber(nullable:true)
        description(nullable:true)
        phoneNumber(nullable:true)
    }
}

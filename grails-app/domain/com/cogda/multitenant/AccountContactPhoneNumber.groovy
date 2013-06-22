package com.cogda.multitenant

import com.cogda.domain.PhoneNumber
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountContactPhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountContactPhoneNumber {

    boolean primaryPhoneNumber

    String description

    String phoneNumber

    static belongsTo	= [accountContact:AccountContact]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static constraints = {
        primaryPhoneNumber(nullable:true)
        description(nullable:true)
        phoneNumber(nullable: false, blank:false)
    }
}

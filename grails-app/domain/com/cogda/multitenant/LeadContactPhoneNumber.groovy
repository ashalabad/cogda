package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadContactPhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadContactPhoneNumber {

    boolean primaryPhoneNumber

    String description

    String phoneNumber

    static belongsTo	= [leadContact:LeadContact]

    static constraints = {
        description(nullable:true)
        phoneNumber(nullable: false, blank:false)
    }
}

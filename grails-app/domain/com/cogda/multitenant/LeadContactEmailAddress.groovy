package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadContactEmailAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadContactEmailAddress {

    static belongsTo	= [leadContact:LeadContact]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    Long	id
    Long	version

    String emailAddress

    boolean primaryEmailAddress

    Date	dateCreated
    Date	lastUpdated

    static constraints = {
        emailAddress(blank:false, nullable:false, email:true)
    }
}

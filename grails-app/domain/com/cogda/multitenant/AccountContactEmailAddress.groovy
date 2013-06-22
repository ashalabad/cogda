package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountContactEmailAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountContactEmailAddress {

    static belongsTo	= [accountContact:AccountContact]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * String emailAddress - the email address for the account
     */
    String emailAddress

    /**
     * boolean primaryEmailAddress
     * Only one AccountContactEmailAddress may be marked with the primaryAddress = true
     */
    boolean primaryEmailAddress

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        emailAddress(nullable:false, email:true)
        primaryEmailAddress(nullable:true)
    }

}

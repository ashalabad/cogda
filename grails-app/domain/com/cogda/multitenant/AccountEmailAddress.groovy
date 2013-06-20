package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountEmailAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountEmailAddress {

    static belongsTo = [account:Account]

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * String emailAddress - the email address for the account
     */
    String emailAddress

    /**
     * boolean primaryEmailAddress
     * Only one AccountEmailAddress may be marked with the primaryAddress = true
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

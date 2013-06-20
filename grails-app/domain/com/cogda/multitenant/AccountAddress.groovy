package com.cogda.multitenant

import com.cogda.domain.Address
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountAddress {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Account account

    /**
     * Embedded Address
     */
    Address address

    /**
     * primaryAddress for this Account
     * Only one AccountAddress may be marked with the primaryAddress = true
     */
    boolean primaryAddress

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo = [account:Account]

    static embedded = ["address"]

    static constraints = {
         primaryAddress(nullable:true)
    }
}

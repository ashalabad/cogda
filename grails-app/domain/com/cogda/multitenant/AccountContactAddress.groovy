package com.cogda.multitenant

import com.cogda.domain.Address
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountContactAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountContactAddress {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static belongsTo	= [accountContact:AccountContact]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    /**
     * Embedded Address
     */
    Address address

    /**
     * primaryAddress for this Account
     * Only one AccountAddress may be marked with the primaryAddress = true
     */
    boolean primaryAddress

    static embedded = ["address"]

    static constraints = {
        primaryAddress(nullable:true)
    }
}

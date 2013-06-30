package com.cogda.multitenant

import com.cogda.domain.Address
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadContactAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadContactAddress {

    Long	id
    Long	version

    Date	dateCreated
    Date	lastUpdated

    static belongsTo	= [leadContact:LeadContact]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    Address address

    boolean primaryAddress

    static embedded = ["address"]

    static constraints = {
        primaryAddress(nullable:true)
        address(nullable: true) // since lead contacts won't necessarily have addresses
    }
}

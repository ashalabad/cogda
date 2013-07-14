package com.cogda.multitenant

import com.cogda.domain.Address
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadAddress
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadAddress {

    Long id
    Long version

    LeadAddressType leadAddressType

    Address address

    boolean primaryAddress

    Date dateCreated
    Date lastUpdated

    static belongsTo = [lead: Lead]

    static embedded = ['address']

    static constraints = {
        leadAddressType(nullable: true)
        address(nullable:true)
    }
}

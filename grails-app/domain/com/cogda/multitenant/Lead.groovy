package com.cogda.multitenant

import com.cogda.common.LeadType
import com.cogda.domain.admin.BusinessType
import com.cogda.domain.admin.NaicsCode
import com.cogda.domain.admin.SicCode
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * Lead
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class Lead {

    Account account
    BusinessType businessType
    NaicsCode naicsCode
    SicCode sicCode
    String clientId
    String ownerName
    String clientName
    String address1
    String address2
    String city
    String state
    String zipCode
    String county
    String country
    String firstName
    String lastName
    String emailAddress
    String phoneNumber

    Date lastUpdated
    Date dateCreated
    LeadType leadType


    static mapping = {
    }

    static constraints = {
        clientId(nullable: true)
        ownerName(nullable: true)
        account(nullable: true)
        businessType(nullable: true)
        naicsCode(nullable: true)
        sicCode(nullable: true)
        leadType(nullable: false)
        clientName(nullable: true)
        address1(nullable: true)
        address2(nullable:true)
        city(nullable:true)
        state(nullable:true)
        zipCode(nullable:true)
        county(nullable:true)
        country(nullable:true)
        firstName(nullable:false, blank:false, minSize:1)
        lastName(nullable:false, blank:false, minSize:1)
        emailAddress(nullable:false, email:true, blank:false)
        phoneNumber(nullable:true)
        businessType(nullable:true)
    }

    String getFullName(){
        return "${lastName}, ${firstName}"
    }

}

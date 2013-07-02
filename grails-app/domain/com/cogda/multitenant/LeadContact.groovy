package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadContact
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadContact {

/* Default (injected) attributes of GORM */
    Long id
    Long version

    String firstName
    String middleName
    String lastName

    Boolean primaryContact

    static hasMany = [leadContactAddresses: LeadContactAddress,
            leadContactEmailAddresses: LeadContactEmailAddress,
            leadContactPhoneNumbers: LeadContactPhoneNumber]

    static transients = ["fullName", "primaryEmailAddress", "primaryPhoneNumber"]

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    static belongsTo = [lead: Lead]    // tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.


    static constraints = {
        firstName(blank: false, nullable: false, size: 1..50)
        middleName(nullable: true)
        lastName(blank: false, nullable: false, size: 1..50)
    }

    String getFullName() {
        return "${lastName}, ${firstName}"
    }

    public String getLeadPrimaryEmailAddress() {
        LeadContactEmailAddress leadContactEmailAddress = this.leadContactEmailAddresses?.find {
            it.primaryEmailAddress == Boolean.TRUE
        }
        return leadContactEmailAddress?.emailAddress
    }

    public String getLeadPrimaryPhoneNumber() {
        LeadContactPhoneNumber leadContactPhoneNumber = this.leadContactPhoneNumbers?.find {
            it.primaryPhoneNumber == Boolean.TRUE
        }
        return leadContactPhoneNumber?.phoneNumber
    }
}

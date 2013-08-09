package com.cogda.multitenant

import com.cogda.common.LeadSubType
import com.cogda.common.LeadType
import com.cogda.domain.FileReference
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

    BusinessType businessType
    String clientId
    String clientName

    Date lastUpdated
    Date dateCreated
    LeadType leadType
    LeadSubType subType
    String customerServiceRepresentative

    static hasMany = [leadNotes: LeadNote,
            files: FileReference,
            leadContacts: LeadContact,
            leadAddresses: LeadAddress,
            linesOfBusiness: LeadLineOfBusiness,
            naicsCodes: NaicsCode,
            sicCodes: SicCode]

    static transients = ["primaryEmailAddress", "primaryLeadEmailAddress", "primaryLeadContact", "primaryLeadContactName"]

    static constraints = {
        clientId(blank: false, nullable: false, size: 1..50)
        clientName(blank:false, nullable: false, size: 1..50)
        businessType(nullable: false)
        subType(nullable: false)
        customerServiceRepresentative(nullable: true)
        linesOfBusiness(validator: {linesOfBusiness, Lead lead  ->
            if (lead.leadType == LeadType.PROSPECT)
                return linesOfBusiness?.size() > 0
        })
    }

    /**
     * Get the Primary Email Address for this Lead
     * @return String
     */
    public String getPrimaryEmailAddress(){
        return getPrimaryLeadEmailAddress()?.emailAddress
    }

    /**
     * Retrieves the primary Lead's primary LeadContactEmailAddress
     * @return LeadContactEmailAddress
     */
    public LeadContactEmailAddress getPrimaryLeadEmailAddress(){
        LeadContactEmailAddress leadContactEmailAddress = this.getPrimaryLeadContact()?.leadContactEmailAddresses?.find {
            it.primaryEmailAddress == Boolean.TRUE
        }
        return leadContactEmailAddress
    }

    /**
     * Retrieves the primary Lead contact's name.
     * @return String
     */
    public String getPrimaryLeadContactName(){
        return getPrimaryLeadContact()?.fullName
    }

    /**
     * Retrieves the LeadContact marked as primaryLead
     * @return LeadContact
     */
    public LeadContact getPrimaryLeadContact(){
        LeadContact leadContact = this.leadContacts?.find {
            it.primaryContact == Boolean.TRUE
        }

        return leadContact
    }

    /**
     * Retrieves the primary Lead's primary LeadContactPhoneNumber
     * @return LeadContactPhoneNumber
     */
    public LeadContactPhoneNumber getPrimaryLeadContactPhoneNumber(){
        LeadContactPhoneNumber leadContactPhoneNumber = getPrimaryLeadContact()?.leadContactPhoneNumbers?.find {
            it.primaryPhoneNumber == Boolean.TRUE
        }

        return leadContactPhoneNumber
    }

    /**
     * Retrieves the primary Lead's primary LeadContactPhoneNumber.phoneNumber
     * @return String
     */
    public String getPrimaryLeadContactPhoneNumberString(){
        return getPrimaryLeadContactPhoneNumber()?.phoneNumber
    }


    /**
     * Retrieves the primary Lead's primary LeadAddress
     * @return LeadAddress
     */
    public LeadAddress getPrimaryAddress(){
        LeadAddress leadAddress = this.leadAddresses?.find {
            it.primaryAddress == Boolean.TRUE
        }
        return leadAddress
    }

}

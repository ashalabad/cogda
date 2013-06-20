package com.cogda.multitenant

import com.cogda.domain.admin.AccountType
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * Account
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class Account {

    String accountName

    String accountCode

    /**
     * AccountType accountType
     *
     */
    AccountType accountType

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static hasMany = [accountContacts:AccountContact]

    static transients = ["primaryEmailAddress", "primaryAccountEmailAddress", "primaryAccountContact", "primaryAccountContactName"]

    static constraints = {
        accountName(nullable:false, blank:false, unique:"tenantId")
        accountCode(nullable:true)

    }

    /**
     * Get the Primary Email Address for this Account
     * @return String
     */
    public String getPrimaryEmailAddress(){
        return getPrimaryAccountEmailAddress()?.emailAddress
    }

    /**
     * Retrieves the primary account's primary AccountEmailAddress
     * @return AccountEmailAddress
     */
    public AccountEmailAddress getPrimaryAccountEmailAddress(){
        AccountEmailAddress accountEmailAddress = this.getPrimaryAccountContact()?.accountEmailAddresses?.find {
            it.primaryEmailAddress == Boolean.TRUE
        }
        return accountEmailAddress
    }

    /**
     * Retrieves the primary account contact's name.
     * @return String
     */
    public String getPrimaryAccountContactName(){
        return getPrimaryAccountContact()?.fullName
    }

    /**
     * Retrieves the AccountContact marked as primaryAccount
     * @return AccountContact
     */
    public AccountContact getPrimaryAccountContact(){
        AccountContact accountContact = this.accountContacts?.find {
            it.primaryContact == Boolean.TRUE
        }

        return accountContact
    }

    /**
     * Retrieves the primary account's primary AccountPhoneNumber
     * @return AccountPhoneNumber
     */
    public AccountPhoneNumber getPrimaryAccountContactPhoneNumber(){
        AccountPhoneNumber accountPhoneNumber = getPrimaryAccountContact()?.accountPhoneNumbers?.find {
            it.primaryPhoneNumber == Boolean.TRUE
        }

        return accountPhoneNumber
    }

    /**
     * Retrieves the primary account's primary AccountPhoneNumber.phoneNumber
     * @return String
     */
    public String getPrimaryAccountContactPhoneNumberString(){
        return getPrimaryAccountContactPhoneNumber()?.phoneNumber
    }
}

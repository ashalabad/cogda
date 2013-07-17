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

    Boolean isMarket
    Boolean active

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static hasMany = [accountContacts:AccountContact,accountAddresses:AccountAddress,accountNotes:AccountNote]

    static transients = ["primaryEmailAddress", "primaryAccountEmailAddress", "primaryAccountContact", "primaryAccountContactName"]

    static constraints = {
        accountName(nullable:false, blank:false) //TODO: Dropped unique, need to add custom validator. Refer to issue #27
        accountCode(nullable:true)
        isMarket(nullable:true)
        active(nullable:true)

    }

    String toString(){
        accountName
    }

    /**
     * Get the Primary Email Address for this Account
     * @return String
     */
    public String getPrimaryEmailAddress(){
        return getPrimaryAccountEmailAddress()?.emailAddress
    }

    /**
     * Retrieves the primary account's primary AccountContactEmailAddress
     * @return AccountContactEmailAddress
     */
    public AccountContactEmailAddress getPrimaryAccountEmailAddress(){
        AccountContactEmailAddress accountContactEmailAddress = this.getPrimaryAccountContact()?.accountContactEmailAddresses?.find {
            it.primaryEmailAddress == Boolean.TRUE
        }
        return accountContactEmailAddress
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
     * Retrieves the primary account's primary AccountContactPhoneNumber
     * @return AccountContactPhoneNumber
     */
    public AccountContactPhoneNumber getPrimaryAccountContactPhoneNumber(){
        AccountContactPhoneNumber accountContactPhoneNumber = getPrimaryAccountContact()?.accountContactPhoneNumbers?.find {
            it.primaryPhoneNumber == Boolean.TRUE
        }

        return accountContactPhoneNumber
    }

    /**
     * Retrieves the primary account's primary AccountContactPhoneNumber.phoneNumber
     * @return String
     */
    public String getPrimaryAccountContactPhoneNumberString(){
        return getPrimaryAccountContactPhoneNumber()?.phoneNumber
    }


    /**
     * Retrieves the primary account's primary AccountAddress
     * @return AccountAddress
     */
    public AccountAddress getPrimaryAddress(){
        AccountAddress accountAddress = this.accountAddresses?.find {
            it.primaryAddress == Boolean.TRUE
        }
        return accountAddress
    }
}

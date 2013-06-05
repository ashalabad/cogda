package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant


/**
 * CompanySettings
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class CompanySettings {

    Company company

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    boolean feinNumberRequired
    boolean allowViewOfChildSubmissions
    boolean clientIdRequired
    boolean contactFirstNameRequired
    boolean contactLastNameRequired
    boolean contactEmailRequired
    boolean contactPhoneRequired
    boolean customerServiceRequired
    boolean businessTypeRequired
    boolean sicCodeRequired
    boolean naicsCodeRequired
    boolean writingCompanyRequired
    boolean makeSubStatusNotificationAsNotRead
    boolean remindUserToUpdateMyStatus
    boolean logoutNotification
    boolean targetDateRequired
    boolean targetPremiumRequired

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo	= [company:Company]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static mapping = {


    }

    static constraints = {
        feinNumberRequired(nullable:true)
        allowViewOfChildSubmissions(nullable:true)
        clientIdRequired(nullable:true)
        contactFirstNameRequired(nullable:true)
        contactLastNameRequired(nullable:true)
        contactEmailRequired(nullable:true)
        contactPhoneRequired(nullable:true)
        customerServiceRequired(nullable:true)
        businessTypeRequired(nullable:true)
        sicCodeRequired(nullable:true)
        naicsCodeRequired(nullable:true)
        writingCompanyRequired(nullable:true)
        makeSubStatusNotificationAsNotRead(nullable:true)
        remindUserToUpdateMyStatus(nullable:true)
        logoutNotification(nullable:true)
        targetDateRequired(nullable:true)
        targetPremiumRequired(nullable:true)
    }

    /*
     * Methods of the Domain Class
     */
}

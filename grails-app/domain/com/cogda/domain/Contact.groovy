package com.cogda.domain

import com.cogda.common.GenderEnum

/**
 * Contact
 * A domain class describes the data object and it's mapping to the database
 */
class Contact {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    String jobTitle
    String website
    String companyName
    String firstName
    String middleName
    String lastName
    Date dateOfBirth
    GenderEnum gender
    String initials
    String title
    UserProfile userProfile  // Is this contact on Cogda?

    static hasMany		= [contactEmailAddresses:ContactEmailAddress,
            contactPhoneNumbers:ContactPhoneNumber,
            contactAddresses:ContactAddress]

    static constraints = {
        jobTitle(nullable:true)
        website(nullable:true)
        companyName(nullable:true)
        firstName(nullable:false, blank:false)
        middleName(nullable:true)
        lastName(nullable:false, blank:false)
        dateOfBirth(nullable:true)
        gender(nullable:true, inList:GenderEnum.values().toList())
        initials(nullable:true)
        title(nullable:true)
        userProfile(nullable:true)
    }

}

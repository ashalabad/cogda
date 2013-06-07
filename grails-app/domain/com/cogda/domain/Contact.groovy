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
    UserProfile userProfile
    Address workAddress
    Address homeAddress

    static constraints = {
        jobTitle(nullable:true)
        website(nullable:true)
        companyName(nullable:true)
        firstName(nullable:true)
        middleName(nullable:true)
        lastName(nullable:true)
        dateOfBirth(nullable:true)
        gender(nullable:true)
        initials(nullable:true)
        title(nullable:true)
        userProfile(nullable:true)
        workAddress(nullable:true)
        homeAddress(nullable:true)
    }

    /*
     * Methods of the Domain Class
     */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}

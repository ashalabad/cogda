package com.cogda.multitenant

import com.cogda.domain.UserProfile

/**
 * AccountContact
 * A domain class describes the data object and it's mapping to the database
 */
class AccountContact {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * If the AccountContact is on Cogda then they will
     * have a UserProfile on Cogda.
     * If the AccountContact is not on Cogda then they
     * will have to have the information added manually by
     * the user.
     */
    UserProfile userProfile

    /**
     * First name
     */
    String firstName

    /**
     * Middle name
     */
    String middleName

    /**
     * Last name
     */
    String lastName

    /**
     * Primary contact
     */
    Boolean primaryContact

    static hasMany = [accountContactAddresses:AccountContactAddress,
            accountContactEmailAddresses:AccountContactEmailAddress,
            accountContactPhoneNumbers:AccountContactPhoneNumber]

    static transients = ["fullName"]

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo	= [account:Account]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.


    static constraints = {
        userProfile(nullable: true)
        firstName(nullable:false, blank:false)
        middleName(nullable:true)
        lastName(nullable:false, blank:false)
        primaryContact(nullable:true)
    }

    /**
     * Gets the full name - lastName, firstName
     * @return String
     */
    String getFullName(){
        return "${lastName}, ${firstName}"
    }
}

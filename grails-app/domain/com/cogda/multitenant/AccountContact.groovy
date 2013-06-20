package com.cogda.multitenant

import com.cogda.domain.UserProfile
import com.cogda.domain.security.User

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
     *
     */
    String firstName

    /**
     *
     */
    String middleName

    /**
     *
     */
    String lastName

    static hasMany = [accountAddresses:AccountAddress,
            accountEmailAddresses:AccountEmailAddress,
            accountPhoneNumbers:AccountPhoneNumber]

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo	= [account:Account]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.


    static mapping = {

    }

    static constraints = {

    }
}

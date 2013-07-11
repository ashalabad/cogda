package com.cogda.domain

/**
 * CompanyProfileContact
 * <br>
 *
 * The UserProfile that is listed as a contact on the CompanyProfile page.
 *
 */
class CompanyProfileContact {

    static belongsTo = CompanyProfile

    UserProfile userProfile

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        userProfile(nullable:false)
    }

}

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

    /**
     * UserProfile of a person working at the CustomerAccount
     * that has this CompanyProfile
     */
    UserProfile userProfile

    /**
     * The CompanyProfile that this CompanyProfileContact belongs to.
     */
    CompanyProfile companyProfile

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        userProfile(nullable:false)
    }

}

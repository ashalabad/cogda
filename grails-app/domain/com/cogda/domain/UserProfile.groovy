package com.cogda.domain

import com.cogda.domain.security.User
import com.cogda.multitenant.Company
import groovy.transform.ToString

/**
 * UserProfile
 * The UserProfile must represent a User and must be available outside of the
 * tenancy.
 */
class UserProfile {

    User user

    Company company

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    Boolean published

    String firstName

    String middleName

    String lastName

    static transients   = ['companyProfile']

	static hasMany		= [userProfileEmailAddresses:UserProfileEmailAddress,
                           userProfilePhoneNumbers:UserProfilePhoneNumber,
                           userProfileAddresses:UserProfileAddress]	// tells GORM to associate other domain objects for a 1-n or n-m mapping

    static constraints = {
        firstName(nullable:false)
        middleName(nullable:true)
        lastName(nullable:false)
        company(nullable:true)
        user(nullable:true)
        published(nullable:true)
    }

    /**
     * Retrieves the CompanyProfile using the company
     * if the Company is available.
     * @return
     */
    CompanyProfile getCompanyProfile(){
        return company?.companyProfile
    }

    String toString(){
        """
        UserProfile:
        id: $id
        version: $version
        dateCreated: $dateCreated
        lastUpdated: $lastUpdated
        published: $published
        firstName: $firstName
        middleName: $middleName
        lastName: $lastName
        company: ${company?.id}
        user: ${user?.id}
        """
    }
}

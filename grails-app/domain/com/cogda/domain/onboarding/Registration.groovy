package com.cogda.domain.onboarding

import com.cogda.domain.admin.CompanyType

/**
 * Registration
 * A domain class describes the data object and it's mapping to the database
 */
class Registration {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    String firstName
    String lastName
    String username
    String emailAddress
    String password
    String companyName
    CompanyType companyType
    String companyTypeOther
    String phoneNumber
    String streetAddressOne
    String streetAddressTwo
    String zipcode
    String city
    String state
    String county
    String country
    Boolean newCompany

    static constraints = {
        firstName(nullable:false, blank:false)
        lastName(nullable:false, blank:false)
        username(nullable:false, blank:false)
        emailAddress(nullable:false, email:true, blank:false)
        password(nullable:false, blank:false)
        companyName(nullable:false, blank:false)
        newCompany(nullable:true)
        companyType(nullable:true)
        companyTypeOther(nullable:true)
        phoneNumber(nullable:true)
        streetAddressOne(nullable:true)
        streetAddressTwo(nullable:true)
        zipcode(nullable:true)
        city(nullable:true)
        state(nullable:true)
        county(nullable:true)
        country(nullable:true)
    }

}

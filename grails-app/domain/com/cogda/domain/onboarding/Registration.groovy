package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.security.User
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount

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
    Company existingCompany
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
    String subDomain
    RegistrationStatus registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION


    static constraints = {
        importFrom CustomerAccount, include: ["subDomain"]
        firstName(nullable:false, blank:false, minSize:1)
        lastName(nullable:false, blank:false, minSize:1)
        username(nullable:false, blank:false, minSize:2)
        emailAddress(nullable:false, email:true, blank:false)
        password(nullable:false, blank:false)
        companyName(nullable:false, blank:false, minSize:1)
        newCompany(nullable:true)
        companyType(nullable:false)
        existingCompany(nullable:true)
        companyTypeOther(nullable:true)
        phoneNumber(nullable:true)
        streetAddressOne(nullable:true)
        streetAddressTwo(nullable:true)
        zipcode(nullable:true)
        city(nullable:true)
        state(nullable:true)
        county(nullable:true)
        country(nullable:true)
        registrationStatus(nullable:false)
    }



}

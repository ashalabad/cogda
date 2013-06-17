package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.security.User
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.security.UserService

/**
 * Registration
 * A domain class describes the data object and it's mapping to the database
 */
class Registration {

    UserService userService

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
    String streetAddressThree
    String zipcode
    String city
    String state
    String county
    String country
    Boolean newCompany

    String token = UUID.randomUUID().toString().replaceAll('-', '')

    /**
     * subDomain will be the place where this new company's COGDA instance lives.
     * subDomain.cogda.com
     */
    String subDomain

    /**
     * The registrationStatus is the current status of this registration.
     */
    RegistrationStatus registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION

    static hasMany = [emailConfirmationLogs:EmailConfirmationLog]

    static transients = ['userService']

    static constraints = {
        firstName(nullable:false, blank:false, minSize:1)
        lastName(nullable:false, blank:false, minSize:1)
        username(nullable:false, blank:false, matches: "[A-Za-z0-9]+", minSize:2, validator: { val, obj ->
            if(!obj?.id || (obj?.id && obj.isDirty("username") && !obj.getPersistentValue("username").equals(val))){
                if(!obj.userService.availableUsername(val)){
                    return ['registration.username.taken']
                }
            }
        })
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
        streetAddressThree(nullable:true)
        zipcode(nullable:true)
        city(nullable:true)
        state(nullable:true)
        county(nullable:true)
        country(nullable:true)
        registrationStatus(nullable:false)
        subDomain(nullable:true, matches: "[A-Za-z0-9]+")
        token(nullable:false, blank:false)
    }

    /**
     * Retrieves a Registration domain class based on the status being equal to AWAITING_USER_EMAIL_CONFIRMATION and the
     * passed in token.
     * @param token
     * @return Registration
     */
    static Registration retrievePendingRegistrationByToken(String token){
        def c = Registration.createCriteria()
        Registration registration = c.get(){
            eq("token",token)
            eq("registrationStatus", RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        }
        return registration
    }

}

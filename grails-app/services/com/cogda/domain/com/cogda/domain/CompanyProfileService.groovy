package com.cogda.domain.com.cogda.domain

import com.cogda.domain.Address
import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.CompanyProfilePhoneNumber
import com.cogda.domain.PhoneNumber
import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.Company

/**
 * CompanyProfileService
 * A service class encapsulates the core business logic of a Grails application
 */
class CompanyProfileService {

    static transactional = true

    CompanyProfile createCompanyProfile(Company company, Registration registration){
        CompanyProfile companyProfile = new CompanyProfile()
        companyProfile.company = company
        companyProfile.companyType = registration.companyType

        // Save the Company Profile
        companyProfile.save(failOnError:true)

        CompanyProfileAddress companyProfileAddress = new CompanyProfileAddress()
        companyProfileAddress.address = new Address()
        companyProfileAddress.address.addressOne = registration.streetAddressOne
        companyProfileAddress.address.addressTwo = registration.streetAddressTwo
        companyProfileAddress.address.city = registration.city
        companyProfileAddress.address.country = registration.country
        companyProfileAddress.address.county = registration.county
        companyProfileAddress.address.state = registration.state
        companyProfileAddress.address.zipcode = registration.zipcode
        companyProfileAddress.primaryAddress = true
        companyProfileAddress.companyProfile = companyProfile

        companyProfile.addToCompanyProfileAddresses(companyProfileAddress)

        CompanyProfilePhoneNumber companyProfilePhoneNumber = new CompanyProfilePhoneNumber()
        companyProfilePhoneNumber.companyProfile = companyProfile
        companyProfilePhoneNumber.phoneNumber = new PhoneNumber(phoneNumber:registration.phoneNumber)
        companyProfilePhoneNumber.primaryPhoneNumber = true

        companyProfile.addToCompanyProfilePhoneNumbers(companyProfilePhoneNumber)
    }
}

package com.cogda.dto

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType

class RegistrationApprovalDto {
    Long   id
    Long   version
    Date   dateCreated
    Date   lastUpdated
    String firstName
    String lastName
    String username
    String emailAddress
    String companyName
    String companyTypeCode
    String companyTypeDescription
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
    String subDomain
    String registrationStatusValue
    Long existingCompanyId
}

package com.cogda.common

import com.cogda.BaseIntegrationTest
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.onboarding.Registration
import grails.converters.JSON
import grails.plugins.springsecurity.SpringSecurityService
import org.junit.Before
import org.junit.Test

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/18/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
class CustomObjectMarshallersTests extends BaseIntegrationTest{
    SpringSecurityService springSecurityService

    @Before
    void setup() {
        createCompanyTypes()
    }

    @Test
    void testRegistrationCustomObjectMarshaller() {
        def registration = new Registration()
        registration.firstName = "Bill"
        registration.lastName = "Alexander"
        registration.username = "administrator"
        registration.emailAddress = "chris@cogda.com"
        registration.password = springSecurityService.encodePassword("password")
        registration.companyName = "Liberty Mutual"
        registration.companyType = CompanyType.findByCode("Carrier")
        registration.existingCompany = null
        registration.companyTypeOther = null
        registration.phoneNumber = "706-255-9087"
        registration.streetAddressOne = "1 Press Place"
        registration.streetAddressTwo = "Suite 200"
        registration.streetAddressThree = "Office #17"
        registration.city = "Athens"
        registration.state = "GA"
        registration.zipcode = "30601"
        registration.county = "CLARKE"
        registration.registrationStatus = RegistrationStatus.APPROVED
        registration.subDomain = "libertymutual"
        def registrationJSON = registration as JSON
        println "as JSON: " + registrationJSON
        println "encodeAsJSON: " + registration.encodeAsJSON()
    }
}

package com.cogda.domain.onboarding

import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.security.UserService
import grails.test.mixin.domain.DomainClassUnitTestMixin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * TODO: Fix the RegisterControllerTests Save test does not work - figure out how to mock the importFrom Registration username constraint that uses the userService class.
 */
@TestMixin(DomainClassUnitTestMixin)
@TestFor(RegisterController)
class RegisterControllerTests {

    void setUp() {
        // Setup logic here

    }

    void tearDown() {
        // Tear down logic here
    }

    void testSave() {

        def registration = mockDomain(Registration)
        mockDomain(CustomerAccount)
        def companyTypeMock = mockDomain(CompanyType, [new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer"),
                new CompanyType(code:"Carrier", intCode:1, description: "Carrier"),
                new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer"),
                new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)")])
        mockDomain(SupportedCountryCode, [new SupportedCountryCode(countryCode:"usa", countryDescription:"United States"),
                new SupportedCountryCode(countryCode:"can", countryDescription:"Canada")])
        mockDomain(Company)
        def userServiceMock = mockFor(UserService, false)
        userServiceMock.demand.availableUsername(0..10) { String username ->
            true
        }
        registration.userService = userServiceMock.createMock()

        def registerCommandMock = mockCommandObject(RegisterCommand)
        registerCommandMock.userService = userServiceMock.createMock()

        params.firstName = "First Name"
        params.lastName = "Last Name"
        params.emailAddress = "ctkdev@gmail.com"
        params.username = "username"
        params.password = "password"
        params.passwordTwo = "password"
        params.existingCompanyId = "1"
        params.newCompany = "1"
        params.companyName = "Company Name"
        params.companyTypeId = "1"
        params.companyTypeOther = null
        params.phoneNumber = "7062559077"
        params.streetAddressOne = "Street Address One"
        params.streetAddressTwo = "Street Address Two"
        params.zipcode = "30605"
        params.city = "City"
        params.state = "GA"
        params.county = "Clarke"
        params.country = "usa"

        request.method = "POST"
        request.makeAjaxRequest()

//        controller.save()




    }
}

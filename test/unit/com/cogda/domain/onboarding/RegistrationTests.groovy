package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.security.UserService
import grails.test.mixin.domain.DomainClassUnitTestMixin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Registration)
@TestMixin(DomainClassUnitTestMixin)
class RegistrationTests {

    Registration registration

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

//    importFrom CustomerAccount, include: ["subDomain"]
//    firstName(nullable:false, blank:false, minSize:1)
//    lastName(nullable:false, blank:false, minSize:1)
//    username(nullable:false, blank:false, minSize:2)
//    emailAddress(nullable:false, email:true, blank:false)
//    password(nullable:false, blank:false)
//    companyName(nullable:false, blank:false, minSize:1)
//    newCompany(nullable:true)
//    companyType(nullable:false)
//    companyTypeOther(nullable:true)
//    phoneNumber(nullable:true)
//    streetAddressOne(nullable:true)
//    streetAddressTwo(nullable:true)
//    zipcode(nullable:true)
//    city(nullable:true)
//    state(nullable:true)
//    county(nullable:true)
//    country(nullable:true)
//    registrationStatus(nullable:false)


    /**
     * class Registration {
     * 	...
     *  firstName nullable:false
     *  lastName nullable:false
     *  username nullable:false
     *  emailAddress nullable:false
     *  password nullable:false
     *  companyName nullable:false
     *  newCompany nullable:true
     *  companyType nullable:false
     *  existingCompany nullable:true
     *  companyTypeOther nullable:true
     *  phoneNumber nullable:true
     *  streetAddressOne nullable:true
     *  streetAddressTwo nullable:true
     *  zipcode nullable:true
     *  city nullable:true
     *  state nullable:true
     *  county nullable:true
     *  country nullable:true
     *  registrationStatus nullable:false
     *  ...
     * }
     */
    void testNullable() {
        mockDomain(Registration)
        mockDomain(CustomerAccount)
        mockDomain(CompanyType)
        mockDomain(Company)
//        def userServiceMock = mockFor(UserService, true)
//        userServiceMock.demand.availableUsername { String username -> Boolean.TRUE}
        registration = new Registration()

        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..9) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.registrationStatus = null

        registration.token = null

        assertFalse registration.validate()
        assert "nullable" == registration.errors["firstName"].code
        assert "nullable" == registration.errors["lastName"].code
        assert "nullable" == registration.errors["username"].code
        assert "nullable" == registration.errors["emailAddress"].code
        assert "nullable" == registration.errors["password"].code
        assert "nullable" == registration.errors["companyName"].code
        assert "nullable" == registration.errors["companyType"].code
        assert "nullable" == registration.errors["registrationStatus"].code
        assert "nullable" == registration.errors["token"].code

        assert !registration.errors["subDomain"]
        assert !registration.errors["newCompany"]
        assert !registration.errors["companyTypeOther"]
        assert !registration.errors["existingCompany"]
        assert !registration.errors["phoneNumber"]
        assert !registration.errors["streetAddressOne"]
        assert !registration.errors["streetAddressTwo"]
        assert !registration.errors["streetAddressThree"]
        assert !registration.errors["zipcode"]
        assert !registration.errors["city"]
        assert !registration.errors["state"]
        assert !registration.errors["county"]
        assert !registration.errors["country"]

        registration.firstName = "firstName"
        registration.validate()
        assert !registration.errors["firstName"]

        registration.lastName = "lastName"
        registration.validate()
        assert !registration.errors["lastName"]

        registration.username = "username"
        registration.validate()
        assert !registration.errors["username"]

        registration.emailAddress = "emailAddress@email.com"
        registration.validate()
        assert !registration.errors["emailAddress"]

        registration.password = "password"
        registration.validate()
        assert !registration.errors["password"]

        registration.companyName = "companyName"
        registration.validate()
        assert !registration.errors["companyName"]

        registration.companyType = new CompanyType()
        registration.validate()
        assert !registration.errors["companyType"]

        registration.registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION
        registration.validate()
        assert !registration.errors["registrationStatus"]
    }

    /**
     * class Registration {
     * 	...
     *    firstName(nullable:false, blank:false, minSize:1)
     *    lastName(nullable:false, blank:false, minSize:1)
     *    username(nullable:false, blank:false, minSize:2)
     *    emailAddress(nullable:false, email:true, blank:false)
     *    password(nullable:false, blank:false)
     *    companyName(nullable:false, blank:false, minSize:1)
     *  ...
     * }
     */
    void testBlank(){
        mockDomain(Registration)
        mockDomain(CustomerAccount)
        mockDomain(CompanyType)
        mockDomain(Company)

        registration = new Registration()

        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..5) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.firstName = "  "
        registration.lastName = "  "
        registration.username = "  "
        registration.emailAddress = "  "
        registration.password = "  "
        registration.companyName = "  "
        registration.token = "  "

        assertFalse registration.validate()
        assert "blank" == registration.errors["firstName"].code
        assert "blank" == registration.errors["lastName"].code
        assert "blank" == registration.errors["username"].code
        assert "blank" == registration.errors["emailAddress"].code
        assert "blank" == registration.errors["password"].code
        assert "blank" == registration.errors["companyName"].code
        assert "blank" == registration.errors["token"].code

        registration.firstName = "firstName"
        registration.validate()
        assert !registration.errors["firstName"]

        registration.lastName = "lastName"
        registration.validate()
        assert !registration.errors["lastName"]

        registration.username = "username"
        registration.validate()
        assert !registration.errors["username"]

        registration.emailAddress = "emailAddress@email.com"
        registration.validate()
        assert !registration.errors["emailAddress"]

        registration.password = "password"
        registration.validate()
        assert !registration.errors["password"]

        registration.companyName = "companyName"
        registration.validate()
        assert !registration.errors["companyName"]
    }

    /**
     * class Registration {
     * 	...
     * emailAddress(nullable:false, email:true, blank:false)
     * ...
     * }
     */
    void testEmail(){
        mockDomain(Registration)
        mockDomain(CustomerAccount)
        mockDomain(CompanyType)
        mockDomain(Company)

        registration = new Registration()

        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..5) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.emailAddress = "c.com  "
        assertFalse registration.validate()
        assert "email.invalid" == registration.errors["emailAddress"].code

        registration.emailAddress = "pass@pass.com"
        registration.validate()
        assert !registration.errors["emailAddress"]
    }

    /**
     * firstName(nullable:false, blank:false, minSize:1)
     * lastName(nullable:false, blank:false, minSize:1)
     * username(nullable:false, blank:false, minSize:2)
     * companyName(nullable:false, blank:false, minSize:1)
     */
     void testMinSize(){
         registration = new Registration()

         def mockControl = mockFor(UserService, false)
         mockControl.demand.availableUsername(0..5) { String username ->
             true
         }
         registration.userService = mockControl.createMock()

         registration.firstName = "a"
         registration.validate()
         assert !registration.errors["firstName"]

         registration.lastName = "a"
         registration.validate()
         assert !registration.errors["lastName"]

         registration.username = "a"
         registration.validate()
         assert "minSize.notmet" == registration.errors["username"].code

         registration.username = "ab"
         registration.validate()
         assert !registration.errors["username"]

         registration.companyName = "a"
         registration.validate()
         assert !registration.errors["companyName"]
     }


}

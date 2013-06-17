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

     void testRetrievePendingRegistrationByToken(){
         mockDomain(CompanyType, [new CompanyType(code:"R", description: "R", intCode:0)])

         String token = UUID.randomUUID().toString().replaceAll('-', '')
         String pendingToken = UUID.randomUUID().toString().replaceAll('-', '')

         Registration storedRegistration = createValidRegistration()
         def mockControl = mockFor(UserService, false)
         mockControl.demand.availableUsername(0..5) { String username ->
             true
         }
         storedRegistration.userService = mockControl.createMock()
         storedRegistration.metaClass.isDirty = { String fieldName -> return false }


         // Set the fields that will be used in the tests
         storedRegistration.token = token
         storedRegistration.registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION

         Registration pendingRegistration = createValidRegistration()
         pendingRegistration.userService = mockControl.createMock()
         pendingRegistration.metaClass.isDirty = { String fieldName -> return false }

         pendingRegistration.registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
         pendingRegistration.token = pendingToken

         mockDomain(Registration, [storedRegistration, pendingRegistration])

         Registration r = Registration.retrievePendingRegistrationByToken(token)
         assert r, "Registration was not found"

         r = Registration.retrievePendingRegistrationByToken(pendingToken)
         assertNull "Registration was found and should not have been since it has an invalid status", r
     }

    Registration createValidRegistration(){
        Registration reg = new Registration()
        reg.firstName = "Christopher"
        reg.lastName = "Kwiatkowski"
        reg.username = "ctk"
        reg.emailAddress = "chris@cogda.com"
        reg.password = "939020kiddko2"
        reg.companyName = "Cogda Solutions, LLC."
        reg.companyType = CompanyType.list().first()
        reg.existingCompany = null
        reg.companyTypeOther = null
        reg.phoneNumber = "706-255-9087"
        reg.streetAddressOne = "1 Press Place"
        reg.streetAddressTwo = "Suite 200"
        reg.streetAddressThree = "Office #17"
        reg.city = "Athens"
        reg.state = "GA"
        reg.zipcode = "30601"
        reg.county = "CLARKE"
        reg.registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION
        reg.subDomain = "rais"

        return reg
    }
}

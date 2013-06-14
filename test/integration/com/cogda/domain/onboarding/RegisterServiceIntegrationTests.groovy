package com.cogda.domain.onboarding

import com.cogda.BaseIntegrationTest
import com.cogda.common.RegistrationStatus
import com.cogda.domain.*
import com.cogda.domain.admin.*
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.errors.RegistrationException
import com.cogda.errors.RegistrationValidationException
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import grails.validation.ValidationException
import groovy.sql.Sql
import org.hibernate.SessionFactory
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/11/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */

class RegisterServiceIntegrationTests extends BaseIntegrationTest {

    RegisterService registerService
    def dataSource

    @Rule
    public ExpectedException expectedException = ExpectedException.none()

    @Before
    void setUp() {
        deleteAllData(dataSource)
        createCompanyTypes()
        for (def i = 0; i < 5; i++)
            createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
    }

    @After
    void tearDown() {
        // Tear down logic here
        deleteAllData(dataSource)
//
//        Registration.withTransaction {
//            Registration.executeUpdate("delete from Registration")
//
//            UserRole.executeUpdate("delete from UserRole")
//            Role.executeUpdate("delete from Role")
//
//            UserProfileEmailAddress.executeUpdate("delete from UserProfileEmailAddress")
//            UserProfilePhoneNumber.executeUpdate("delete from UserProfilePhoneNumber")
//            UserProfile.executeUpdate("delete from UserProfile")
//            User.executeUpdate("delete from User")
//
//            CompanyProfileAddress.executeUpdate("delete from CompanyProfileAddress")
//            CompanyProfilePhoneNumber.executeUpdate("delete from CompanyProfilePhoneNumber")
//            CompanyProfile.executeUpdate("delete from CompanyProfile")
//            Company.executeUpdate("delete from Company")
//
//            CustomerAccount.executeUpdate("delete from CustomerAccount")
//            EmailConfirmationLog.executeUpdate("delete from EmailConfirmationLog")
//            CompanyType.executeUpdate("delete from CompanyType")
//            HtmlFragment.executeUpdate("delete from HtmlFragment")
//            NaicsCode.executeUpdate("delete from NaicsCode")
//            SicCode.executeUpdate("delete from SicCode")
//            SicCodeDivision.executeUpdate("delete from SicCodeDivision")
//            SupportedCountryCode.executeUpdate("delete from SupportedCountryCode")
//            SystemEmailMessageTemplate.executeUpdate("delete from SystemEmailMessageTemplate")
//        }
    }

    @Test
    void testListRegistrations() {
        log.debug("testLinkRegistrations begin.")
        def expectedRegistrations = Registration.list()
        def actualRegistrations = registerService.list()
        assert expectedRegistrations.size(), actualRegistrations.size()
        expectedRegistrations.each {
            assert expectedRegistrations.contains(it)
        }
        def maxArg = 1
        expectedRegistrations = Registration.list(max: maxArg)
        actualRegistrations = Registration.list(max: maxArg)
        assert expectedRegistrations.size(), actualRegistrations.size()


        maxArg = 20
        expectedRegistrations = Registration.list(max: maxArg)
        actualRegistrations = Registration.list(max: maxArg)
        assert expectedRegistrations.size(), actualRegistrations.size()
        log.debug("testLinkRegistrations end.")
    }

    @Test
    void testFindRegistrationById() {
        def expectedRegistrations = Registration.list()
        expectedRegistrations.each {
            def actualRegistration = registerService.findById(it.id)
            assert actualRegistration
            assert actualRegistration.token, actualRegistration.token
            assert actualRegistration.id, actualRegistration.token
        }
    }

    @Test
    void testFindRegistrationByIdBad() {
        def actualRegistration = registerService.findById(-1)
        assert !actualRegistration
    }

    @Test
    void testSaveRegistration() {
        def expectedRegistration = createValidRegistration("Abc123")
        registerService.save(expectedRegistration)
        Registration actualRegistration = Registration.findByToken(expectedRegistration.token)
        assert expectedRegistration, actualRegistration
    }

    @Test(expected = ValidationException.class)
    void testSaveInvalidRegistration() {
        def invalidRegistration = createInvalidRegistration()
        expectedException.expect(RegistrationValidationException)
        expectedException.expectMessage("Failed to save registration")
        registerService.save(invalidRegistration)
    }

    @Test
    void testUpdate() {
        def registrationToUpdate = Registration.first()
        String newUsername = "bananaphone"
        def originalVersion = registrationToUpdate.version
        registrationToUpdate.username = newUsername
        registerService.save(registrationToUpdate)
        def actualRegistration = Registration.get(registrationToUpdate.id)
        assert newUsername, actualRegistration.username
        assert originalVersion + 1, actualRegistration.version
    }

    @Test(expected = ValidationException.class)
    void testInvalidUpdate() {
        def registrationToUpdate = Registration.first()
        String originalFirstName = registrationToUpdate.firstName
        registrationToUpdate.firstName = null
        registerService.update(registrationToUpdate.id, registrationToUpdate)
        Registration actualRegistration = Registration.findByToken(registrationToUpdate.token)
        assert originalFirstName, actualRegistration.firstName
    }

    @Test(expected = RegistrationException.class)
    void testNotFoundUpdate() {
        def registrationToUpdate = createValidRegistration("bananas")
        registerService.update(-9, registrationToUpdate)
    }

    @Test(expected = RegistrationException)
    void testApproveNoDomain() {
        Registration registrationToUpdate = Registration.first()
        registerService.approve(registrationToUpdate.id)
    }

    @Test
    void testApprove() {
        Registration registrationToUpdate = Registration.first()
        registrationToUpdate.subDomain = "banana"
        registrationToUpdate.registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
        registerService.update(registrationToUpdate.id, registrationToUpdate)
        registerService.approve(registrationToUpdate.id)
        Registration actual = Registration.findById(registrationToUpdate.id)
        assert actual.registrationStatus, RegistrationStatus.APPROVED
    }

    def createValidRegistration(String username, RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
        Registration registration = createValidBaseRegistration(registrationStatus)
        registration.username = username
        return registration
    }

    def createInvalidRegistration() {
        return createValidBaseRegistration()
    }

    Registration createValidBaseRegistration(RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
        Registration registration = new Registration(registrationStatus: registrationStatus)
        registration.firstName = "Walnut"
        registration.lastName = "Banks"
        registration.emailAddress = "chris@cogda.com"
        registration.password = "939020kiddko2"
        registration.companyName = "Adgoc Solutions, LLC."
        registration.companyType = CompanyType.list().first()
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
        return registration
    }

    Registration createAndSaveValidRegistration(String username, RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
        Registration registration = createValidRegistration(username, registrationStatus)
        return saveRegistration(registration)
    }

    def saveRegistration(Registration registration) {
        log.debug("Saving Registration Domain Class")
        assert registration.save(), "Registration save failed: ${registration.errors}"
    }

    def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }


}

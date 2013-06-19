package com.cogda.api.admin

import com.cogda.BaseIntegrationTest
import com.cogda.common.RegistrationStatus
import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.admin.AdminService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.onboarding.RegisterService
import com.cogda.domain.onboarding.Registration
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import grails.validation.ValidationException
import org.codehaus.groovy.grails.plugins.springsecurity.AjaxAwareAuthenticationSuccessHandler
import org.codehaus.groovy.grails.web.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.context.MessageSource

class AdminRegisterControllerIntegrationTests extends BaseIntegrationTest {

    AdminService adminService
    RegisterService registerService
    ErrorMessageResolverService errorMessageResolverService
    MessageSource messageSource

    @Before
    void setUp() {
        // Setup logic here
        createCompanyTypes()
        for (def i = 0; i < 5; i++)
            createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))

    }

    @After
    void tearDown() {

    }

    @Test
    void testSuccessfulList() {
        def adminRegisterController = new AdminRegisterController()
        adminService.registerService = registerService
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        def expectedRegistrations = Registration.list()
        adminRegisterController.list()
        def results = adminRegisterController.response.contentAsString
        AjaxResponseDto actualResultsObj = JSON.parse(results)
        assert actualResultsObj
        assert actualResultsObj.success
        List<JSONObject> actualRegistrations = actualResultsObj.modelObject.registrationList
        assert Registration.count == actualRegistrations.size()
        println "size: " + actualRegistrations.size()
        expectedRegistrations.each { Registration expectedRegistration ->
            assert actualRegistrations.any { actualRegistration ->
                compareRegistrationToJson(expectedRegistration, actualRegistration)
            }
        }
    }

    boolean compareRegistrationToJson(Registration registration, JSONObject json) {
        compareRegistrationsJSON(JSON.parse(registration.encodeAsJSON()), json)
    }

    static boolean compareRegistrationsJSON(JSONObject a, JSONObject b) {
        return a.city == b.city &&
                a.companyName == b.companyName &&
                a.companyType == b.companyType &&
                a.companyTypeOther == b.companyTypeOther &&
                a.country == b.country &&
                a.county == b.county &&
                a.dateCreated == b.dateCreated &&
                a.emailAddress == b.emailAddress &&
                a.existingCompany == b.existingCompany &&
                a.firstName == b.firstName &&
                a.lastName == b.lastName &&
                a.phoneNumber == b.phoneNumber &&
                a.streetAddressOne == b.streetAddressOne &&
                a.streetAddressTwo == b.streetAddressTwo &&
                a.streetAddressThree == b.streetAddressThree &&
                a.zipcode == b.zipcode &&
                a.state == b.state &&
                a.newCompany == b.newCompany
    }

    boolean compareRegistrationsJSONExcludingExistingCompany(JSONObject a, JSONObject b) {
        assert a.city == b.city
        assert a.companyName == b.companyName
        assert a.companyType.id == b.companyType.id
        assert a.companyType.intCode == b.companyType.intCode
        assert a.companyTypeOther == b.companyTypeOther
        assert a.country == b.country
        assert a.county == b.county
        assert a.emailAddress == b.emailAddress
        assert a.firstName == b.firstName
        assert a.lastName == b.lastName
        assert a.phoneNumber == b.phoneNumber
        assert a.streetAddressOne == b.streetAddressOne
        assert a.streetAddressTwo == b.streetAddressTwo
        assert a.streetAddressThree == b.streetAddressThree
        assert a.zipcode == b.zipcode
        assert a.state == b.state
        assert a.newCompany == b.newCompany
        return true
    }

    boolean compareRegistrations(Registration a, Registration b) {
        return a.city == b.city &&
                a.companyName == b.companyName &&
                a.companyType == b.companyType &&
                a.companyTypeOther == b.companyTypeOther &&
                a.country == b.country &&
                a.county == b.county &&
                a.dateCreated == b.dateCreated &&
                a.emailAddress == b.emailAddress &&
                a.existingCompany == b.existingCompany &&
                a.firstName == b.firstName &&
                a.lastName == b.lastName &&
                a.phoneNumber == b.phoneNumber &&
                a.streetAddressOne == b.streetAddressOne &&
                a.streetAddressTwo == b.streetAddressTwo &&
                a.streetAddressThree == b.streetAddressThree &&
                a.zipcode == b.zipcode &&
                a.state == b.state &&
                a.newCompany == b.newCompany
    }



    @Test
    void testCreate() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        adminRegisterController.create()
        AjaxResponseDto response = adminRegisterController.response.json
        assert response.success == true
    }

    @Test
    void testShow() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration expectedRegistration = Registration.first()
        adminRegisterController.show(expectedRegistration.id)
        assert adminRegisterController.response.status == 200
        assert compareRegistrationToJson(expectedRegistration, adminRegisterController.response.json.registrationInstance)
    }

    @Test
    void testShowInvalid() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        adminRegisterController.show(-1)
        assert adminRegisterController.response.status == 404
    }

    @Test
    void testUpdateValid() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToUpdate = Registration.first()
        def registrationToUpdateJson = JSON.parse(registrationToUpdate.encodeAsJSON())
        def newSubDomain = 'bananaphone'
        registrationToUpdateJson.subDomain = newSubDomain
        adminRegisterController.request.json = registrationToUpdateJson
        adminRegisterController.update(registrationToUpdate.id)
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        assert ajaxResponseDto.success == true
        assert ajaxResponseDto.modelObject.registration.subDomain == newSubDomain
        assert Registration.get(registrationToUpdateJson.id).subDomain == newSubDomain
    }

    @Test(expected = ValidationException.class)
    void testUpdateInvalidDomain() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToUpdate = Registration.first()
        def registrationToUpdateJSON = JSON.parse(registrationToUpdate.encodeAsJSON())
        def newSubDomain = '@@@'
        def originalSubDomain = registrationToUpdate.subDomain
        registrationToUpdateJSON.subDomain = newSubDomain
        adminRegisterController.request.json = registrationToUpdateJSON
        adminRegisterController.update(registrationToUpdate.id)
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        assert ajaxResponseDto.success == false
        assert ajaxResponseDto.modelObject.subDomain == newSubDomain
        assert Registration.get(registrationToUpdateJSON.id).subDomain == originalSubDomain
    }

    @Test
    void testApproveValid() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToApprove = createAndSaveValidRegistration("kitty", RegistrationStatus.AWAITING_ADMIN_APPROVAL)
        registrationToApprove.subDomain = 'iliketurtles'
        registrationToApprove.save(flush: true, failOnError: true)
        adminRegisterController.approve(registrationToApprove.id)
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        assert ajaxResponseDto.success == true
        assert Registration.get(registrationToApprove.id).registrationStatus == RegistrationStatus.APPROVED
        assert ajaxResponseDto.messages.size() == 1
        assert ajaxResponseDto.messages.first() == messageSource.getMessage('registration.status.adminapproved', null, Locale.default)
    }

    @Test
    void testApproveInvalidSubdomain() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToApprove = createAndSaveValidRegistration("pussandboots", RegistrationStatus.AWAITING_ADMIN_APPROVAL)
        adminRegisterController.approve(registrationToApprove.id)
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        validateInvalidRegistration(ajaxResponseDto, registrationToApprove.id)
    }

    @Test
    void testApproveInvalidStatus() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToApprove = createAndSaveValidRegistration("fluffypants", RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        registrationToApprove.subDomain = 'iliketurtles'
        registrationToApprove.save(flush: true, failOnError: true)
        adminRegisterController.approve(registrationToApprove.id)
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        validateInvalidRegistration(ajaxResponseDto, registrationToApprove.id)
    }

    void validateInvalidRegistration(AjaxResponseDto ajaxResponseDto, Long registrationId) {
        assert ajaxResponseDto.success == false
        assert ajaxResponseDto.errors.error0 == messageSource.getMessage('registration.status.adminapprovedfailed', null, Locale.default)
        assert Registration.get(registrationId).registrationStatus != RegistrationStatus.APPROVED
    }

    @Test
    void testSave() {
        def adminRegisterController = new AdminRegisterController()
        adminService.registerService = registerService
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        def registrationToSaveJSON = """{
   "class":"com.cogda.domain.onboarding.Registration",
   "city":"Athens",
   "companyName":"Adgoc Solutions, LLC.",
   "companyType":{
      "class":"com.cogda.domain.admin.CompanyType",
      "id":4,
      "code":"Wholesaler (MGA, Broker)",
      "description":"Wholesaler (MGA, Broker)",
      "intCode":3,
   },
   "companyTypeOther":null,
   "country":null,
   "county":"CLARKE",
   "dateCreated":null,
   "emailAddress":"chris@cogda.com",
   "emailConfirmationLogs":null,
   "firstName":"Walnut",
   "lastName":"Banks",
   "lastUpdated":null,
   "newCompany":null,
   "password":"939020kiddko2",
   "phoneNumber":"706-255-9087",
   "registrationStatus":{
      "enumType":"com.cogda.common.RegistrationStatus",
      "name":"APPROVED"
   },
   "state":"GA",
   "streetAddressOne":"1 Press Place",
   "streetAddressThree":"Office #17",
   "streetAddressTwo":"Suite 200",
   "subDomain":null,
   "token":"ed6ce98f1e864dcc9a9dd9add8be7824",
   "username":"whatever",
   "zipcode":"30601"
}"""
        def registrationToSaveJSONObj = JSON.parse(registrationToSaveJSON)
        registrationToSaveJSONObj.companyType.id = CompanyType.first().id
        adminRegisterController.request.json = registrationToSaveJSONObj.toString()
        adminRegisterController.save()
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        assert ajaxResponseDto.success == true
        assert Registration.get(ajaxResponseDto.modelObject.registration.id) != null
        assert compareRegistrationsJSONExcludingExistingCompany(registrationToSaveJSONObj, ajaxResponseDto.modelObject.registration)
    }

    @Test
    void testUpdateSubDomain() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToUpdate = Registration.first()
        def originalSubDomain = registrationToUpdate.subDomain
        def newSubDomain = 'doodoodoodoodoo'
        adminRegisterController.updateSubdomain(registrationToUpdate.id, newSubDomain)
        AjaxResponseDto ajaxResponseDto = adminRegisterController.response.json
        assert ajaxResponseDto != null
        assert ajaxResponseDto.success == true
        println ajaxResponseDto.modelObject
        assert ajaxResponseDto.messages.size() == 1
        assert ajaxResponseDto.messages[0] == messageSource.getMessage("registration.subdomain.successful", null, Locale.default)
        assert Registration.get(registrationToUpdate.id).subDomain != originalSubDomain
        assert Registration.get(registrationToUpdate.id).subDomain == newSubDomain
    }

    Registration createValidRegistration(String username, RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
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
        assert registration.save(flush: true, failOnError: true), "Registration save failed: ${registration.errors}"
        return registration
    }

    def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }
}

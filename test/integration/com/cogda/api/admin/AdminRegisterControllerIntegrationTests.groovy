package com.cogda.api.admin

import com.cogda.BaseIntegrationTest
import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.AdminService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.onboarding.RegisterService
import com.cogda.domain.onboarding.Registration
import com.cogda.util.ErrorMessageResolverService
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.reflect.TypeToken
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.context.MessageSource

import java.lang.reflect.Type

class AdminRegisterControllerIntegrationTests extends BaseIntegrationTest {

    AdminService adminService
    RegisterService registerService
    ErrorMessageResolverService errorMessageResolverService
    def gsonBuilder
    Gson gson

    @Before
    void setUp() {
        // Setup logic here
        gson = gsonBuilder.create()
        createCompanyTypes()
        for (def i = 0; i < 5; i++)
            createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
    }

    @After
    void tearDown() {

    }

    @Test
    void testGson() {
        Registration r = Registration.first()
        assert r != null
        def gson = gsonBuilder.create()
        def gs = gson.toJson(r)
        println gs
        Registration z = gson.fromJson(gs, Registration.class)
        println z
    }

    @Test
    void testSuccessfulList() {
        def adminRegisterController = new AdminRegisterController()
        adminService.registerService = registerService
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        def expectedRegistrations = Registration.list()
        adminRegisterController.list()
        Type listType = new TypeToken<ArrayList<Registration>>() {}.getType()
        List<Registration> actualRegistrations = gson.fromJson(adminRegisterController.response.json.toString(), listType)
        assert adminRegisterController.response.status == 200
        assert Registration.count == actualRegistrations.size()
        println "size: " + actualRegistrations.size()
        expectedRegistrations.each { Registration expectedRegistration ->
            assert actualRegistrations.any { actualRegistration ->
                compareRegistrations(expectedRegistration, actualRegistration)
            }
        }
    }

    static boolean assertRegistrationsEqual(Registration a, Registration b) {
        assert a.city == b.city
        assert a.companyName == b.companyName
        assert a.companyType == b.companyType
        assert a.companyTypeOther == b.companyTypeOther
        assert a.country == b.country
        assert a.county == b.county
        assert a.emailAddress == b.emailAddress
        assert a.existingCompany == b.existingCompany
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

    static boolean compareRegistrations(Registration a, Registration b) {
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
        assert adminRegisterController.response.status == 200
    }

    @Test
    void testShow() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration expectedRegistration = Registration.first()
        adminRegisterController.show(expectedRegistration.id)
        def actualRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 200
        assertRegistrationsEqual(expectedRegistration, actualRegistration)
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
        def newSubDomain = 'bananaphone'
        adminRegisterController.request.json = getGsonAddProperty(registrationToUpdate, 'subDomain', newSubDomain)
        adminRegisterController.update(registrationToUpdate.id)
        Registration actualRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 200
        assert actualRegistration.subDomain == newSubDomain
        assert Registration.get(registrationToUpdate.id).subDomain == newSubDomain
    }

    @Test
    void testUpdateInvalidDomain() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToUpdate = Registration.first()
        def newSubDomain = '@@@'
        def originalSubDomain = registrationToUpdate.subDomain
        adminRegisterController.request.json = getGsonAddProperty(registrationToUpdate, 'subDomain', newSubDomain)
        adminRegisterController.update(registrationToUpdate.id)
        Registration actualRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 422
        assert actualRegistration.subDomain != newSubDomain
        assert Registration.get(registrationToUpdate.id).subDomain == originalSubDomain
    }

    @Test
    void testApproveValid() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToApprove = createAndSaveValidRegistration("kitty", RegistrationStatus.AWAITING_ADMIN_APPROVAL)
        registrationToApprove.subDomain = 'iliketurtles'
        registrationToApprove.save(failOnError: true)
        adminRegisterController.approve(registrationToApprove.id)
        assert adminRegisterController.response.status == 200
        assert Registration.get(registrationToApprove.id).registrationStatus == RegistrationStatus.APPROVED
    }

    @Test
    void testApproveInvalidSubdomain() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToApprove = createAndSaveValidRegistration("pussandboots", RegistrationStatus.AWAITING_ADMIN_APPROVAL)
        adminRegisterController.approve(registrationToApprove.id)
        Registration responseRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 409
        assert responseRegistration.errors != null
    }

    @Test
    void testApproveInvalidStatus() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToApprove = createAndSaveValidRegistration("fluffypants", RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        registrationToApprove.subDomain = 'iliketurtles'
        registrationToApprove.save(failOnError: true)
        adminRegisterController.approve(registrationToApprove.id)
        Registration responseRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 409
        assert responseRegistration.errors != null
    }

    @Test
    void testSave() {
        def adminRegisterController = new AdminRegisterController()
        adminService.registerService = registerService
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToSave = createValidRegistration('idcccccc')
        def registrationToSaveGSON = gson.toJson(registrationToSave)
        adminRegisterController.request.json = registrationToSaveGSON
        adminRegisterController.save()
        Registration responseRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 201
        assert Registration.get(responseRegistration.id) != null
        assertRegistrationsEqual(registrationToSave, responseRegistration)
    }

    @Test
    void testUpdateSubDomain() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        Registration registrationToUpdate = Registration.first()
        def originalSubDomain = registrationToUpdate.subDomain
        def newSubDomain = 'doodoodoodoodoo'
        adminRegisterController.request.json = getGsonAddProperty(registrationToUpdate, 'subDomain', newSubDomain)
        adminRegisterController.update(registrationToUpdate.id)
        Registration responseRegistration = gson.fromJson(adminRegisterController.response.json.toString(), Registration)
        assert adminRegisterController.response.status == 200
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

    static def saveRegistration(Registration registration) {
        assert registration.save(failOnError: true), "Registration save failed: ${registration.errors}"
        return registration
    }

    def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }

    def compareRegistration(JsonObject a, Registration b) {
        compareItems(a.city, b.city)
        compareItems(a.companyName, b.companyName)
        compareItems(a.companyType.id, b.companyType.id)
        compareItems(a.companyType.intCode, b.companyType.intCode)
        compareItems(a.companyTypeOther, b.companyTypeOther)
        compareItems(a.country, b.country)
        compareItems(a.county, b.county)
        compareItems(a.emailAddress, b.emailAddress)
        compareItems(a.firstName, b.firstName)
        compareItems(a.lastName, b.lastName)
        compareItems(a.phoneNumber, b.phoneNumber)
        compareItems(a.streetAddressOne, b.streetAddressOne)
        compareItems(a.streetAddressTwo, b.streetAddressTwo)
        compareItems(a.streetAddressThree, b.streetAddressThree)
        compareItems(a.state, b.state)
        compareItems(a.newCompany, b.newCompany)
        return true
    }

    def compareItems(JsonPrimitive a, def b) {
        assert getValue(a) == b
    }

    def getValue(JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive == null)
            return null
        if (jsonPrimitive.isNumber())
            return jsonPrimitive.getAsNumber()
        if (jsonPrimitive.isString())
            return jsonPrimitive.getAsString()
        if (jsonPrimitive.isBoolean())
            return jsonPrimitive.getAsBoolean()
        return jsonPrimitive
    }

    String getGsonAddProperty(Object objectToAddTo, String propertyName, Object value) {
        JsonElement registrationGSON = gson.toJsonTree(objectToAddTo)
        registrationGSON.getAsJsonObject().addProperty(propertyName, value)
        return registrationGSON.toString()
    }
}

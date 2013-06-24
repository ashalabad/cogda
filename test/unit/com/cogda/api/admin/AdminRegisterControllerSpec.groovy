package com.cogda.api.admin

import com.cogda.BaseRegistrationSpec
import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.AdminService
import com.cogda.domain.onboarding.Registration
import com.cogda.security.UserService
import com.cogda.util.ErrorMessageResolverService
import com.google.gson.*
import grails.plugin.gson.test.GsonUnitTestMixin
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.apache.commons.lang.StringUtils

@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin, GsonUnitTestMixin])
@Mock([Registration])  //
class AdminRegisterControllerSpec extends BaseRegistrationSpec {

    AdminService adminService = Mock(AdminService)
    ErrorMessageResolverService errorMessageResolverService = Mock(ErrorMessageResolverService)
    def userService = Mock(UserService)
    def controller = testFor(AdminRegisterController)
    GsonBuilder gsonBuilder
    Gson gson
    def messageSource

    def setup() {

        controller.errorMessageResolverService = errorMessageResolverService
        grailsApplication.mainContext.bean
        userService.availableUsername(_) >> true
        adminService.listRegistrations(_) >> { Map v -> Registration.list(v) }
        adminService.updateSubdomain(_, _) >> { Long id, String subdomain -> subdomain }
        adminService.saveRegistration(_) >> { Registration registration ->
            registration.userService = userService
            return registration.save()
        }
        adminService.approveRegistration(_) >> { Registration registrationInstance ->
            if (registrationInstance.registrationStatus == RegistrationStatus.AWAITING_ADMIN_APPROVAL) {
                registrationInstance.registrationStatus = RegistrationStatus.APPROVED
                registrationInstance.save()
            } else {
                registrationInstance.errors.rejectValue('registrationStatus', 'registration.subdomain.approval')
            }
        }
        adminService.findRegistrationById(_) >> { long id ->
            return Registration.get(id)
        }
        controller.adminService = adminService
        gsonBuilder = applicationContext.getBean('gsonBuilder', GsonBuilder)
        gson = gsonBuilder.create()
    }


    def 'save action: valid registration'() {
        given:
        def registration = createValidRegistration('banana')
        def registrationGSON = gson.toJson(registration)
        request.json = registrationGSON

        when:
        controller.save()

        then:
        response.status == 201
        response.header("Location") == controller.createLink(action: 'show', id: response.GSON.id.getAsLong())
        with(response.GSON) {
            def registrationInstance = Registration.get(it.id.getAsLong())
            assert registrationInstance != null
            compareRegistration(it, registrationInstance)
            compareRegistration(registration, registrationInstance)
        }
    }

    def justSave() {
        setup:
        def registration = createValidRegistration('hello');
        registration.userService = userService
        registration.metaClass.isDirty = { String fieldName -> return false }
        assert registration.userService != null
        registration.save(failOnError: true)

        expect:
        Registration.count == 1
    }

    def 'list action: 2 registration and max = 1 '() {
        given:
        def registration1 = createValidRegistration('hmmzors')
        def registration2 = createValidRegistration('hmmzors2')
        saveRegistration(registration1)
        saveRegistration(registration2)
        controller.request.method = 'GET'
        def max = 1
        Map params = [max: max]
        controller.params.putAll(params)

        when:
        controller.list()

        then:
        response.status == 200
        with(response.GSON) {
            println 'response' + it
            it.size() == 1
            compareRegistration(it.first(), Registration.first())
        }
    }

    def 'list action: all registration'() {
        given:
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }
        controller.request.method = 'GET'

        when:
        controller.list()
        def actualRegistrations = Registration.list()

        then:
        response.status == 200
        with(response.GSON) {
            it.size() == Registration.count
            it.each {
                actualRegistrations.any {
                    Registration actualRegistration ->
                        compareRegistration(it, actualRegistration)
                }
            }
        }
    }

    def 'updateSubdomain action: valid subdomain'() {
        given:
        def registration1 = createAndSaveValidRegistration('hmmzors')
        def subDomain = 'yarly'
        controller.request.method = 'POST'
        JsonElement registrationGson = gson.toJsonTree(registration1)
        registrationGson.getAsJsonObject().addProperty('subDomain', subDomain)
        request.json = registrationGson.toString()

        when:
        controller.update(registration1.id)

        then:
        response.status == 200
        with(response.GSON) {
            getValue(it.subDomain) == subDomain
            Registration.get(it.id.getAsLong()).subDomain == subDomain
        }
    }

    def 'approve action'() {
        given:
        def registration1 = createValidRegistration('hmmzors', RegistrationStatus.AWAITING_ADMIN_APPROVAL)
        registration1.subDomain = 'goodsubdomain'
        saveRegistration(registration1)
        controller.request.method = 'POST'

        when:
        controller.approve(registration1.id)

        then:
        response.status == 200
        with(response.GSON) {
            getValue(it.registrationStatus) == RegistrationStatus.APPROVED.toString()
        }
    }

    def 'create action'() {
        when:
        controller.create()

        then:
        response.status == 200
        with(response.GSON) {
            getValue(it.registrationStatus) == RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION.toString()
            def token = getValue(it.token)
            token != null
            StringUtils.isNotEmpty(token)
            StringUtils.isNotBlank(token)
        }
    }

    def 'show action'() {
        given:
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }
        controller.request.method = 'GET'
        def expectedRegistration = Registration.first()

        when:
        controller.show(expectedRegistration.id)

        then:
        response.status == 200
        with(response.GSON) {
            compareRegistration(it, expectedRegistration)
        }
    }

    def 'show action: item not found'() {
        when:
        controller.show(-1)

        then:
        response.status == 404
    }

    def 'save action: invalidRegistration'() {
        given:
        def registration = createInvalidRegistration()
        def registrationGSON = gson.toJson(registration)
        request.json = registrationGSON

        when:
        controller.save()

        then:
        validateRespondUnprocessableEntity(response, registration)
        Registration.get(registration.id) == null
    }

    def 'update action: invalid update'() {
        given:
        def registration1 = createAndSaveValidRegistration('hmmzors')
        def subDomain = '@@@@'
        controller.request.method = 'POST'

        JsonElement registrationGson = gson.toJsonTree(registration1)
        registrationGson.getAsJsonObject().addProperty('subDomain', subDomain)
        request.json = registrationGson.toString()

        when:
        controller.update(registration1.id)

        then:
        validateRespondUnprocessableEntity(response, registration1)
    }

    def 'update action: item not found'() {
        given:
        createAndSaveValidRegistration('whatever')
        def registration = Registration.first()
        request.json = gson.toJson(registration)

        when:
        controller.update(-1)

        then:
        response.status == 404
    }


    def 'show action: invalid json'() {
        when:
        controller.update(1)

        then:
        validateRespondNotAcceptable(response)
    }

    def 'save action: invalid json'() {
        when:
        controller.save()

        then:
        validateRespondNotAcceptable(response)
    }

    def 'approve action: not found'() {
        when:
        controller.approve(-1)

        then:
        response.status == 404
    }

    def 'companyTypes action: valid'() {
        given:
        createCompanyTypes()

        when:
        controller.companyTypes()

        then:
        response.status == 200
        with(response.GSON) {
            it.size() == 4
        }
    }

    def 'companyTypes action: none found'() {
        when:
        controller.companyTypes()

        then:
        response.status == 404
    }

    def 'update action: version changed'() {
        given:
        def registration = createAndSaveValidRegistration('banana')
        def registrationGSON = gson.toJson(registration)
        request.json = registrationGSON
        registration.version = 1
        registration.save()
        params.version = 0

        when:
        controller.update(registration.id)

        then:
        response.status == 409
        with(response.GSON) {
            it.errors != null
        }
    }

    def 'approve action: invalid status'() {
        given:
        def registration = createAndSaveValidRegistration('whatever', RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        request.json = gson.toJson(registration)

        when:
        controller.approve(registration.id)

        then:
        response.status == 409
        with(response.GSON) {
            it.errors != null
        }
    }

    def validateRespondNotAcceptable(response) {
        assert response.status == 406
        assert response.contentLength == 0
        return true
    }

    def validateRespondUnprocessableEntity(response, Registration registration) {
        assert response.status == 422
        with(response.GSON) {
            assert it.errors != null
        }
        return true
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
}
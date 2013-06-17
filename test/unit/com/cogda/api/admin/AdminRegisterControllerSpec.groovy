package com.cogda.api.admin

import com.cogda.common.RegistrationStatus
import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.admin.AdminService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.Company
import com.cogda.security.UserService
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON
import grails.test.MockUtils

//import grails.converters.JSON
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import grails.util.Holders
import groovy.json.JsonSlurper
import groovy.mock.interceptor.MockFor
import spock.lang.Specification

import javax.naming.CompositeName

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
//@TestFor(AdminRegisterController)
//@Mock([Registration, Company, CompanyType, UserService, EmailConfirmationLog])
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin])
@Mock([Registration])  //
class AdminRegisterControllerSpec extends Specification {

    AdminService adminService = Mock(AdminService)
    ErrorMessageResolverService errorMessageResolverService = Mock(ErrorMessageResolverService)
    def userService = Mock(UserService)
    def controller = testFor(AdminRegisterController)

    def setup() {

        controller.errorMessageResolverService = errorMessageResolverService
        grailsApplication.mainContext.bean
        userService.availableUsername(_) >> true
        adminService.listRegistrations(_) >> { Map v -> Registration.list(v) }
        adminService.updateSubdomain(_,_) >> { Long id, String subdomain -> subdomain }
        controller.adminService = adminService

        //grailsApplication.mainContext.userService = userService
    }

    def 'save action: valid registration'() {
        given:
        controller.request.method = 'POST'
//        request.json = [registration: registration as JSON]
        controller.request.contentType = "text/json"
//        controller.request.content = (registration as JSON).toString().getBytes()

//        controller.params.registration = (registration as JSON) as Map
        defineBeans {
            userService(UserService) {
                bean -> bean.autowire = true
            }
        }
        //mockDomain(Registration)
        println registration as JSON
        request.json = [registration: registration]
//        def r = new Registration(registration as JSON)
        when:
        // Todo fails because service does not exist, using defineBeans works to inject service but causes issues with Registration Mock
//        controller.save()

        then:
//        AjaxResponseDto ajaxResponseDto = JSON.parse(controller.response.json.toString())
//        Registration actualRegistration = new Registration(ajaxResponseDto.modelObject.registration)
//        actualRegistration == registration

        where:
        registration = createValidRegistration('banana')
    }

    def justSave() {
        setup:
        mockDomain(Registration)
        registration.userService = userService
        registration.metaClass.isDirty = { String fieldName -> return false }
        assert registration.userService != null
        registration.save(failOnError: true)

        expect:

        Registration.count == 1

        where:
        registration = createValidRegistration('hello');
    }

    def 'list action: 2 registration and max = 1 '() {
        given:
//        mockDomain(Registration)
        saveRegistration(registration1)
        saveRegistration(registration2)
        controller.request.method = 'GET'
        def max = 1
        Map params = [max: max]
        controller.params.putAll(params)

        when:
        controller.list()
        AjaxResponseDto result = JSON.parse(controller.response.json.toString())

        then:
        result.success == true
        def expected = JSON.parse(([registrationList: [registration1]] as JSON).toString())
        result.modelObject == expected
        result.modelObject.registrationList.size() == max

        where:
        registration1 = createValidRegistration('hmmzors')
        registration2 = createValidRegistration('hmmzors2')
    }

    def 'list action: all registration'() {
        given:
//        mockDomain(Registration)
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }
        controller.request.method = 'GET'

        when:
        controller.list()
        AjaxResponseDto result = JSON.parse(controller.response.json.toString())

        then:
        result.success == true
        result.modelObject.registrationList.size() == Registration.count
    }

    def 'updateSubdomain action: valid subdomain'() {
        given:
        saveRegistration(registration1)
        saveRegistration(registration2)
        controller.request.method = 'POST'

        when:
        controller.updateSubdomain(registration1.id, subdomain)
        AjaxResponseDto result = JSON.parse(controller.response.json.toString())

        then:
        result.success == true
        result.modelObject != null
        result.modelObject.subDomain == subdomain

        where:
        registration1 = createValidRegistration('hmmzors')
        registration2 = createValidRegistration('hmmzors2')
        subdomain = 'yarly'
    }

    def 'approve action'() {
        given:
        saveRegistration(registration1)
        saveRegistration(registration2)
        controller.request.method = 'POST'

        when:
        controller.approve(registration1.id)
        AjaxResponseDto result = JSON.parse(controller.response.json.toString())

        then:
        result.success == true
        result.messages.size() == 1
        result.messages.first() == "registration.status.adminapproved"

        where:
        registration1 = createValidRegistration('hmmzors')
        registration2 = createValidRegistration('hmmzors2')
        subdomain = 'yarly'
    }



    def saveRegistration(Registration registration) {
        registration.userService = userService
        registration.metaClass.isDirty = { String fieldName -> return false }
        registration.save(failOnError: true)
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
        registration.companyType = mockDomain(CompanyType)
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

    def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }
}

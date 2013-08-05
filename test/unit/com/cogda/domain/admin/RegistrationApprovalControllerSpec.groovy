package com.cogda.domain.admin

import com.cogda.common.RegistrationStatus
import com.cogda.domain.CompanyProfile
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.onboarding.RegistrationService
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import com.cogda.security.UserService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.adapters.GrailsDomainDeserializer
import grails.plugin.gson.adapters.GrailsDomainSerializer
import grails.plugin.gson.api.ArtefactEnhancer
import grails.plugin.gson.spring.GsonBuilderFactory
import grails.plugin.gson.support.proxy.DefaultEntityProxyHandler
import grails.plugin.multitenant.core.CurrentTenant
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.*
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@ConfineMetaClassChanges(Registration)
@TestFor(RegistrationApprovalController)
@TestMixin([DomainClassUnitTestMixin, ControllerUnitTestMixin, ServiceUnitTestMixin])
@Mock([Registration, CustomerAccount, CustomerAccountService, Company, CompanyProfile, CompanyType, UserService, RegistrationService])
class RegistrationApprovalControllerSpec extends Specification{

    final String jsonContentType = "application/json"

    GsonBuilder gsonBuilder
    Gson gson

    String internalSubDomain =  "internalCustomerAccount"
    String subDomain =  "customerAccount"
    CustomerAccount internalCustomerAccount
    CustomerAccount customerAccount
    CustomerAccountService customerAccountService = new CustomerAccountService()
    CompanyType companyType
    UserService userService = new UserService()
    RegistrationService registrationService = new RegistrationService()
    RegistrationApprovalCommand registrationApprovalCommand = mockCommandObject RegistrationApprovalCommand

    void setupSpec() {
        defineBeans {
            proxyHandler DefaultEntityProxyHandler
            domainSerializer GrailsDomainSerializer, ref('grailsApplication'), ref('proxyHandler')
            domainDeserializer GrailsDomainDeserializer, ref('grailsApplication')
            gsonBuilder(GsonBuilderFactory) {
                pluginManager = ref('pluginManager')
            }
        }


    }

    def setup(){

        def looseUserServiceMock = mockFor(UserService, true)
        looseUserServiceMock.demand.availableUsername(1..15) { String username -> true }
        userService = looseUserServiceMock.createMock()
        gsonEnabledControllerSetup()

        internalCustomerAccount = new CustomerAccount(subDomain: internalSubDomain, internalSystemAccount:true).save()
        assert !internalCustomerAccount.hasErrors(), "${internalCustomerAccount.errors.allErrors}"

        customerAccount = new CustomerAccount(subDomain: subDomain).save()
        assert !customerAccount.hasErrors(), "${customerAccount.errors.allErrors}"

        controller.customerAccountService = customerAccountService

        companyType = new CompanyType()
        companyType.intCode = 0
        companyType.code = "MGA"
        companyType.description = "MGA"
        companyType.save(failOnError: true)

        // build and save some registration domain classes
        // 3 Registrations for newCompany = true, registrationStatus = AWAITING_USER_EMAIL_CONFIRMATION, subDomain = null
        3.times {
            def newCompany = true
            def registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION
            def subDomain = ""
            buildRegistration(newCompany, registrationStatus, subDomain)
        }

        // 3 Registrations for newCompany = true, registrationStatus = AWAITING_ADMIN_APPROVAL, subDomain = null
        3.times {
            def newCompany = true
            def registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
            def subDomain = ""
            buildRegistration(newCompany, registrationStatus, subDomain)
        }

        // 3 Registrations for newCompany = true, RegistrationStatus.REJECTED, subDomain = null
        3.times {
            def newCompany = true
            def registrationStatus = RegistrationStatus.REJECTED
            def subDomain = ""
            buildRegistration(newCompany, registrationStatus, subDomain)
        }

        // 3 Registrations for newCompany = false, registrationStatus = AWAITING_ADMIN_APPROVAL, subDomain = null
        3.times {
            def alphabet = ['A', 'B', 'C']
            def newCompany = false
            def registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
            def subDomain = null
            buildRegistration(newCompany, registrationStatus, subDomain)
        }

        // 3 Registrations that have been approved by an admin
        3.times {
            def alphabet = ['A', 'B', 'C']
            def newCompany = true
            def registrationStatus = RegistrationStatus.APPROVED
            def subDomain = "subDomain" + alphabet[it]
            buildRegistration(newCompany, registrationStatus, subDomain)
        }

        // Create a single existing CustomerAccount

    }

    def cleanup(){

    }

    def "verify beforeInterceptor prevents access to CustomerAccounts that are not internalSystemAccount"() {
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { customerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()

        when:
        Boolean validRequest = controller.beforeInterceptor()

        then:
        validRequest  == false
    }

    def "verify isValidRequest prevents access to CustomerAccounts that are not internalSystemAccount"() {
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { customerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()

        when:
        Boolean validRequest = controller.isValidRequest()

        then:
        validRequest  == false
    }

    def "verify beforeInterceptor allows access to CustomerAccounts that are internalSystemAccount"() {
        given: "a mock so that the currentTenant.get method will return the id of the internalCustomerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()

        when:
        Boolean validRequest = controller.beforeInterceptor()

        then:
        validRequest == true
    }

    def "verify isValidRequest allows access to CustomerAccounts that are internalSystemAccount"() {
        given: "a mock so that the currentTenant.get method will return the id of the internalCustomerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()

        when:
        Boolean validRequest = controller.isValidRequest()

        then:
        validRequest == true
    }

    def "verify list() returns registrations in json format"() {
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()

        when:
        controller.list()

        then:
        response.contentType.contains(jsonContentType)
        response.getHeader(X_PAGINATION_TOTAL).toInteger() == Registration.count()
        response.status == SC_OK
    }

    def "verify reject() updates the RegistrationStatus to REJECTED"() {
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()
        Registration registration = Registration.findByRegistrationStatus(RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        assert registration, "Registration was not found"
        Map registrationMap = [id:registration.id]
        request.json = gson.toJson(registrationMap)
        params.id = registration.id

        when:
        controller.reject()

        then:
        response.status == SC_OK
        response.json.registrationStatus == RegistrationStatus.REJECTED.toString()
    }

    def "verify approve() updates the RegistrationStatus to APPROVED "() {
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }

        def looseCustomerAccountServiceMock = mockFor(CustomerAccountService, true)
        looseCustomerAccountServiceMock.demand.onboardCustomerAccount(1..1) { Registration registration -> }

        registrationService.customerAccountService = looseCustomerAccountServiceMock.createMock()
        controller.registrationService = registrationService
        controller.currentTenant = looseCurrentTenantMock.createMock()

        Registration registration = Registration.findByRegistrationStatus(RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        assert registration, "Registration was not found"
        Map registrationMap = [id:registration.id, subDomain:"aNewSubDomain"]
        request.json = gson.toJson(registrationMap)
        params.id = registration.id

        when:
        controller.approve()

        then:
        response.status == SC_OK
        response.json.registrationStatus == RegistrationStatus.APPROVED.toString()
    }

    def "approve for an ID that does not exist returns 404"(){
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }

        def looseCustomerAccountServiceMock = mockFor(CustomerAccountService, true)
        looseCustomerAccountServiceMock.demand.onboardCustomerAccount(1..1) { Registration registration -> }

        registrationService.customerAccountService = looseCustomerAccountServiceMock.createMock()
        controller.registrationService = registrationService
        controller.currentTenant = looseCurrentTenantMock.createMock()

        Registration registration = Registration.findByRegistrationStatus(RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION)
        assert registration, "Registration was not found"
        Map registrationMap = [id:registration.id, subDomain:"aNewSubDomain"]
        request.json = gson.toJson(registrationMap)
        params.id = Long.MAX_VALUE

        when:
        controller.approve()

        then:
        response.status == SC_NOT_FOUND
        response.json.message == "default.not.found.message"
    }

    def "approve for an invalid ID and subDomain returns errors unprocessible entity 422"(){
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }

        def looseCustomerAccountServiceMock = mockFor(CustomerAccountService, true)
        looseCustomerAccountServiceMock.demand.onboardCustomerAccount(1..1) { Registration registration -> }

        registrationService.customerAccountService = looseCustomerAccountServiceMock.createMock()
        controller.registrationService = registrationService
        controller.currentTenant = looseCurrentTenantMock.createMock()

        Registration registration = Registration.findByRegistrationStatus(RegistrationStatus.APPROVED)
        assert registration, "Registration was not found"
        Map registrationMap = [id:registration.id, subDomain:"aNewSubDomain"]
        request.json = gson.toJson(registrationMap)
        params.id = registration.id

        when:
        controller.approve()

        then:
        response.status == SC_UNPROCESSABLE_ENTITY
        response.json.errors.id
    }

    def "get an existing Registration object"(){
        given: "an id of an existing Registration"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }
        Registration registration = Registration.findByRegistrationStatus(RegistrationStatus.APPROVED)
        params.id = registration.id

        when: "get is called"
        controller.get()

        then: "status 200 and domain class serialized correctly"
        response.status == SC_OK
        response.json.id                   == registration.id
        response.json.firstName            == registration.firstName
        response.json.lastName             == registration.lastName
    }

    def "delete an existing Regisrtation"(){
        given: "an existing id"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { internalCustomerAccount.id.toInteger() }
        Registration registration = Registration.findByRegistrationStatus(RegistrationStatus.APPROVED)
        params.id = registration.id

        when: " delete is called "
        controller.delete()

        then:
        response.status == SC_OK
        response.json.message
        response.json.message == "default.deleted.message"
    }

    def "verify nullable constraints on RegistrationApprovalCommand"(){
        given:
        RegistrationApprovalCommand registrationApprovalCommand = new RegistrationApprovalCommand()
        mockForConstraintsTests(RegistrationApprovalCommand, [registrationApprovalCommand])
        registrationApprovalCommand.id = null
        registrationApprovalCommand.subDomain = null

        expect:
        !registrationApprovalCommand.validate()
        "nullable" == registrationApprovalCommand.errors["id"]
        "nullable" == registrationApprovalCommand.errors["subDomain"]
    }

    def "verify blank constraints on RegistrationApprovalCommand"(){
        given:
        RegistrationApprovalCommand registrationApprovalCommand = new RegistrationApprovalCommand()
        mockForConstraintsTests(RegistrationApprovalCommand, [registrationApprovalCommand])
        registrationApprovalCommand.subDomain = ""

        expect:
        !registrationApprovalCommand.validate()
        "blank" == registrationApprovalCommand.errors["subDomain"]
    }

    def "verify validator constraints on RegistrationApprovalCommand subDomain"(){
        given:
        RegistrationApprovalCommand registrationApprovalCommand = new RegistrationApprovalCommand()
        mockForConstraintsTests(RegistrationApprovalCommand, [registrationApprovalCommand])
        registrationApprovalCommand.subDomain = internalSubDomain

        expect:
        !registrationApprovalCommand.validate()
        "registrationApproval.subDomain.already.exists" == registrationApprovalCommand.errors["subDomain"]
    }


    def "verify validator constraints on RegistrationApprovalCommand id does not exist"(){
        given:
        RegistrationApprovalCommand registrationApprovalCommand = new RegistrationApprovalCommand()
        mockForConstraintsTests(RegistrationApprovalCommand, [registrationApprovalCommand])
        registrationApprovalCommand.id = Long.MAX_VALUE

        expect:
        !registrationApprovalCommand.validate()
        "registrationApproval.id.doesnotexist" == registrationApprovalCommand.errors["id"]
    }

    def "verify validator constraints on RegistrationApprovalCommand id APPROVED"(){
        given:
        RegistrationApprovalCommand registrationApprovalCommand = new RegistrationApprovalCommand()
        mockForConstraintsTests(RegistrationApprovalCommand, [registrationApprovalCommand])
        registrationApprovalCommand.id = Registration.findByRegistrationStatus(RegistrationStatus.APPROVED).id

        expect:
        !registrationApprovalCommand.validate()
        "registrationApproval.registrationStatus.approved" == registrationApprovalCommand.errors["id"]
    }

    def "verify validator constraints on RegistrationApprovalCommand id REJECTED"(){
        given:
        RegistrationApprovalCommand registrationApprovalCommand = new RegistrationApprovalCommand()
        mockForConstraintsTests(RegistrationApprovalCommand, [registrationApprovalCommand])
        registrationApprovalCommand.id = Registration.findByRegistrationStatus(RegistrationStatus.REJECTED).id

        expect:
        !registrationApprovalCommand.validate()
        "registrationApproval.registrationStatus.rejected" == registrationApprovalCommand.errors["id"]
    }

    private void gsonEnabledControllerSetup(){
        GrailsApplication grailsApplication = applicationContext.getBean("grailsApplication", GrailsApplication)
        gsonBuilder = applicationContext.getBean('gsonBuilder', GsonBuilder)
        def domainDeserializer = applicationContext.getBean('domainDeserializer', GrailsDomainDeserializer)
        def enhancer = new ArtefactEnhancer(grailsApplication, gsonBuilder, domainDeserializer)
        enhancer.enhanceDomains()
        enhancer.enhanceRequest()
        controller.gsonBuilder = gsonBuilder
        gson = gsonBuilder.create()
    }

    private Registration buildRegistration(Boolean newCompany,
                                           RegistrationStatus registrationStatus,
                                           String subDomain = null){
        Registration registration = new Registration()
        registration.metaClass.isDirty = { String fieldName -> false }
        registration.metaClass.getPersistentValue = { String fieldName -> this."$fieldName" }

        registration.userService = userService

        registration.firstName = "firstName"
        registration.lastName  = "lastName"
        registration.username  = UUID.randomUUID().toString().replaceAll('-', '')
        registration.emailAddress = "email@cogda.com"
        registration.password     = "password"
        registration.companyName  = "Company Name"
        registration.companyType  = CompanyType.first()

        if(newCompany){

            registration.existingCompany = null
            registration.phoneNumber = "7777777777"
            registration.streetAddressOne = "1 Press Place"
            registration.streetAddressTwo = ""
            registration.streetAddressThree = ""
            registration.zipcode = "30601"
            registration.city = "Athens"
            registration.state = "GA"
            registration.county = "Clarke"
            registration.country = "usa"

        }else{

            registration.existingCompany = createCompany("existingCompany")

        }

        registration.registrationStatus = registrationStatus
        registration.subDomain = subDomain

        assert registration.save(), "registration save failed with errors: ${registration.errors}"
    }

    private Company createCompany(String companyName){
        Company company = new Company()
        CompanyProfile companyProfile = new CompanyProfile()
        company.companyName = companyName
        company.doingBusinessAs = "A Company"
        company.intCode = 0
        company.companyProfile = companyProfile
        company.companyType = companyType
        company.save(failOnError:true)
        companyProfile = new CompanyProfile()
        companyProfile.company = company
        companyProfile.companyDescription = "lsllsls"
        companyProfile.published = true
        companyProfile.companyWebsite = "http://www.google.com"
        companyProfile.save(failOnError:true)
        return company
    }
}


package com.cogda.domain.admin

import com.cogda.domain.onboarding.Registration
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
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

import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(RegistrationApprovalController)
@TestMixin([DomainClassUnitTestMixin, ControllerUnitTestMixin, ServiceUnitTestMixin])
@Mock([Registration, CustomerAccount, CustomerAccountService])
class RegistrationApprovalControllerSpec extends Specification{

    GsonBuilder gsonBuilder
    Gson gson

    String internalSubDomain =  "internalCustomerAccount"
    String subDomain =  "customerAccount"
    CustomerAccount internalCustomerAccount
    CustomerAccount customerAccount
    CustomerAccountService customerAccountService = new CustomerAccountService()

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
        gsonEnabledControllerSetup()

        internalCustomerAccount = new CustomerAccount(subDomain: internalSubDomain, internalSystemAccount:true).save()
        assert !internalCustomerAccount.hasErrors(), "${internalCustomerAccount.errors.allErrors}"

        customerAccount = new CustomerAccount(subDomain: subDomain).save()
        assert !customerAccount.hasErrors(), "${customerAccount.errors.allErrors}"

        controller.customerAccountService = customerAccountService
    }

    def cleanup(){

    }

    def "verify beforeInterceptor prevents access to CustomerAccounts that are not internalSystemAccount"() {
        given: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { customerAccount.id.toInteger() }
        controller.currentTenant = looseCurrentTenantMock.createMock()

        expect:
        null == controller.list()
        null == controller.reject()
        null == controller.get()
        null == controller.delete()
    }

    def "verify beforeInterceptor allows access to CustomerAccounts that are an internalSystemAccount"(){
        expect:
        fail "Implement me"
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
}


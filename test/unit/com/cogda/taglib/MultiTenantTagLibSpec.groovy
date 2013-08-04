package com.cogda.taglib

import com.cogda.domain.CompanyProfile
import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import grails.plugin.multitenant.core.CurrentTenant
import grails.plugin.spock.TagLibSpec
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import com.cogda.taglib.MultiTenantTagLib
import spock.lang.Specification

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(MultiTenantTagLib)
@TestMixin([DomainClassUnitTestMixin, ServiceUnitTestMixin])
@Mock([CustomerAccount, Company, CompanyType, CompanyProfile, CustomerAccountService])
class MultiTenantTagLibSpec extends Specification {  // ATTENTION: do not extend TabLibSpec!

    String internalSubDomain =  "internalCustomerAccount"
    String subDomain =  "customerAccount"
    CustomerAccount internalCustomerAccount
    CustomerAccount customerAccount
    CompanyType companyType
    Company rootCompany
    CompanyProfile companyProfile
    CustomerAccountService customerAccountService = new CustomerAccountService()

    void setup() {
        companyType = new CompanyType()
        companyType.intCode = 0
        companyType.code = "MGA"
        companyType.description = "MGA"
        companyType.save(failOnError: true)

        // Setup logic here
        internalCustomerAccount = new CustomerAccount(subDomain: internalSubDomain, internalSystemAccount:true).save()
        assert !internalCustomerAccount.hasErrors(), "${internalCustomerAccount.errors.allErrors}"

        customerAccount = new CustomerAccount(subDomain: subDomain).save()
        assert !customerAccount.hasErrors(), "${customerAccount.errors.allErrors}"


        rootCompany = new Company()
        rootCompany.companyName = "Company"
        rootCompany.doingBusinessAs = "A Company"
        rootCompany.intCode = 0
        rootCompany.companyProfile = companyProfile
        rootCompany.companyType = companyType
        rootCompany.save(failOnError:true)
        companyProfile = new CompanyProfile()
        companyProfile.company = rootCompany
        companyProfile.companyDescription = "lsllsls"
        companyProfile.published = true
        companyProfile.companyWebsite = "http://www.google.com"
        companyProfile.save(failOnError:true)

    }

    void cleanup() {
        // Tear down logic here
    }

    def "isAnInternalCustomerAccount will return the body of the tag"() {
        setup: "a mock so that the currentTenant.get method will return the id of the internalCustomerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..4) { internalCustomerAccount.id.toInteger() }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()
        tagLib.customerAccountService = customerAccountService

        when:
        String returnValue = tagLib.isAnInternalCustomerAccount([:], { "body" }) as String

        then:
        returnValue == "body"
    }

    def "isAnInternalCustomerAccount will NOT return the body for a CustomerAccount where internalCustomerAccount false"(){
        setup: "a mock so that the currentTenant.get method will return the id of the customerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..4) { customerAccount.id.toInteger() }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()
        tagLib.customerAccountService = customerAccountService

        when:
        String returnValue = tagLib.isAnInternalCustomerAccount([:], { "body" }) as String

        then:
        returnValue == ""
    }

    def "subDomainName is found for current tenant"(){
        setup: "a mock so that the currentTenant.get method will return the id of the internalCustomerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..4) { internalCustomerAccount.id.toInteger() }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()

        when:
        String subDomain = tagLib.subDomainName([:], {}) as String

        then:
        subDomain == internalCustomerAccount.subDomain
    }

    def "subDomainName is not returned when no CurrentTenant available"(){
        given: " currentTenant is null "
        tagLib.currentTenant = null

        when: "subDomainName is called "
        String subDomain = tagLib.subDomainName([:], {}) as String

        then: "out << receives null string"
        subDomain == ""
    }

    def "hasTenant returns body when one is available"(){
        setup:
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..3) { internalCustomerAccount.id.toInteger() }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()

        when:
        String body = tagLib.hasTenant([:], {"body"}) as String

        then:
        body == "body"
    }

    def "hasTenant does not return body when CurrentTenant is null"(){
        setup:
        tagLib.currentTenant = null

        when:
        String body = tagLib.hasTenant([:], {"body"}) as String

        then:
        body == ""
    }

    def "hasTenant does not return body when CurrentTenant.get() is null"(){
        setup:
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..1) { null }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()

        when:
        String body = tagLib.hasTenant([:], {"body"}) as String

        then:
        body == ""
    }

    def "hasTenant does not return body when CustomerAccount does not exist"(){
        setup:
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..2) { Integer.MAX_VALUE }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()

        when:
        String body = tagLib.hasTenant([:], {"body"}) as String

        then:
        body == ""
    }

    def "rootCompanyName returns '' when tenant is not available"(){
        expect:
        tagLib.rootCompanyName([:], {}).toString() == ""
    }

    def "rootCompanyName returns root Company Name when tenant is available"(){
        setup:
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..3) { internalCustomerAccount.id.toInteger() }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()

        when:
        String rootCompanyName = tagLib.rootCompanyName([:], {}) as String

        then:
        rootCompanyName == rootCompany.companyName
    }


}

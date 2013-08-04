package com.cogda.taglib

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
@Mock([CustomerAccount, CustomerAccountService])
class MultiTenantTagLibSpec extends Specification {  // ATTENTION: do not extend TabLibSpec!

    String internalSubDomain =  "internalCustomerAccount"
    String subDomain =  "customerAccount"
    CustomerAccount internalCustomerAccount
    CustomerAccount customerAccount

    CustomerAccountService customerAccountService = new CustomerAccountService()

    void setup() {
        // Setup logic here
        internalCustomerAccount = new CustomerAccount(subDomain: internalSubDomain, internalSystemAccount:true).save()
        assert !internalCustomerAccount.hasErrors(), "${internalCustomerAccount.errors.allErrors}"

        customerAccount = new CustomerAccount(subDomain: subDomain).save()
        assert !customerAccount.hasErrors(), "${customerAccount.errors.allErrors}"
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
        setup: "a mock so that the currentTenant.get method will return the id of the internalCustomerAccount"
        def looseCurrentTenantMock = mockFor(CurrentTenant, true)
        looseCurrentTenantMock.demand.get(1..4) { customerAccount.id.toInteger() }
        tagLib.currentTenant = looseCurrentTenantMock.createMock()
        tagLib.customerAccountService = customerAccountService

        when:
        String returnValue = tagLib.isAnInternalCustomerAccount([:], { "body" }) as String

        then:
        returnValue == ""
    }
}

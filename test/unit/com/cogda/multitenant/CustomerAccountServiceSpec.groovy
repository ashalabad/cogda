package com.cogda.multitenant

import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import spock.lang.Specification
import spock.lang.Unroll

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(CustomerAccountService)
@TestMixin([DomainClassUnitTestMixin, ServiceUnitTestMixin])
@Mock([CustomerAccount])
class CustomerAccountServiceSpec extends Specification{

    String internalSubDomain =  "internalCustomerAccount"
    String subDomain =  "customerAccount"
    CustomerAccount internalCustomerAccount
    CustomerAccount customerAccount

    void setup() {
        internalCustomerAccount = new CustomerAccount(subDomain: internalSubDomain, internalSystemAccount:true).save()
        assert !internalCustomerAccount.hasErrors(), "${internalCustomerAccount.errors.allErrors}"

        customerAccount = new CustomerAccount(subDomain: subDomain).save()
        assert !customerAccount.hasErrors(), "${customerAccount.errors.allErrors}"
    }

    void cleanup() {
    }

    def "isInternalCustomerAccount with a numeric id returns expected results"() {
        expect:
        true  == service.isInternalCustomerAccount(internalCustomerAccount.id)
        true  == service.isInternalCustomerAccount(internalCustomerAccount.id as Integer)
        false == service.isInternalCustomerAccount(customerAccount.id)
        false == service.isInternalCustomerAccount(customerAccount.id as Integer)
    }

    def "isInternalCustomerAccount with a CustomerAccount param returns expected results"() {
        expect:
        true  == service.isInternalCustomerAccount(internalCustomerAccount)
        false == service.isInternalCustomerAccount(customerAccount)
    }
}

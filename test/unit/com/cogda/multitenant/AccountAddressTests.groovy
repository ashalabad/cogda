package com.cogda.multitenant

import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountAddress)
@TestMixin(DomainClassUnitTestMixin)
class AccountAddressTests {

    AccountAddress accountAddress

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    /**
     * class AccountAddress {
     * 	...
     * 	accountAddressName(nullable:false, blank:false)
     * 	accountAddressCode(nullable:true)
     *  ...
     */
    void testNullable(){
        mockDomain(AccountAddress)

        accountAddress = new AccountAddress()

        assertFalse accountAddress.validate()
        //accountAddress.errors.each { println it }

        assert "nullable" == accountAddress.errors["address"].code
        assert "nullable" == accountAddress.errors["account"].code
        assert "nullable" == accountAddress.errors["accountAddressType"].code

        assert !accountAddress.errors["primaryAddress"]

    }
}

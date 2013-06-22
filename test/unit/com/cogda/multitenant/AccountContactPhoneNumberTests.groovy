package com.cogda.multitenant

import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountContactPhoneNumber)
@TestMixin(DomainClassUnitTestMixin)
class AccountContactPhoneNumberTests {

    AccountContactPhoneNumber accountPhoneNumber

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(AccountContactPhoneNumber)

        accountPhoneNumber = new AccountContactPhoneNumber()

        assertFalse accountPhoneNumber.validate()
        //accountPhoneNumber.errors.each { println it }

        assert "nullable" == accountPhoneNumber.errors["phoneNumber"].code
        assert "nullable" == accountPhoneNumber.errors["accountContact"].code

        assert !accountPhoneNumber.errors["primaryPhoneNumber"]
        assert !accountPhoneNumber.errors["description"]

    }
}
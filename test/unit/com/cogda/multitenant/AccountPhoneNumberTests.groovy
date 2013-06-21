package com.cogda.multitenant

import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountPhoneNumber)
@TestMixin(DomainClassUnitTestMixin)
class AccountPhoneNumberTests {

    AccountPhoneNumber accountPhoneNumber

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(AccountPhoneNumber)

        accountPhoneNumber = new AccountPhoneNumber()

        assertFalse accountPhoneNumber.validate()
        //accountPhoneNumber.errors.each { println it }

        assert "nullable" == accountPhoneNumber.errors["phoneNumber"].code
        assert "nullable" == accountPhoneNumber.errors["account"].code

        assert !accountPhoneNumber.errors["primaryPhoneNumber"]
        assert !accountPhoneNumber.errors["description"]

    }
}
package com.cogda.multitenant

import com.cogda.domain.admin.AccountType
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountContactEmailAddress)
@TestMixin(DomainClassUnitTestMixin)
class AccountContactEmailAddressTests {

    AccountContactEmailAddress accountEmailAddress

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(AccountContactEmailAddress)

        accountEmailAddress = new AccountContactEmailAddress()

        assertFalse accountEmailAddress.validate()
        //accountEmailAddress.errors.each { println it }

        assert "nullable" == accountEmailAddress.errors["emailAddress"].code
        assert "nullable" == accountEmailAddress.errors["accountContact"].code
        assert !accountEmailAddress.errors["primaryEmailAddress"]

    }


    void testEmailConstraint(){

        AccountContact accountContact = mockDomain(AccountContact)
        accountContact.firstName = "firstName"
        accountContact.middleName = "middleName"
        accountContact.lastName = "lastName"
        assert accountContact.save(), "AccountContact test domain class was not saved successfully"

        accountEmailAddress = mockDomain(AccountContactEmailAddress)
        accountEmailAddress.accountContact = accountContact
        accountEmailAddress.emailAddress = "test@cogda.com"
        accountEmailAddress.primaryEmailAddress = true
        assert accountEmailAddress.save(),"AccountContactEmailAddress test domain class was not saved successfully"

        accountEmailAddress.emailAddress = "@cogda.com"
        assertFalse accountEmailAddress.validate()
        assert "email.invalid" == accountEmailAddress.errors["emailAddress"].code

        //accountEmailAddress.errors.each { println it }

        accountEmailAddress.emailAddress = "test.cogda.com"
        assertFalse accountEmailAddress.validate()
        assert "email.invalid" == accountEmailAddress.errors["emailAddress"].code

        accountEmailAddress.emailAddress = "test@cogda"
        assertFalse accountEmailAddress.validate()
        assert "email.invalid" == accountEmailAddress.errors["emailAddress"].code
    }
}

package com.cogda.multitenant

import com.cogda.domain.admin.AccountType
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountEmailAddress)
@TestMixin(DomainClassUnitTestMixin)
class AccountEmailAddressTests {

    AccountEmailAddress accountEmailAddress

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(AccountEmailAddress)

        accountEmailAddress = new AccountEmailAddress()

        assertFalse accountEmailAddress.validate()
        //accountEmailAddress.errors.each { println it }

        assert "nullable" == accountEmailAddress.errors["emailAddress"].code
        assert "nullable" == accountEmailAddress.errors["account"].code
        assert !accountEmailAddress.errors["primaryEmailAddress"]

    }


    void testEmailConstraint(){
        mockDomain(AccountType, [new AccountType(code:"Agency", intCode:0, description: "Agency")])

        Account account = mockDomain(Account)
        account.accountName = "accountName"
        account.accountCode = "accountCode"
        account.accountType = AccountType.findByCode("Agency")
        assert account.save(), "Account test domain class was not saved successfully"

        accountEmailAddress = mockDomain(AccountEmailAddress)
        accountEmailAddress.account = account
        accountEmailAddress.emailAddress = "test@cogda.com"
        accountEmailAddress.primaryEmailAddress = true
        assert accountEmailAddress.save(),"AccountEmailAddress test domain class was not saved successfully"

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

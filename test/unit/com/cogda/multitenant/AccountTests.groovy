package com.cogda.multitenant

import com.cogda.domain.admin.AccountType
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
@TestMixin(DomainClassUnitTestMixin)
class AccountTests {

    Account account

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    /**
     * class Account {
     * 	...
     * 	accountName(nullable:false, blank:false)
     * 	accountCode(nullable:true)
     *  ...
     */
    void testNullable(){
        mockDomain(Account)

        account = new Account()

        assertFalse account.validate()

        assert "nullable" == account.errors["accountName"].code
        assert "nullable" == account.errors["accountType"].code
        assert !account.errors["accountCode"]
    }


    /**
     *
     */
    void testGetPrimaryEmailAddress(){

        String primaryEmailAddress =  "primary@cogda.com"

        mockDomain(AccountType, [new AccountType(code:"Agency", intCode:0, description: "Agency")])

        account = mockDomain(Account)
            account.accountName = "accountName"
            account.accountCode = "accountCode"
            account.accountType = AccountType.findByCode("Agency")
        assert account.save(), "Account test domain class was not saved successfully"

        AccountEmailAddress accountEmailAddress1 = mockDomain(AccountEmailAddress)
            accountEmailAddress1.emailAddress = primaryEmailAddress
            accountEmailAddress1.primaryEmailAddress = true
            accountEmailAddress1.account = account
        assert accountEmailAddress1.save(), "AccountEmailAddress1 test domain class was not saved successfully"

        AccountEmailAddress accountEmailAddress2 = mockDomain(AccountEmailAddress)
            accountEmailAddress2.emailAddress = "test@cogda.com"
            accountEmailAddress2.primaryEmailAddress = false
            accountEmailAddress2.account = account
        assert accountEmailAddress2.save(), "AccountEmailAddress2 test domain class was not saved successfully"

        AccountContact accountContact = mockDomain(AccountContact)
            accountContact.firstName = "firstName"
            accountContact.middleName = "middleName"
            accountContact.lastName = "lastName"
            accountContact.account = account
            accountContact.primaryContact = true
            accountContact.accountEmailAddresses = [accountEmailAddress1,accountEmailAddress2]
        assert accountContact.save(), "AccountContact test domain class was not saved successfully"


        String emailAddress = account.primaryEmailAddress
        assert emailAddress.equals(primaryEmailAddress)

    }

    /**
     *
     */
    void testGetPrimaryAccountContactPhoneNumberString(){

        String primaryPhoneNumber = "1-123-456-7890"

        mockDomain(AccountType, [new AccountType(code:"Agency", intCode:0, description: "Agency")])

        account = mockDomain(Account)
            account.accountName = "accountName"
            account.accountCode = "accountCode"
            account.accountType = AccountType.findByCode("Agency")
        assert account.save(), "Account test domain class was not saved successfully"

        AccountPhoneNumber accountPhoneNumber1 = mockDomain(AccountPhoneNumber)
            accountPhoneNumber1.phoneNumber = primaryPhoneNumber
            accountPhoneNumber1.primaryPhoneNumber = true
            accountPhoneNumber1.account = account
        assert accountPhoneNumber1.save(), "AccountPhoneNumber1 test domain class was not saved successfully"

        AccountPhoneNumber accountPhoneNumber2 = mockDomain(AccountPhoneNumber)
            accountPhoneNumber2.phoneNumber = "1-456-245-7890"
            accountPhoneNumber2.primaryPhoneNumber = false
            accountPhoneNumber2.account = account
        assert accountPhoneNumber2.save(), "AccountPhoneNumber2 test domain class was not saved successfully"

        AccountContact accountContact = mockDomain(AccountContact)
            accountContact.firstName = "firstName"
            accountContact.middleName = "middleName"
            accountContact.lastName = "lastName"
            accountContact.account = account
            accountContact.primaryContact = true
            accountContact.accountPhoneNumbers = [accountPhoneNumber1,accountPhoneNumber2]
        assert accountContact.save(), "AccountContact test domain class was not saved successfully"


        String phoneNumber = account.primaryAccountContactPhoneNumberString
        assert phoneNumber.equals(primaryPhoneNumber)

    }

}

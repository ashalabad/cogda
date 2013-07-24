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
        assert !account.errors["isMarket"]
        assert !account.errors["active"]
        assert !account.errors["favorite"]
    }


    /**
     *
     */
//    void testGetPrimaryEmailAddress(){
//
//        String primaryEmailAddress =  "primary@cogda.com"
//
//        mockDomain(AccountType, [new AccountType(code:"Agency", intCode:0, description: "Agency")])
//
//        account = mockDomain(Account)
//            account.accountName = "accountName"
//            account.accountCode = "accountCode"
//            account.accountType = AccountType.findByCode("Agency")
//        assert account.save(), "Account test domain class was not saved successfully"
//
//        AccountContact accountContact = mockDomain(AccountContact)
//        accountContact.firstName = "firstName"
//        accountContact.middleName = "middleName"
//        accountContact.lastName = "lastName"
//        accountContact.account = account
//        accountContact.primaryContact = true
//        assert accountContact.save(), "AccountContact test domain class was not saved successfully"
//
//        AccountContactEmailAddress accountContactEmailAddress1 = mockDomain(AccountContactEmailAddress)
//            accountContactEmailAddress1.emailAddress = primaryEmailAddress
//            accountContactEmailAddress1.primaryEmailAddress = true
//            accountContactEmailAddress1.accountContact = accountContact
//        assert accountContactEmailAddress1.save(), "AccountEmailAddress1 test domain class was not saved successfully"
//
//        AccountContactEmailAddress accountContactEmailAddress2 = mockDomain(AccountContactEmailAddress)
//            accountContactEmailAddress2.emailAddress = "test@cogda.com"
//            accountContactEmailAddress2.primaryEmailAddress = false
//            accountContactEmailAddress2.accountContact = accountContact
//        assert accountContactEmailAddress2.save(), "AccountEmailAddress2 test domain class was not saved successfully"
//
//        accountContact.accountContactEmailAddresses = [accountContactEmailAddress1,accountContactEmailAddress2]
//        accountContact.save()
//
//        String emailAddress = account.primaryEmailAddress
//        assert emailAddress.equals(primaryEmailAddress)
//
//    }

    /**
     *
     */
//    void testGetPrimaryAccountContactPhoneNumberString(){
//
//        String primaryPhoneNumber = "1-123-456-7890"
//
//        mockDomain(AccountType, [new AccountType(code:"Agency", intCode:0, description: "Agency")])
//
//        account = mockDomain(Account)
//            account.accountName = "accountName"
//            account.accountCode = "accountCode"
//            account.accountType = AccountType.findByCode("Agency")
//        assert account.save(), "Account test domain class was not saved successfully"
//
//
//        AccountContact accountContact = mockDomain(AccountContact)
//            accountContact.firstName = "firstName"
//            accountContact.middleName = "middleName"
//            accountContact.lastName = "lastName"
//            accountContact.account = account
//            accountContact.primaryContact = true
//        assert accountContact.save(), "AccountContact test domain class was not saved successfully"
//
//        AccountContactPhoneNumber accountContactPhoneNumber1 = mockDomain(AccountContactPhoneNumber)
//        accountContactPhoneNumber1.phoneNumber = primaryPhoneNumber
//        accountContactPhoneNumber1.primaryPhoneNumber = true
//        accountContactPhoneNumber1.accountContact = accountContact
//        assert accountContactPhoneNumber1.save(), "AccountPhoneNumber1 test domain class was not saved successfully"
//
//        AccountContactPhoneNumber accountContactPhoneNumber2 = mockDomain(AccountContactPhoneNumber)
//        accountContactPhoneNumber2.phoneNumber = "1-456-245-7890"
//        accountContactPhoneNumber2.primaryPhoneNumber = false
//        accountContactPhoneNumber2.accountContact = accountContact
//        assert accountContactPhoneNumber2.save(), "AccountPhoneNumber2 test domain class was not saved successfully"
//
//        accountContact.accountContactPhoneNumbers = [accountContactPhoneNumber1,accountContactPhoneNumber2]
//        accountContact.save()
//
//        String phoneNumber = account.primaryAccountContactPhoneNumberString
//        assert phoneNumber.equals(primaryPhoneNumber)
//
//    }

}

package com.cogda.multitenant

import com.cogda.domain.admin.AccountType
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountContact)
@TestMixin(DomainClassUnitTestMixin)
class AccountContactTests {

    AccountContact accountContact

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    /**
     * class AccountContact {
     * 	...
     * 	accountContactName(nullable:false, blank:false)
     * 	accountContactCode(nullable:true)
     *  ...
     */
    void testNullable(){
        mockDomain(AccountContact)

        accountContact = new AccountContact()

        assertFalse accountContact.validate()
        //accountContact.errors.each { println it }

        assert "nullable" == accountContact.errors["firstName"].code
        assert "nullable" == accountContact.errors["lastName"].code
        assert "nullable" == accountContact.errors["account"].code
        assert !accountContact.errors["userProfile"]
        assert !accountContact.errors["middleName"]
        assert !accountContact.errors["primaryContact"]

    }


    /**
     * class AccountContact {
     * 	...
     * Gets the full name - lastName, firstName
     * @return String
     * String getFullName(){
     *      return "${lastName}, ${firstName}"
     *  }
     *  ...
     */

    void testGetFullName(){

        mockDomain(AccountType, [new AccountType(code:"Agency", intCode:0, description: "Agency")])

        Account account = mockDomain(Account)
        account.accountName = "accountName"
        account.accountCode = "accountCode"
        account.accountType = AccountType.findByCode("Agency")
        assert account.save(), "Account test domain class was not saved successfully"

        def firstName = "firstName"
        def lastName = "lastname"

        accountContact = mockDomain(AccountContact)
            accountContact.account = account
            accountContact.firstName = firstName
            accountContact.lastName = lastName
        assert accountContact.save(),"AccountContact test domain class was not saved successfully"

        assert accountContact.getFullName() == "${lastName}, ${firstName}"

    }

}
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

    AccountContact accountContact,accountContact2


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

        def firstName = "firstName"
        def middleName = "middlename"
        def lastName = "lastname"

        accountContact = mockDomain(AccountContact)
            accountContact.firstName = firstName
            accountContact.lastName = lastName
            accountContact.middleName = middleName
        assert accountContact.save(),"AccountContact test domain class was not saved successfully"

        assert accountContact.getFullName() == "${lastName}, ${firstName} ${middleName}"

        accountContact2 = mockDomain(AccountContact)
        accountContact2.firstName = firstName
        accountContact2.lastName = lastName
        accountContact2.middleName = null
        assert accountContact2.save(),"AccountContact test domain class was not saved successfully"

        assert accountContact2.getFullName() == "${lastName}, ${firstName}"

    }

}
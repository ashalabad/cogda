package com.cogda.multitenant



import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(CustomerAccount)
@TestMixin(DomainClassUnitTestMixin)
class CustomerAccountTests {

    CustomerAccount customerAccount

    /**
     * class CustomerAccount {
     * 	...
     * subDomain(nullable:false, blank:false, unique:true, minSize:2, validator: { val, obj ->
     *    if(CustomerAccount.DISALLOWED_SUBDOMAINS.contains(val)){
     *        return ['customerAccount.subDomain.invalid']
     *    }
     *  })
     *  accountId(nullable:false, blank:false, unique:true)
     *  ...
     * }
     */
    void testNullable(){
        mockDomain(CustomerAccount, [new CustomerAccount(subDomain:"taken"), new CustomerAccount(subDomain:"rais")])

        customerAccount = new CustomerAccount()
        customerAccount.subDomain = null
        customerAccount.accountId = null

        assertFalse customerAccount.validate()

        assert "nullable" == customerAccount.errors["subDomain"].code
        assert "nullable" == customerAccount.errors["accountId"].code
    }

    void testBlank(){

        mockDomain(CustomerAccount, [new CustomerAccount(subDomain:"taken"), new CustomerAccount(subDomain:"rais")])

        customerAccount = new CustomerAccount()
        customerAccount.subDomain = "   "
        customerAccount.accountId = "   "

        assertFalse customerAccount.validate()

        assert "blank" == customerAccount.errors["subDomain"].code
        assert "blank" == customerAccount.errors["accountId"].code
    }

    void testUnique(){
        mockDomain(CustomerAccount, [new CustomerAccount(subDomain:"taken", accountId:"100000000"), new CustomerAccount(subDomain:"rais")])

        customerAccount = new CustomerAccount()
        customerAccount.subDomain = "taken"
        customerAccount.accountId = "100000000"

        assertFalse customerAccount.validate()

        assert "unique" == customerAccount.errors["subDomain"].code
        assert "unique" == customerAccount.errors["accountId"].code
    }

    void testMinSize(){
        mockDomain(CustomerAccount, [new CustomerAccount(subDomain:"taken"), new CustomerAccount(subDomain:"rais")])

        customerAccount = new CustomerAccount()

        customerAccount.subDomain = "a"
        customerAccount.validate()

        assert "minSize.notmet" == customerAccount.errors["subDomain"].code

    }

    void testValidator(){
        mockDomain(CustomerAccount, [new CustomerAccount(subDomain:"taken"), new CustomerAccount(subDomain:"rais")])

        customerAccount = new CustomerAccount()

        CustomerAccount.DISALLOWED_SUBDOMAINS.each { String subDomain ->
            customerAccount.subDomain = subDomain

            assertFalse customerAccount.validate()

            assert customerAccount.errors["subDomain"].code
        }

        customerAccount.subDomain = "thisisavalidsubdomain"
        assert customerAccount.validate()
    }

    void testMatches(){
        mockDomain(CustomerAccount, [new CustomerAccount(subDomain:"taken"), new CustomerAccount(subDomain:"rais")])

        customerAccount = new CustomerAccount()

        customerAccount.subDomain = "abcdefghijklmnopqrstuvwxyz"

        assert customerAccount.validate()

        customerAccount.subDomain += "1"

        assertFalse customerAccount.validate()

        assert "matches.invalid" == customerAccount.errors["subDomain"].code
    }
}

package com.cogda.multitenant

import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(AccountNote)
@TestMixin(DomainClassUnitTestMixin)
class AccountNoteTests {

    AccountNote accountNote

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(AccountNote)

        accountNote = new AccountNote()

        assertFalse accountNote.validate()
        //accountNote.errors.each { println it }

        assert "nullable" == accountNote.errors["note"].code
        assert "nullable" == accountNote.errors["account"].code
        assert "nullable" == accountNote.errors["noteType"].code

    }
}
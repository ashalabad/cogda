package com.cogda.domain.admin

import grails.test.mixin.domain.DomainClassUnitTestMixin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(NaicsCode)
@TestMixin(DomainClassUnitTestMixin)
class NaicsCodeTests {

    NaicsCode naicsCode

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(NaicsCode)

        naicsCode = new NaicsCode()

        assertFalse naicsCode.validate()

        assert "nullable" == naicsCode.errors["code"].code
        assert "nullable" == naicsCode.errors["description"].code
        assert !naicsCode.errors["active"]
        assert !naicsCode.errors["level"]
        assert !naicsCode.errors["parentNaicsCode"]
    }

    void testBeforeValidate() {
        mockDomain(NaicsCode)

        naicsCode = new NaicsCode()

        assertFalse naicsCode.validate()
        assert naicsCode.level == 0

        naicsCode = new NaicsCode()
        naicsCode.code = "123"
        naicsCode.description= "description here"
        assert naicsCode.save(), "NaicsCode test domain class was not saved successfully"

        NaicsCode naicsCode2 = new NaicsCode()
        naicsCode2.code = "123"
        naicsCode2.description= "description here"
        naicsCode2.parentNaicsCode = naicsCode
        assert naicsCode2.save(), "NaicsCode test child domain class was not saved successfully"

        assert naicsCode.level == 0
        assert naicsCode2.level == 1
    }

    void testToString(){
        mockDomain(NaicsCode)

        naicsCode = new NaicsCode()
        naicsCode.code = "123"
        naicsCode.description= "description here"
        assert naicsCode.save(), "NaicsCode test domain class was not saved successfully"

        assert naicsCode.toString() == "123 - description here"
    }

}

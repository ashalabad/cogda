package com.cogda.domain.admin

import grails.test.mixin.domain.DomainClassUnitTestMixin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(SicCode)
@TestMixin(DomainClassUnitTestMixin)
class SicCodeTests {

    SicCode sicCode

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testNullable(){
        mockDomain(SicCode)

        sicCode = new SicCode()

        assertFalse sicCode.validate()

        assert "nullable" == sicCode.errors["code"].code
        assert "nullable" == sicCode.errors["description"].code
        assert !sicCode.errors["active"]
        assert !sicCode.errors["level"]
        assert !sicCode.errors["parentSicCode"]
    }

    void testBeforeValidate() {
        mockDomain(SicCode)

        sicCode = new SicCode()

        assertFalse sicCode.validate()
        assert sicCode.level == 0

        sicCode = new SicCode()
        sicCode.code = "123"
        sicCode.description= "description here"
        assert sicCode.save(), "SicCode test domain class was not saved successfully"

        SicCode sicCode2 = new SicCode()
        sicCode2.code = "123"
        sicCode2.description= "description here"
        sicCode2.parentSicCode = sicCode
        assert sicCode2.save(), "SicCode test child domain class was not saved successfully"

        assert sicCode.level == 0
        assert sicCode2.level == 1
    }

    void testToString(){
        mockDomain(SicCode)

        sicCode = new SicCode()
        sicCode.code = "123"
        sicCode.description= "description here"
        assert sicCode.save(), "SicCode test domain class was not saved successfully"

        assert sicCode.toString() == "123 - description here"
    }

}

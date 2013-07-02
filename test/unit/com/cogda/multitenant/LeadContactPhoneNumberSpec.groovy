package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(LeadContactPhoneNumber)
class LeadContactPhoneNumberSpec extends ConstraintUnitSpec {

    def setup() {
        mockForConstraintsTests(LeadContactPhoneNumber, [new LeadContactPhoneNumber()])
    }

    @Unroll("test leadContactPhoneNumber #field is #error using #value")
    def 'test leadContactPhoneNumber all constraints'() {
        when:
        def leadContactPhoneNumber = new LeadContactPhoneNumber("$field": value)

        then:
        validateConstraints(leadContactPhoneNumber, field, error)

        where:
        error      | field         | value
        'valid'    | 'description' | null
        'valid'    | 'description' | ''
        'nullable' | 'phoneNumber' | null
        'blank'    | 'phoneNumber' | ''
        'valid'    | 'phoneNumber' | getLongString(50)
        'valid'    | 'phoneNumber' | getLongString(1)
    }
}

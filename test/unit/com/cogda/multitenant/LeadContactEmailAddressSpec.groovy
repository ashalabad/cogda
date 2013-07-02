package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(LeadContactEmailAddress)
class LeadContactEmailAddressSpec extends ConstraintUnitSpec {
    def setup() {
        mockForConstraintsTests(LeadContactEmailAddress, [new LeadContactEmailAddress()])
    }

    @Unroll("test leadContactEmailAddress #field is #error using #value")
    def 'test leadContactEmailAddress all constraints'() {
        when:
        def leadContactEmailAddress = new LeadContactEmailAddress("$field": value)

        then:
        validateConstraints(leadContactEmailAddress, field, error)

        where:
        error      | field          | value
        'nullable' | 'emailAddress' | null
        'blank'    | 'emailAddress' | ''
        'email'    | 'emailAddress' | getLongString(51)
        'valid'    | 'emailAddress' | getEmail(true)
        'email'    | 'emailAddress' | getEmail(false)
    }

}

package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import com.cogda.domain.Address
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(LeadAddress)
class LeadAddressSpec extends ConstraintUnitSpec {

    def setup() {
        mockForConstraintsTests(LeadAddress)
    }

    @Unroll("test leadAddress #field is #error using #value")
    def 'test leadAddress.address constraints'() {
        when:
        def leadAddress = new LeadAddress("$field": value)

        then:
        validateConstraints(leadAddress, field, error)

        where:
        error      | field     | value
        'nullable' | 'address' | null
        'valid'    | 'address' | new Address()
    }

}

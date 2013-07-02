package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import com.cogda.domain.Address
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(LeadContactAddress)
class LeadContactAddressSpec extends ConstraintUnitSpec {
    def setup() {
        mockForConstraintsTests(LeadContactAddress, [new LeadContactAddress()])
    }

    @Unroll("test leadContactAddress wall constraints #field is #error")
    def 'test leadContactAddress.address constraints'() {
        when:
        def leadContactAddress = new LeadContactAddress("$field": value)

        then:
        validateConstraints(leadContactAddress, field, error)

        where:
        error   | field     | value
        'valid' | 'address' | null
        'valid' | 'address' | new Address()
    }
}

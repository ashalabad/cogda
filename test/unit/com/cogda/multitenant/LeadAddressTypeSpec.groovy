package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Unroll

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 7/1/13
 * Time: 11:57 PM
 * To change this template use File | Settings | File Templates.
 */
@TestFor(LeadAddressType)
class LeadAddressTypeSpec extends ConstraintUnitSpec {

    def setup() {
        mockForConstraintsTests(LeadAddressType, [new LeadAddressType(code: "123456789")])
    }

    @Unroll("test leadAddressType wall constraints #field is #error")
    def 'test leadAddressType all constraints'() {
        when:
        def leadAddressType = new LeadAddressType("$field": value)

        then:
        validateConstraints(leadAddressType, field, error)

        where:
        error      | field         | value
        'nullable' | 'code'        | null
        'maxSize'  | 'code'        | getLongString(51)
        'maxSize'  | 'description' | getLongString(256)
    }

    @Unroll("test leadAddressType #field is #error using #value")
    def 'test leadAddressType code constraints'() {
        when:
        def leadAddressType = new LeadAddressType("$field": value)

        then:
        validateConstraints(leadAddressType, field, error)

        where:
        error      | field  | value
        'nullable' | 'code' | null
        'maxSize'  | 'code' | getLongString(51)
        'unique'   | 'code' | '123456789'
        'valid'    | 'code' | getLongString(50)
    }

    @Unroll("test leadAddressType #field is #error using #value")
    def 'test leadAddressType description constraints'() {
        when:
        def leadAddressType = new LeadAddressType("$field": value)

        then:
        validateConstraints(leadAddressType, field, error)

        where:
        error     | field         | value
        'maxSize' | 'description' | getLongString(256)
        'valid'   | 'description' | null
        'valid'   | 'description' | getLongString(255)
    }
}

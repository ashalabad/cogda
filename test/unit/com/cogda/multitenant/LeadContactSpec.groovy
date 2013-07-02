package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(LeadContact)
class LeadContactSpec extends ConstraintUnitSpec {

    def setup() {
        mockForConstraintsTests(LeadContact, [new LeadContact()])
    }

    @Unroll("test leadContact wall constraints #field is #error")
    def 'test leadContact all constraints'() {
        when:
        def leadContact = new LeadContact("$field": value)

        then:
        validateConstraints(leadContact, field, error)

        where:
        error      | field       | value
        'nullable' | 'firstName' | null
        'blank'    | 'firstName' | ''
        'size'     | 'firstName' | getLongString(51)
        'nullable' | 'lastName'  | null
        'blank'    | 'lastName'  | ''
        'size'     | 'lastName'  | getLongString(51)
    }

    @Unroll("test leadContact #field is #error using #value")
    def 'test leadContact firstName constraints'() {
        when:
        def leadContact = new LeadContact("$field": value)

        then:
        validateConstraints(leadContact, field, error)

        where:
        error      | field       | value
        'nullable' | 'firstName' | null
        'blank'    | 'firstName' | ''
        'size'     | 'firstName' | getLongString(51)
        'valid'    | 'firstName' | getLongString(50)
        'valid'    | 'firstName' | getLongString(1)
    }

    @Unroll("test leadContact #field is #error using #value")
    def 'test leadContact lastName constraints'() {
        when:
        def leadContact = new LeadContact("$field": value)

        then:
        validateConstraints(leadContact, field, error)

        where:
        error      | field      | value
        'nullable' | 'lastName' | null
        'blank'    | 'lastName' | ''
        'size'     | 'lastName' | getLongString(51)
        'valid'    | 'lastName' | getLongString(50)
        'valid'    | 'lastName' | getLongString(1)
    }

    def 'test getFullName'() {
        when:
        def leadContact = new LeadContact(firstName: "Tommy", lastName: "Jones")

        then:
        "Jones, Tommy" == leadContact.getFullName()
    }

    @Unroll("test getLeadPrimaryEmailAddress with primary #primaryEmailAddres")
    def 'test getLeadPrimaryEmailAddress with/out primary'() {
        when:
        def emailAddress = getEmail(true)
        def expectedLeadContactEmailAddress = new LeadContactEmailAddress(emailAddress: emailAddress, primaryEmailAddress: primaryEmailAddres)
        def leadContact = new LeadContact(leadContactEmailAddresses: [expectedLeadContactEmailAddress])

        then:
        if (primaryEmailAddres)
            expectedLeadContactEmailAddress == leadContact.getLeadPrimaryEmailAddress()
        else
            null == leadContact.getLeadPrimaryEmailAddress()

        where:
        primaryEmailAddres << [true, false]
    }

    @Unroll("test getLeadPrimaryPhoneNumber with primary #primaryPhoneNumber")
    def 'test getPrimaryLeadPhoneNumber with/out primary'() {
        when:
        def phoneNumber = getLongString(10)
        def leadContact = new LeadContact(leadContactPhoneNumbers:
                [new LeadContactPhoneNumber(primaryPhoneNumber: primaryPhoneNumber,
                        phoneNumber: phoneNumber,
                        description: getLongString(8))])

        then:
        if (primaryPhoneNumber)
            phoneNumber == leadContact.getLeadPrimaryPhoneNumber()
        else
            null == leadContact.getLeadPrimaryPhoneNumber()

        where:
        primaryPhoneNumber << [true, false]
    }

}

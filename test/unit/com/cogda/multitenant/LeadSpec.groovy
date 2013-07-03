package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import com.cogda.domain.Address
import grails.test.mixin.TestFor
import spock.lang.Unroll

@TestFor(Lead)
class LeadSpec extends ConstraintUnitSpec {

    def setup() {
        mockForConstraintsTests(Lead, [new Lead()])
    }

    @Unroll("test lead wall constraints #field is #error")
    def 'test lead all constraints'() {
        when:
        def lead = new Lead("$field": value)

        then:
        validateConstraints(lead, field, error)

        where:
        error      | field        | value
        'nullable' | 'clientId'   | null
        'blank'    | 'clientId'   | ''
        'size'     | 'clientId'   | getLongString(51)
        'nullable' | 'clientName' | null
        'blank'    | 'clientName' | ''
        'size'     | 'clientName' | getLongString(51)
    }

    @Unroll("test lead #field is #error using #value")
    def 'test lead clientId constraints'() {
        when:
        def lead = new Lead("$field": value)

        then:
        validateConstraints(lead, field, error)

        where:
        error      | field      | value
        'blank'    | 'clientId' | ''
        'nullable' | 'clientId' | null
        'size'     | 'clientId' | getLongString(51)
        'valid'    | 'clientId' | getLongString(50)
        'valid'    | 'clientId' | getLongString(1)
    }

    @Unroll("test lead #field is #error using #value")
    def 'test lead clientName constraints'() {
        when:
        def lead = new Lead("$field": value)

        then:
        validateConstraints(lead, field, error)

        where:
        error      | field        | value
        'nullable' | 'clientName' | null
        'blank'    | 'clientName' | ''
        'size'     | 'clientName' | getLongString(51)
        'valid'    | 'clientName' | getLongString(50)
        'valid'    | 'clientName' | getLongString(1)
    }

    def 'test getPrimaryEmailAddresses with primary'() {
        when:
        def expectedEmail = getEmail(true)
        def lead = new Lead(leadContacts:
                [new LeadContact(leadContactEmailAddresses:
                        [new LeadContactEmailAddress(emailAddress:
                                expectedEmail, primaryEmailAddress: true)], primaryContact: true)])

        then:
        expectedEmail == lead.getPrimaryEmailAddress()

    }

    def 'test getPrimaryEmailAddresses without primary contact'() {
        when:
        def expectedEmail = getEmail(true)
        def lead = new Lead(leadContacts:
                [new LeadContact(leadContactEmailAddresses:
                        [new LeadContactEmailAddress(emailAddress:
                                expectedEmail, primaryEmailAddress: true)], primaryContact: false)])

        then:
        null == lead.getPrimaryEmailAddress()
    }

    def 'test getPrimaryEmailAddresses without primary email'() {
        when:
        def expectedEmail = getEmail(true)
        def lead = new Lead(leadContacts:
                [new LeadContact(leadContactEmailAddresses:
                        [new LeadContactEmailAddress(emailAddress:
                                expectedEmail, primaryEmailAddress: false)], primaryContact: true)])

        then:
        null == lead.getPrimaryEmailAddress()
    }

    def 'test getPrimaryLeadEmailAddress with primary'() {
        when:
        def expectedEmail = new LeadContactEmailAddress(emailAddress:
                getEmail(true), primaryEmailAddress: true)
        def lead = new Lead(leadContacts:
                [new LeadContact(leadContactEmailAddresses:
                        [expectedEmail], primaryContact: true)])
        then:
        expectedEmail == lead.getPrimaryLeadEmailAddress()
    }

    def 'test getPrimaryLeadEmailAddress without primary contact'() {
        when:
        def expectedEmail = getEmail(true)
        def lead = new Lead(leadContacts:
                [new LeadContact(leadContactEmailAddresses:
                        [new LeadContactEmailAddress(emailAddress:
                                expectedEmail, primaryEmailAddress: true)], primaryContact: false)])

        then:
        null == lead.getPrimaryLeadEmailAddress()
    }

    def 'test getPrimaryLeadEmailAddress without primary email'() {
        when:
        def expectedEmail = getEmail(true)
        def lead = new Lead(leadContacts:
                [new LeadContact(leadContactEmailAddresses:
                        [new LeadContactEmailAddress(emailAddress:
                                expectedEmail, primaryEmailAddress: false)], primaryContact: true)])

        then:
        null == lead.getPrimaryLeadEmailAddress()
    }

    @Unroll("test getPrimaryLeadContactName with primaryContact #primaryContact")
    def 'test getPrimaryLeadContactName with/out primary contact'() {
        when:
        def expectedContact = new LeadContact(firstName: "Tommy", lastName: "Jones", primaryContact: primaryContact)
        def lead = new Lead(leadContacts: [expectedContact])

        then:
        if (primaryContact)
            "Tommy Jones" == lead.getPrimaryLeadContactName()
        else
            null == lead.getPrimaryLeadContactName()

        where:
        primaryContact << [true, false]
    }

    @Unroll("test getPrimaryLeadContact with primaryContact #primaryContact")
    def 'test getPrimaryLeadContact with/out primary contact'() {
        when:
        def expectedContact = new LeadContact(firstName: "Tommy", lastName: "Jones", primaryContact: primaryContact)
        def lead = new Lead(leadContacts: [expectedContact])

        then:
        if (primaryContact)
            expectedContact == lead.getPrimaryLeadContact()
        else
            null == lead.getPrimaryLeadContact()

        where:
        primaryContact << [true, false]
    }

    @Unroll("test getPrimaryLeadContactPhoneNumber with primaryContact #primaryContact")
    def 'test getPrimaryLeadContactPhoneNumber with/out primary contact'() {
        when:
        def expectedPhoneNumber = new LeadContactPhoneNumber(description: getLongString(8), phoneNumber: getLongString(8))
        def expectedContact = new LeadContact(leadContactPhoneNumbers: [expectedPhoneNumber], primaryContact: primaryContact)
        def lead = new Lead(leadContacts: [expectedContact])

        then:
        if (primaryContact)
            expectedPhoneNumber == lead.getPrimaryLeadContactPhoneNumber()
        else
            null == lead.getPrimaryLeadContactPhoneNumber()

        where:
        primaryContact << [true, false]
    }

    @Unroll("test getPrimaryLeadContactPhoneNumberString with primaryContact #primaryContact")
    def 'test getPrimaryLeadContactPhoneNumberString with/out primary contact'() {
        when:
        def expectedPhoneNumber = new LeadContactPhoneNumber(description: getLongString(8), phoneNumber: getLongString(8))
        def expectedContact = new LeadContact(leadContactPhoneNumbers: [expectedPhoneNumber], primaryContact: primaryContact)
        def lead = new Lead(leadContacts: [expectedContact])

        then:
        if (primaryContact)
            expectedPhoneNumber.phoneNumber == lead.getPrimaryLeadContactPhoneNumberString()
        else
            null == lead.getPrimaryLeadContactPhoneNumberString()

        where:
        primaryContact << [true, false]
    }


    @Unroll("test getPrimaryAddress with primaryAddress #primaryAddress")
    def 'test getPrimaryAddress with/out primary address'() {
        when:
        def expectedAddress = new LeadAddress(address: new Address(addressOne: getLongString(8)), primaryAddress: primaryAddress)
        def lead = new Lead(leadAddresses: [expectedAddress])

        then:
        if (expectedAddress)
            expectedAddress == lead.getPrimaryAddress()
        else
            null == lead.getPrimaryAddress()

        where:
        primaryAddress << [true, false]
    }
}

package com.cogda.multitenant

import com.cogda.domain.lead.SuspectController
import grails.test.mixin.*
import spock.lang.Specification

/**
 * SuspectControllerSpec
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(SuspectController)
@Mock(Lead)
class SuspectControllerSpec extends Specification {

    def 'test something'() {
        expect:
        1==1
    }
}

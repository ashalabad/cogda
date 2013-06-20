package com.cogda.domain



import org.junit.*
import grails.test.mixin.*

/**
 * ContactControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(ContactController)
@Mock(Contact)
class ContactControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    // TODO: Add the tests of the JSON responses.

    void testAddTests(){
        assert 1 == 1
    }
}

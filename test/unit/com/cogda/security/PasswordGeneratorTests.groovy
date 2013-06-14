package com.cogda.security

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class PasswordGeneratorTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    /**
     * Test the create temp password
     */
    void testCreateTempPassword() {
        (0..1000).each{
            String password = PasswordGenerator.createTempPassword()
            assert password.size() == PasswordGenerator.COUNT
            assert password ==~ PasswordGenerator.PASSWORD_REGEX
        }
    }
}

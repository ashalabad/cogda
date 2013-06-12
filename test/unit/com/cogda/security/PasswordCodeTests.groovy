package com.cogda.security

import com.cogda.domain.security.PasswordCode
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(DomainClassUnitTestMixin)
@TestFor(PasswordCode)
class PasswordCodeTests {
    PasswordCode passwordCode

    void testNullable() {
        mockDomain(PasswordCode)
        passwordCode = new PasswordCode()
        passwordCode.token = null
        passwordCode.username = null

        assertFalse passwordCode.validate()

        assert "nullable" == passwordCode.errors["token"].code
        assert "nullable" == passwordCode.errors["username"].code

        passwordCode = new PasswordCode()
        passwordCode.username = "username"

        assert passwordCode.validate()
        assert passwordCode.save()




    }
}

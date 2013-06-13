package com.cogda.domain.admin

import com.cogda.account.EmailSendReasonCode
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(DomainClassUnitTestMixin)
@TestFor(EmailConfirmationLog)
class EmailConfirmationLogTests {

    /**
     * Everything is Nullable in EmailConfirmationLog - so null it up.
     */
    void testNullable() {
        EmailConfirmationLog emailConfirmationLog = new EmailConfirmationLog()

        assertFalse emailConfirmationLog.validate()
        assert "nullable" == emailConfirmationLog.errors["emailSendReason"].code

        emailConfirmationLog.emailSendReason = EmailSendReasonCode.ACCOUNT_ACTIVATION_EMAIL
        assert emailConfirmationLog.validate()
    }
}

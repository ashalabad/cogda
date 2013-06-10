package com.cogda.domain.onboarding

import com.cogda.BaseIntegrationTest
import com.cogda.account.EmailSendReasonCode
import com.cogda.account.EmailSendStatusCode
import com.cogda.common.MarkupLanguage
import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.multitenant.CustomerAccountServiceTests
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.commons.GrailsApplication

import static org.junit.Assert.*
import org.junit.*

class AccountActivationServiceTests extends BaseIntegrationTest {

    AccountActivationService accountActivationService
    GrailsApplication grailsApplication
    SpringSecurityService springSecurityService

    @Before
    void setUp() {
        createCompanyTypes()
        createValidSystemEmailMessageTemplate()
    }

    @After
    void tearDown() {
        // Tear down logic here
        Registration.list().each { Registration registration ->
            registration.emailConfirmationLogs.each { log ->
                registration.removeFromEmailConfirmationLogs(log)
            }
        }
        Registration.executeUpdate("delete from Registration")
        SystemEmailMessageTemplate.executeUpdate("delete from SystemEmailMessageTemplate")
        CompanyType.executeUpdate("delete from CompanyType")
    }

    @Test
    void testPrepareEmailVerification() {
        Registration registration = createValidRegistration()
        String emailVerificationUrl = "https://cogda.com/register/verifyRegistration"
        EmailConfirmationLog emailConfirmationLog = accountActivationService.prepareEmailVerification(registration, emailVerificationUrl)

        SystemEmailMessageTemplate systemEmailMessageTemplate = SystemEmailMessageTemplate.first()
        assert systemEmailMessageTemplate, "SystemEmailMessageTemplate was not setup properly"

        assert emailConfirmationLog, "EmailConfirmationLog is null"
        assert emailConfirmationLog.emailSendReason == EmailSendReasonCode.INITIAL_ACCOUNT_VERIFICATION_EMAIL
        assert emailConfirmationLog.emailErrors == null
        assert emailConfirmationLog.emailTo.equals(registration.emailAddress)
        assert emailConfirmationLog.emailFrom.equals(grailsApplication.config.grails.mail.default.from)
        assert emailConfirmationLog.emailSubject.equals(systemEmailMessageTemplate.subject)
        assert emailConfirmationLog.emailBody.contains(emailVerificationUrl)

    }

    @Test
    void testSendEmailVerification() {
        Registration registration = createValidRegistration()
        String emailVerificationUrl = "https://cogda.com/register/verifyRegistration"
        EmailConfirmationLog emailConfirmationLog = accountActivationService.prepareEmailVerification(registration, emailVerificationUrl)

        accountActivationService.sendEmailVerification(emailConfirmationLog)

        assert emailConfirmationLog.emailSendStatus == EmailSendStatusCode.SUCCESS


    }
}

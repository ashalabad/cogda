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

    }

    @After
    void tearDown() {

    }

    @Test
    void testConfirmEmailVerification() {
        createCompanyTypes()

        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"
        createConfirmEmailVerificationMessageTemplate()

        EmailConfirmationLog emailConfirmationLog = accountActivationService.confirmEmailVerification(registration)

        SystemEmailMessageTemplate systemEmailMessageTemplate = SystemEmailMessageTemplate.findByTitle("VERIFIED_SUCCESSFULLY_EMAIL")
        assert systemEmailMessageTemplate, "SystemEmailMessageTemplate was not setup properly"

        assert emailConfirmationLog, "EmailConfirmationLog is null"
        assert emailConfirmationLog.emailSendReason == EmailSendReasonCode.EMAIL_VERIFIED_SUCCESSFULLY
        assert emailConfirmationLog.emailErrors == null
        assert emailConfirmationLog.emailTo.equals(registration.emailAddress)
        assert emailConfirmationLog.emailFrom.equals(grailsApplication.config.grails.mail.default.from)
        assert emailConfirmationLog.emailSubject.equals(systemEmailMessageTemplate.subject)
    }

    @Test
    void testSendEmailVerified() {
        createCompanyTypes()
        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"

        createConfirmEmailVerificationMessageTemplate()

        EmailConfirmationLog emailConfirmationLog = accountActivationService.confirmEmailVerification(registration)

        accountActivationService.sendEmailVerified(emailConfirmationLog)
        assert emailConfirmationLog.emailSendStatus == EmailSendStatusCode.SUCCESS

    }

    @Test
    void testPrepareEmailVerification() {
        createCompanyTypes()
        createValidSystemEmailMessageTemplate()
        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"
        String emailVerificationUrl = "https://cogda.com/emailVerification/verify?t=20020202020"
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
        createCompanyTypes()
        createValidSystemEmailMessageTemplate()
        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"
        String emailVerificationUrl = "https://cogda.com/register/verifyRegistration"
        EmailConfirmationLog emailConfirmationLog = accountActivationService.prepareEmailVerification(registration, emailVerificationUrl)

        accountActivationService.sendEmailVerification(emailConfirmationLog)

        assert emailConfirmationLog.emailSendStatus == EmailSendStatusCode.SUCCESS


    }

    /**
     * Creates a Valid SystemEmailMessageTemplate
     * @return SystemEmailMessageTemplate
     */
    private SystemEmailMessageTemplate createValidSystemEmailMessageTemplate(){
        SystemEmailMessageTemplate accountActivationEmailMessage = new SystemEmailMessageTemplate()
        accountActivationEmailMessage.markupLanguage = MarkupLanguage.MARKDOWN
        accountActivationEmailMessage.title = "INITIAL_ACCOUNT_ACTIVATION_EMAIL"
        accountActivationEmailMessage.description = "The email message that is sent to the User when activating a new account."
        accountActivationEmailMessage.subject = "Cogda Email Verification"
        accountActivationEmailMessage.fromEmail = "mail@cogda.com"
        accountActivationEmailMessage.body = """
Thank you for your interest in {appName}.  We sincerely look forward to serving you and your company.

Please click the following verification link to activate your new {appName} account.

{activationUrl}

Upon successful activation of your account your company information will be verified and your company's account provisioned on {appName}!

Thank you!

{appName} Team"""
        accountActivationEmailMessage.acceptsParameters = true
        accountActivationEmailMessage.requiredParameterNames = ['appName', 'activationUrl']
        assert accountActivationEmailMessage.save(flush:true), "AccountActivationEmailMessage save failed: ${accountActivationEmailMessage.errors}"

        log.debug "Successfully saved AccountActivationEmailMessage"

        return accountActivationEmailMessage
    }
}

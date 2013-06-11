package com.cogda.domain.admin

import com.cogda.BaseIntegrationTest
import com.cogda.account.EmailSendReasonCode
import com.cogda.account.EmailSendStatusCode
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.onboarding.Registration
import org.codehaus.groovy.grails.commons.GrailsApplication

import static org.junit.Assert.*
import org.junit.*

class CustomerNotificationServiceTests extends BaseIntegrationTest {

    GrailsApplication grailsApplication
    CustomerNotificationService customerNotificationService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
        UserProfileEmailAddress.executeUpdate("delete from UserProfileEmailAddress")
        UserProfile.executeUpdate("delete from UserProfile")
        EmailConfirmationLog.executeUpdate("delete from EmailConfirmationLog")
    }

    @Test
    void testPrepareResetPasswordMessage() {
        UserProfile userProfile = createUserProfile()
        createResetPasswordMessageTemplate()
        String resetPasswordUrl = "https://cogda.com/password/resetPassword?t=20020202020"
        EmailConfirmationLog emailConfirmationLog = customerNotificationService.prepareResetPasswordMessage(userProfile, resetPasswordUrl)

        SystemEmailMessageTemplate systemEmailMessageTemplate = SystemEmailMessageTemplate.findByTitle("RESET_PASSWORD_EMAIL")
        assert systemEmailMessageTemplate, "SystemEmailMessageTemplate was not setup properly"

        assert emailConfirmationLog, "EmailConfirmationLog is null"
        assert emailConfirmationLog.emailSendReason == EmailSendReasonCode.USER_RESET_PASSWORD
        assert emailConfirmationLog.emailErrors == null
        assert emailConfirmationLog.emailTo.equals(userProfile.primaryEmailAddress)
        assert emailConfirmationLog.emailFrom.equals(grailsApplication.config.grails.mail.default.from)
        assert emailConfirmationLog.emailSubject.equals(systemEmailMessageTemplate.subject)
        assert emailConfirmationLog.emailBody.contains(resetPasswordUrl)
    }

    @Test
    void testSendMail(){

        EmailConfirmationLog emailConfirmationLog = new EmailConfirmationLog()
        emailConfirmationLog.emailSendReason = EmailSendReasonCode.USER_RESET_PASSWORD
        emailConfirmationLog.emailTo = "primary@cogda.com"
        emailConfirmationLog.emailFrom = grailsApplication.config.grails.mail.default.from
        emailConfirmationLog.emailSubject = "sendMail email subject"
        emailConfirmationLog.emailBody = "sendMail email body"
        assert emailConfirmationLog.save(flush:true), "Save was not successful on EmailConfirmationLog"

        customerNotificationService.sendMail(emailConfirmationLog)

        assert emailConfirmationLog.emailErrors == null
        assert emailConfirmationLog.emailSendStatus == EmailSendStatusCode.SUCCESS

    }
}

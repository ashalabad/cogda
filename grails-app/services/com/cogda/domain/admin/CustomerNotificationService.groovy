package com.cogda.domain.admin

import com.cogda.account.EmailSendReasonCode
import com.cogda.account.EmailSendStatusCode
import com.cogda.domain.UserProfile
import grails.gsp.PageRenderer
import grails.plugin.mail.MailService
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.grails.plugin.platform.events.EventMessage
import org.springframework.mail.MailMessage

/**
 * CustomerNotificationService
 * A service class encapsulates the core business logic of a Grails application
 */
class CustomerNotificationService {
    GrailsApplication grailsApplication
    PageRenderer groovyPageRenderer
    MailService mailService

    static transactional = true

    /**
     * Send an email confirmation based on the
     * passed in email address in the args and
     * the Registration object.
     *
     * @param args Map of arguments:
     * emailAddress - Required, email address
     *
     * @return AccountActivationEmailConfirmation
     */
    public EmailConfirmationLog prepareResetPasswordMessage(UserProfile userProfile, String resetPasswordUrl) {

        SystemEmailMessageTemplate resetPasswordMessage = SystemEmailMessageTemplate.findByTitle("RESET_PASSWORD_EMAIL")

        assert resetPasswordMessage, "Reset Password Email Message Was Not Found!"

        Map messageParameters = [:]
        messageParameters.appName = grailsApplication.config.application.name
        messageParameters.resetPasswordUrl = resetPasswordUrl

        // Apply the dynamic values to the body of the email template
        String messageBody = resetPasswordMessage.writeMessageOutput(messageParameters)

        String emailBody = groovyPageRenderer.render(view:'/email/confirmationLayout', model:[body:messageBody])

        EmailConfirmationLog confLog = new EmailConfirmationLog()
        confLog.emailSendReason = EmailSendReasonCode.USER_RESET_PASSWORD
        confLog.emailTo = userProfile.primaryEmailAddress
        confLog.emailFrom = grailsApplication.config.grails.mail.default.from
        confLog.emailSubject = resetPasswordMessage.subject
        confLog.emailBody = messageBody

        confLog.save()

        // Fire the sendEmailConfirmation event method asynchronously after the successful
        // save of EmailConfirmationLog for email processing.
        event('sendEmailResetPassword', confLog)

        return confLog
    }

    @grails.events.Listener(topic="sendEmailResetPassword")
    def sendEmailResetPassword(EventMessage eventMessage){
        EmailConfirmationLog confLog = (EmailConfirmationLog)eventMessage.data
        sendMail(confLog)
    }

    /**
     * Sends the email verification.
     */
    def sendMail(EmailConfirmationLog confLog){
        MailMessage message
        try {
            message = mailService.sendMail {
                to confLog.emailTo
                from confLog.emailFrom
                subject confLog.emailSubject
                html confLog.emailBody
            }
            confLog.emailSendStatus = EmailSendStatusCode.SUCCESS
            confLog.save()
        } catch (Throwable t) {
            confLog.emailErrors = t.toString()
            confLog.emailSendStatus = EmailSendStatusCode.FAILURE
            confLog.save()
            log.error("Mail send failed", t)
        }
    }
}

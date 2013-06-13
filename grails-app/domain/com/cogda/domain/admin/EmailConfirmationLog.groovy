package com.cogda.domain.admin

import com.cogda.account.EmailSendReasonCode
import com.cogda.account.EmailSendStatusCode

/**
 * EmailConfirmationLog
 * A domain class describes the data object and it's mapping to the database
 */
class EmailConfirmationLog {

    /**
     * The status code assigned to the
     * activity that indicates whether
     * the email was sent successfully or
     * failed.  Success means that the mailService
     * sent the email, not that the email address
     * it was sent to was valid.
     * SUCCESS, FAILURE
     */
    EmailSendStatusCode emailSendStatus

    /**
     * The reason code associated with WHY we
     * sent the email. e.g. INITIAL_VERIFICATION_EMAIL,
     * REMINDER_VERIFICATION_EMAIL,
     * TIMEOUT_EMAIL, ACCOUNT_ACTIVATION_EMAIL
     */
    EmailSendReasonCode emailSendReason

    /**
     * Any errors associated with the sending of
     * emails to the customer.
     */
    String emailErrors

    /**
     * The email address(es) that this email was sent to.
     */
    String emailTo

    /**
     * The email address that we sent this email from
     */
    String emailFrom

    /**
     * The email subject that was used
     */
    String emailSubject

    /**
     * The body of the email sent
     */
    String emailBody

    /* Automatic timestamping by Hibernate */

    /**
     * Date dateCreated<br>
     * Hibernate managed audit-logging
     */
    Date dateCreated

    /**
     * Date lastUpdated
     * Hibernate managed audit-logging
     */
    Date lastUpdated

    static mapping = {
        emailErrors type:'text'
        emailBody type:'text'
    }

    static constraints = {
        emailSendStatus(nullable:true)
        emailSendReason(nullable:false)
        emailErrors(nullable:true)
        emailTo(nullable:true)
        emailFrom(nullable:true)
        emailSubject(nullable:true)
        emailBody(nullable:true)
    }

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return """
emailSendStatus: $emailSendStatus
emailSendReason: $emailSendReason
emailErrors: $emailErrors
emailTo: $emailTo
emailSubject: $emailSubject
emailBody: $emailBody
dateCreated: $dateCreated
lastUpdated: $lastUpdated"""
    }
}

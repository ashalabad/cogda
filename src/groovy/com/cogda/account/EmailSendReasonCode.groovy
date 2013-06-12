package com.cogda.account

/**
 * EmailSendReasonCode provides codes that 
 * indicate why the email was sent to the recipient.
 * 
 * @author christopher
 */
enum EmailSendReasonCode {
	INITIAL_ACCOUNT_VERIFICATION_EMAIL, REMINDER_ACCOUNT_VERIFICATION_EMAIL, 
	ACCOUNT_VERIFICATION_TIMEOUT_EMAIL, ACCOUNT_ACTIVATION_EMAIL,
    ACCOUNT_WELCOME_EMAIL, EMAIL_VERIFIED_SUCCESSFULLY, USER_RESET_PASSWORD
}

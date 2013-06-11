package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus

/**
 * EmailVerificationController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class EmailVerificationController {

    AccountActivationService accountActivationService

    def verify(){
        String token = params.t?.trim()

        if(!token){
            flash.error = message(code:'registration.token.invalid')
            return
        }

        Registration registration = token ? Registration.retrievePendingRegistrationByToken(token) : null

        if(!registration){
            registration = Registration.findByToken(token)
            if(registration){
                // Check the current status and give the user a meaningful response.
                switch(registration.registrationStatus){
                    case RegistrationStatus.AWAITING_ADMIN_APPROVAL:
                        flash.message = message(code:'registration.status.awaitingAdminApproval')
                        return
                    case RegistrationStatus.APPROVED:
                        flash.message = message(code:'registration.status.approved')
                        return
                }
            }

            flash.error = message(code:'registration.token.invalid')
            return
        }

        // The token matches so verify the email
        accountActivationService.confirmEmailVerification(registration)

        flash.message = message(code:'registration.email.verified')




    }
}

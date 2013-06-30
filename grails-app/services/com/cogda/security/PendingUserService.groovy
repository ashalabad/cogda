package com.cogda.security

import com.cogda.domain.admin.CustomerNotificationService
import com.cogda.domain.security.PendingUser
import com.cogda.multitenant.CustomerAccountService
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import grails.validation.ValidationException
/**
 * PendingUserService
 * A service class encapsulates the core business logic of a Grails application
 */
class PendingUserService {
    private static final log = LogFactory.getLog(this)

    CustomerNotificationService customerNotificationService
    CustomerAccountService customerAccountService
    LinkGenerator grailsLinkGenerator

    /**
     * Onboards the PendingUser by
     * @param importedUser
     */
    public void onboardPendingUser(PendingUser pendingUser, String username) {
        pendingUser.pendingUserStatus = PendingUserStatus.COMPLETED
        pendingUser.onboardedDate = new Date()
        pendingUser.onboardedSuccessfully = Boolean.TRUE
        pendingUser.username = username  // to allow us to link back to the User that was created from the PendingUser

        try{
            pendingUser.save(failOnError:true)
        }catch(ValidationException ve){
            pendingUser.getErrorStrings().each { k, v ->
                log.error("Field: " + k + " Error: " + v)
            }
        }
    }

    /**
     * Sends the user email invitations
     * @param importedUserIds
     */
    public void sendUserEmailInvitations(List importedUserIds){
        importedUserIds.each { Long id ->
            PendingUser pendingUser = PendingUser.get(id)
            if(pendingUser){
                String baseURL = customerAccountService.getCustomerAccountBaseUrl(pendingUser.tenantId)
                String inviteLink = grailsLinkGenerator.link(base:baseURL, controller:"userInvite", action:"index", params:[t:pendingUser.token])
                customerNotificationService.prepareUserInvitationMessage(pendingUser, inviteLink)
            }
        }
        log.debug "completed preparing user invitation messages for PendingUser with id(s) ${importedUserIds}"
    }

    /**
     * Delete the passed in pendingUserIds
     * @param pendingUserIds
     */
    public void delete(List pendingUserIds){
        PendingUser.executeUpdate("delete from PendingUser where id in (:pendingUserIds)", [pendingUserIds:pendingUserIds])
    }
}

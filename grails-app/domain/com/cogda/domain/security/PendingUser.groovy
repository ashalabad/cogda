package com.cogda.domain.security

import com.cogda.security.PendingUserStatus
import grails.plugin.multitenant.core.annotation.MultiTenant
import groovy.transform.ToString

/**
 * PendingUser
 * This Domain Class serves as a holding tank for the users that are imported during the UserImport process.
 *
 */
@ToString(includeNames=true, includeFields=true)
@MultiTenant
class PendingUser {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    /**
     * The username of the person that
     * uploaded this User.
     */
    String loadedByUsername

    /**
     * The date that the person uploaded this user.
     */
    Date loadedDate

    String firstName
    String lastName
    String emailAddress
    String securityRoles

    /**
     * Boolean flag indicating if this PendingUser was successfully added to the com.cogda.domain.security.User
     * domain class.
     */
    Boolean onboardedSuccessfully = Boolean.FALSE

    /**
     * The date that the PendingUser was added to the com.cogda.domain.security.User domain class.
     */
    Date onboardedDate

    /**
     * Unique token
     */
    String token = UUID.randomUUID().toString().replaceAll('-', '')

    /**
     * This is the username that is created by the user after they
     * have created their User account in Cogda.
     */
    String username

    /**
     * The current status of this PendingUser
     */
    PendingUserStatus pendingUserStatus = PendingUserStatus.PENDING

    static constraints = {
        loadedByUsername(nullable:false)
        loadedDate(nullable:false)
        firstName()
        lastName()
        emailAddress()
        securityRoles()
        onboardedSuccessfully(nullable:false)
        onboardedDate(nullable:true)
        token(nullable:false)
        pendingUserStatus(nullable:false)
        username(nullable:true)
    }

    /**
     * Finds a PendingUser based on the passed in token
     * and the PendingUserStatus.PENDING
     * @param token
     * @return PendingUser
     */
    static PendingUser retrievePendingByToken(String token){
        def c = PendingUser.createCriteria()
        PendingUser pendingUser = c.get(){
            eq("token",token)
            eq("pendingUserStatus", PendingUserStatus.PENDING)
        }
        return pendingUser
    }
}

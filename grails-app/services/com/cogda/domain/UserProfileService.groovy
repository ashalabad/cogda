package com.cogda.domain

import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.User

/**
 * UserProfileService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserProfileService {

    /**
     * Creates a UserProfile based on the passed in User object and the
     * Registration object.
     * @param user
     * @param registration
     * @return UserProfile
     */
    UserProfile createUserProfile(User user, Registration registration){
        UserProfile userProfile = new UserProfile()
        userProfile.user = user
        userProfile.firstName = registration.firstName
        userProfile.lastName = registration.lastName
        userProfile.save() ?: log.error ("Error saving UserProfile errors -> ${userProfile.errors}")

        addUserProfileEmailAddress(userProfile, registration.emailAddress, Boolean.TRUE)

        return userProfile
    }

    /**
     * Adds a userProfileEmailAddress to the passed in UserProfile
     * @param userProfile
     * @param emailAddress
     * @param primary
     * @return
     */
    UserProfileEmailAddress addUserProfileEmailAddress(UserProfile userProfile, String emailAddress, Boolean primary = Boolean.FALSE){
        UserProfileEmailAddress userProfileEmailAddress = new UserProfileEmailAddress()
        userProfileEmailAddress.emailAddress = emailAddress
        userProfileEmailAddress.primaryEmailAddress = true
        userProfileEmailAddress.userProfile = userProfile

        userProfile.addToUserProfileEmailAddresses(userProfileEmailAddress)

        return userProfileEmailAddress
    }

    /**
     * Creates a new UserProfile
     * @param user
     * @param firstName
     * @param lastName
     * @param emailAddress
     * @return UserProfile
     */
    UserProfile createUserProfile(User user, String firstName, String lastName, String emailAddress){
        UserProfile userProfile
        if(!UserProfile.findByUser(user)){
            userProfile = new UserProfile()
            userProfile.user = user
            userProfile.firstName = firstName
            userProfile.lastName = lastName

            userProfile.save()

            addUserProfileEmailAddress(userProfile, emailAddress, Boolean.TRUE)
        }

        return userProfile

    }
}

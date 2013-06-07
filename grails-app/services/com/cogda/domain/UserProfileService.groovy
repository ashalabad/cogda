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
        userProfile.save(failOnError: true)

        UserProfileEmailAddress userProfileEmailAddress = new UserProfileEmailAddress()
        userProfileEmailAddress.emailAddress = registration.emailAddress
        userProfileEmailAddress.primaryEmailAddress = true
        userProfileEmailAddress.userProfile = userProfile

        userProfile.addToUserProfileEmailAddresses(userProfileEmailAddress)
    }
}

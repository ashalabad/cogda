package com.cogda.domain

import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.User
import org.hibernate.Criteria

/**
 * UserProfileService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserProfileService {

    List<UserProfile> internalUserProfileList(Map params){
        params.q = "%"
        return internalUserProfileSearch(params)
    }

    /**
     * returns search results constrained to the active tenant id
     * by doing a join of the UserProfile to the User object.
     * This method cannot be used outside of a an active CustomerAccount.
     * @param params
     * @return List
     */
    List<UserProfile> internalUserProfileSearch(Map params){

        if(!params.q){
            return [] // Please provide a search query
        }

        String searchQuery = "%" + params.q.trim() + "%"
        params.max = Math.min(params.max ?: 10, 100)
        params.sort = params.sort ?: "lastName"
        params.order = params.order ?: "asc"
        params.offset = params.offset ?: 0

        String query = "select userProfile from User user, UserProfile userProfile where " +
                " userProfile.user.id = user.id and user.enabled is :enabled and user.accountExpired is :accountExpired " +
                " and " +
                " ( lower(userProfile.firstName) like lower(:searchQuery) or " +
                "   lower(userProfile.lastName)  like lower(:searchQuery) or " +
                "   lower(user.username) like lower(:searchQuery) " +
                " ) " +
                " order by userProfile.lastName asc, userProfile.firstName asc "

        List<UserProfile> userProfiles = UserProfile.executeQuery(query,
                [enabled:true, accountExpired:false, searchQuery:searchQuery],
                [max:params.max, offset:params.offset, cache:true])

        return userProfiles
    }

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

package com.cogda.security

import com.cogda.domain.UserProfile
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole

/**
 * UserService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserService {

    static transactional = true

    /**
     * Creates a User domain class object in the database.
     * @param user
     * @return
     */
    def create(User user) {
        user.save(failOnError: true)
    }

    /**
     * Creates a new User with the specified roles.
     * @param user
     * @param roles
     * @return
     */
    def create(User user, List<Role> roles){
        create(user)
        roles.each { Role role ->
            UserRole.create(user, role)
        }

    }

    def createWithStringRoles(User user, List<String> roles){
        create(user)

        roles.each { String authority ->
            UserRole.create(user, Role.findByAuthority(authority))
        }
    }

    /**
     * Checks for an available Username across all of the
     * Users stored in the system.
     * @param username
     * @return boolean
     */
    def availableUsername(String username){
        User.withoutTenantRestriction {
            if(User.findByUsername(username)){
                return false
            }
            return true
        }
    }

    /**
     * Creates a user based on the passed in parameters.
     * encodePassword boolean if set to true
     * @param username
     * @param password
     * @param encodePassword
     * @return
     */
    def createUser(String username, String password, boolean encodePassword = false){
        // Create the user
        User user = new User()
        user.username = username
        user.password = password
        user.enabled = true
        user.accountExpired = false
        user.accountLocked = false
        user.passwordExpired = false
        user.encodePassword = false  // do not allow the password to be re-encoded
        user.save(failOnError:true)
    }
}

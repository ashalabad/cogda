package com.cogda.security

import com.cogda.domain.UserProfileService
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.PendingUser
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import org.apache.commons.lang.StringUtils

/**
 * UserService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserService {

    static transactional = true

    UserProfileService userProfileService
    PendingUserService pendingUserService
    /**
     * Creates a User based on the passed in UserInviteCommand object and
     * the corresponding PendingUser domain class which it reads in from the
     * database to determine which Roles to grant the newly created User.
     *
     * @param userInviteCommand
     */
    public void createFromUserInvite(UserInviteCommand userInviteCommand){
        User.withTransaction {
            PendingUser pendingUser = PendingUser.findByToken(userInviteCommand.t)
            if(pendingUser){
                // Step 1: Create the User
                User user = createUser(userInviteCommand.username, userInviteCommand.password)
                // Step 2: Add the securityRoles from the PendingUser domain class to the newly created User
                addImportedUserRoles(user, pendingUser)
                // Step 3: Create the UserProfile for the newly created User
                userProfileService.createUserProfile(user, pendingUser.firstName, pendingUser.lastName, pendingUser.emailAddress)
                // Step 4: Update the Status of the PendingUser from PENDING to COMPLETE
                pendingUserService.onboardPendingUser(pendingUser, user.username)
            }
        }
    }

    /**
     *
     * @param user
     * @param importedUser
     */
    public void addImportedUserRoles(User user, PendingUser importedUser){
        if(importedUser.securityRoles.contains(",")){
            List securityRoles = importedUser.securityRoles.split(",") as List
            securityRoles = securityRoles.unique()
            securityRoles = securityRoles.collect {
                StringUtils.strip(it)
            }
            securityRoles.each { String authority ->
                Role role = Role.findByAuthority(authority)
                if(role){
                    UserRole.create(user, role)
                }else{
                    log.warn ("Ignoring the provided authority since it was not found in Role table " + authority)
                }
            }
            if(!securityRoles.contains(CustomerAccountService.ROLE_USER)){
                Role roleUser = Role.findByAuthority(CustomerAccountService.ROLE_USER)
                UserRole.create(user, roleUser)
            }
        }else{
            String authority = StringUtils.strip(importedUser.securityRoles)
            if(authority){
                Role role = Role.findByAuthority(authority)
                if(role){
                    UserRole.create(user, role)
                }
            }
            if(!authority.equals(CustomerAccountService.ROLE_USER)){
                Role roleUser = Role.findByAuthority(CustomerAccountService.ROLE_USER)
                UserRole.create(user, roleUser)
            }
        }
    }

    /**
     * Creates a User domain class object in the database.
     * @param user
     * @return
     */
    def create(User user) {
        user.save() ?: log.error("Error saving User errors -> ${user.errors}")
        return user
    }

    /**
     * Creates a new User with the specified roles.
     * @param user
     * @param roles
     * @return
     */
    def create(User user, List<Role> roles){
        create(user)
        if(!user.hasErrors()){
            roles.each { Role role ->
                UserRole.create(user, role)
            }
        }else{
            log.error ("Unable to apply roles to User due to validation errors on User errors -> ${user.errors}")
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
        Boolean available = Boolean.TRUE
        CustomerAccount.withoutTenantRestriction{
            if(User.findByUsername(username)){
                available = Boolean.FALSE
            }
        }

        if(!available){
            return Boolean.FALSE
        }

        // Now check Registration
        if(Registration.findByUsername(username)){
            return Boolean.FALSE
        }

        return Boolean.TRUE
    }

    /**
     * Creates a user based on the passed in parameters.
     * encodePassword boolean if set to true
     * @param username
     * @param password
     * @param encodePassword
     * @return User user
     */
    public User createUser(String username, String password, boolean encodePassword = true){
        // Create the user
        User user = new User()
        user.username = username
        user.password = password
        user.enabled = true
        user.accountExpired = false
        user.accountLocked = false
        user.passwordExpired = false
        user.encodePassword = encodePassword  // do not allow the password to be re-encoded
        user.save() ?: log.error ("Error saving User errors -> ${user.errors}")
        return user
    }
}

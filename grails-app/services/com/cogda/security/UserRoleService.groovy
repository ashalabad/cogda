package com.cogda.security

import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole

/**
 * UserRoleService
 * A service class encapsulates the core business logic of a Grails application
 */
class UserRoleService {

    static transactional = true

    /**
     * Adds the List roles to the User.
     * @param user
     * @param roles
     */
    def createUserRoles(User user, List<Role> roles){
        roles.each { role ->
            UserRole.create(user, role)
        }
    }
}

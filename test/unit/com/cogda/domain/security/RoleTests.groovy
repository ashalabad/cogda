package com.cogda.domain.security

import com.cogda.multitenant.CustomerAccountService
import grails.test.mixin.domain.DomainClassUnitTestMixin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(Role)
@TestMixin(DomainClassUnitTestMixin)
class RoleTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testAdminAssignableAuthorities() {
        mockDomain(Role)

        Role role = new Role(authority: CustomerAccountService.ROLE_ADMINISTRATOR, systemRole: true, description: "GOD")
        assert role.save(), "Role with authority ${role.authority} could not be saved. Role errors -> ${role.errors}"

        Role.ADMIN_ASSIGNABLE_AUTHORITIES.each { String authority ->
            Role r = new Role(authority: authority, systemRole: true, description: "$authority description")
            assert r.save(), "Role with authority ${r.authority} could not be saved. Role errors -> ${r.errors}"
        }

        List<Role> adminAssignableRoles = Role.adminAssignableAuthorities()
        assert adminAssignableRoles, "adminAssignableRoles is null"
        assert adminAssignableRoles.size() == Role.ADMIN_ASSIGNABLE_AUTHORITIES.size(), "adminAssignableRoles.size() should equal ${Role.ADMIN_ASSIGNABLE_AUTHORITIES.size()}"
        assert !adminAssignableRoles.contains(role), "adminAssignableRoles list contains CustomerAccountService.ROLE_ADMINISTRATOR"
        Role.ADMIN_ASSIGNABLE_AUTHORITIES.each { String authority ->
            assert adminAssignableRoles.find() { it.authority.equals(authority)}
        }



    }
}

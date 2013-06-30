package com.cogda.domain.security

import com.cogda.multitenant.CustomerAccountService
import grails.plugin.multitenant.core.annotation.MultiTenant
import org.grails.datastore.mapping.query.api.Criteria


@MultiTenant
class Role {
	String authority
    String description

    /**
     * A systemRole is a Role that cannot be modified by the
     * Admin of a CustomerAccount.
     */
    Boolean systemRole

	static mapping = {
		cache true
	}

	static constraints = {
		authority(blank: false, unique: 'tenantId')
        description(nullable:true, maxSize:250)
        systemRole(nullable:true)
	}

    /**
     * This is a list of AUTHORITIES that a User can assign to other users
     * if they have the CustomerAccountService.ROLE_HOST_ADMINISTRATOR
     * Authority granted to them.
     */
    public final static List<String> ADMIN_ASSIGNABLE_AUTHORITIES = [
            CustomerAccountService.ROLE_HOST_ADMINISTRATOR,
            CustomerAccountService.ROLE_UNDERWRITER,
            CustomerAccountService.ROLE_CUSTOMER_SERVICE_REP,
            CustomerAccountService.ROLE_BRANCH_MANAGER,
            CustomerAccountService.ROLE_MAREKETING_MANAGER,
            CustomerAccountService.ROLE_SALES_MANAGER,
            CustomerAccountService.ROLE_MARKETER,
            CustomerAccountService.ROLE_COMPANY_MANAGER].sort()



    /**
     * adminAssignableAuthorities queries the database based on the ADMIN_ASSIGNABLE_AUTHORITIES
     * for the Role domain classes.
     * @return List
     */
    static List<Role> adminAssignableAuthorities(){
        Criteria c = Role.createCriteria()
        List<Role> adminAssignableAuthorities = c.list {
            inList("authority", Role.ADMIN_ASSIGNABLE_AUTHORITIES)
        }

        return adminAssignableAuthorities
    }
}

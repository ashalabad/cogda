package com.cogda.domain.security

import grails.plugin.multitenant.core.annotation.MultiTenant


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
		authority(blank: false, unique: true)
        description(nullable:true, maxSize:250)
        systemRole(nullable:true)
	}
}

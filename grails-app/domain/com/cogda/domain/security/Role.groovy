package com.cogda.domain.security

import grails.plugin.multitenant.core.annotation.MultiTenant


@MultiTenant
class Role {
	String authority
    String description

	static mapping = {
		cache true
	}

	static constraints = {
		authority(blank: false, unique: true)
        description(nullable:true, maxSize:250)
	}
}

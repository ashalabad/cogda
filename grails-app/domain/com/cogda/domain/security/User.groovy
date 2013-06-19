package com.cogda.domain.security

import grails.plugin.multitenant.core.annotation.MultiTenant


@MultiTenant
class User {
	transient springSecurityService

	String username
	String password

    String accountId = UUID.randomUUID().toString().replaceAll('-', '')

    boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    /**
     * Set encodePassword to false to
     * bypass the encodePassword procedure
     * this is used in situations where the password
     * that is being assigned to the user has already been
     * encoded.
     */
    boolean encodePassword = true

    static transients = ['bypassEncodePassword']

	static constraints = {
		username(blank: false, unique: true)
		password(blank: false)
        accountId(nullable:false, blank:false, unique:true)
	}

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
        if(encodePassword){
		    password = springSecurityService.encodePassword(password)
        }
	}

    String toString(){
        """
        User:
        id: $id
        version: $version
        tenantId: $tenantId
        username: $username
        password: null
        enabled: $enabled
        accountExpired: $accountExpired
        accountLocked: $accountLocked
        passwordExpired: $passwordExpired
        """
    }
}

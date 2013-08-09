package com.cogda.domain.storage

import com.cogda.domain.security.User
import com.cogda.multitenant.Company
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * StorageReference
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class StorageReference {

	Long	id
	Long	version
	Date	dateCreated
	Date	lastUpdated

    String  md5
    String  fileReference
    User    user

    //static hasOne = {}
    //static mapping = {}

    static constraints = {
        dateCreated(blank:false, nullable: false)
        lastUpdated(blank:false,nullable: false)
        fileReference(blank: false, nullable: false)
        user(nullable: false)
    }

    /*
     * Methods of the Domain Class
     */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}

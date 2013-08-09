package com.cogda.multitenant

import com.cogda.domain.security.User
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * Document
 * A domain class describes the data object and it's mapping to the database
  */
@MultiTenant
class Document {

	Long	id
	Long	version

	Date	dateCreated
	Date	lastUpdated

    String name
    String description
    String category
    Integer currentVersion
    Date    originationDate
    User    createdBy
    User    lastUpdatedBy
//	static belongsTo	= []	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
//	static hasMany		= []	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static mappedBy		= []	// specifies which property should be used in a mapping 

    static mapping = {
        table "storage_document"
    }

    static constraints = {
        originationDate(nullable: true)
    }
//	@Override	// Override toString for a nicer / more descriptive UI
	public String toString() {
		return "${name}-${description}-${category}";
	}
}

package com.cogda.multitenant

import com.cogda.domain.DocumentDataStreamType
import com.cogda.domain.security.User
import com.cogda.domain.storage.StorageReference
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * DocumentVersion
 * A domain class describes the data object and it's mapping to the database
 * The link between a Document and a Storage Reference
 */
@MultiTenant
class DocumentVersion {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    Integer documentVersion
    DocumentDataStreamType streamType
    Document document
    StorageReference storageReference
    User createdBy

	static belongsTo	= [document:Document]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
	//static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping

    static mapping = {
    }

    static constraints = {
    }

    /*
     * Methods of the Domain Class
     */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}

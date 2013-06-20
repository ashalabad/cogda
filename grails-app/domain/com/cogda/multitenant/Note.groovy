package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * Note
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class Note {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    String notes

    static mapping = {
        notes type:'text'
    }

    static constraints = {
        notes(maxSize:500)
    }

}

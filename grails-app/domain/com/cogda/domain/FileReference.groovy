package com.cogda.domain

/**
 * FileReference
 * A domain class describes the data object and it's mapping to the database
 */
class FileReference {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static mapping = {
    }

    static constraints = {

    }

}

package com.cogda.domain

/**
 * Note
 * A domain class describes the data object and it's mapping to the database
 */
class Note {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    String notes

    static mapping = {
        notes type:'text'
    }

    static constraints = {
        notes(maxSize:500)
    }

}

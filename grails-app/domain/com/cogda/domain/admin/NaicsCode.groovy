package com.cogda.domain.admin

/**
 * NaicsCode
 * A domain class describes the data object and it's mapping to the database
 */
class NaicsCode {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Long code
    String description

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated


    static constraints = {
        code(nullable:false, unique:true)
        description(nullable:false)
    }
}

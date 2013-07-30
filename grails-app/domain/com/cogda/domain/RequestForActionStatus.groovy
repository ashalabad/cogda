package com.cogda.domain

/**
 * RequestForActionStatus
 * A domain class describes the data object and it's mapping to the database
 */
class RequestForActionStatus {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    String code
    String description

    static constraints = {
        code nullable:false, maxSize: 30, unique:true
        description nullable:true, maxSize: 255
    }

    String toString() {
        code
    }
}

package com.cogda.multitenant

/**
 * AccountAddressType
 * A domain class describes the data object and it's mapping to the database
 */
class AccountAddressType {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    String code

    String description

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static constraints = {
        code(nullable:false, maxSize: 50, unique:true)
        description(nullable:true, maxSize: 255)
    }

    String toString() {
        code
    }

}

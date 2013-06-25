package com.cogda.domain.admin

import groovy.transform.ToString

/**
 * BusinessType
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class BusinessType {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * used for ordering the results
     */
    Integer intCode

    /**
     * Sic Code
     */
    Integer codeFrom

    /**
     * Sic Code
     */
    Integer codeTo

    /**
     * The code
     */
    String code

    /**
     * The description
     */
    String description


    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        intCode(nullable:false)
        code(nullable:false, unique: true)
        description(nullable:true, maxSize: 255)
        codeFrom(nullable:true)
        codeTo(nullable:true)
    }

}

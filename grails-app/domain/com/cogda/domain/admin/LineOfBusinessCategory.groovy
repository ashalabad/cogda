package com.cogda.domain.admin

import groovy.transform.ToString

/**
 * LineOfBusinessCategory
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class LineOfBusinessCategory {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * Used primarily to order the LineOfBusinessCategory
     */
    Integer intCode

    /**
     * Describes the Line of Business Category
     * e.g. Commercial Lines, Health, Non P&C, Personal Lines
     */
    String code

    /**
     * An optional longer description of the code
     */
    String description

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static mapping = {
    }

    static constraints = {
        intCode(nullable:false)
        code(nullable:false, maxSize: 50, unique:true)
        description(nullable:true, maxSize: 255)
    }
}

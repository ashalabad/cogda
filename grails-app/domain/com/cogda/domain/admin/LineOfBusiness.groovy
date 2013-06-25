package com.cogda.domain.admin

import groovy.transform.ToString

/**
 * LineOfBusiness
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class LineOfBusiness {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * Used primarily to order the LineOfBusiness
     */
    Integer intCode

    /**
     * Describes the LineOfBusiness - used as the value in the
     * in the Select input boxes
     */
    String code

    /**
     * A description of the LineOfBusiness -
     */
    String description

    /**
     * The category that this Line of Business belongs to.
     */
    LineOfBusinessCategory lineOfBusinessCategory

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        intCode(nullable:false)
        code(nullable:false, maxSize: 50, unique:true)
        description(nullable:true, maxSize: 255)
        lineOfBusinessCategory(nullable:true)
    }

}

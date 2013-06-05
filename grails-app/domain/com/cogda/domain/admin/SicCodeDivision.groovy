package com.cogda.domain.admin

/**
 * SicCodeDivision
 * A domain class describes the data object and it's mapping to the database
 */
class SicCodeDivision {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    String divisionCode
    String divisionDescription

    static hasMany = [sicCodes:SicCode]

    static mapping = {
    }

    static constraints = {
        divisionCode(nullable:false)
        divisionDescription(nullable:false)
    }

    /*
     * Methods of the Domain Class
     */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}

package com.cogda.domain.admin

/**
 * CompanyType
 * A domain class describes the data object and it's mapping to the database
 */
class CompanyType {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Integer intCode

    String code

    String description

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        intCode(nullable:false, size:0..30)
        code(nullable:false, maxSize: 50, unique:true)
        description(nullable:true, maxSize: 255)
    }

    /**
     * Returns the current intCodes available in the database.
     * @return List
     */
    static List retrieveIntCodes(){
        return CompanyType.list().collect { it.intCode }
    }

    /**
     * Retrieves the ids from the CompanyType in a List
     * @return List
     */
    static List retrieveIds(){
        return CompanyType.list().collect { it.id }
    }

    /*
     * Methods of the Domain Class
     */
	@Override	// Override toString for a nicer / more descriptive UI
	public String toString() {
		return "${code}";
	}
}

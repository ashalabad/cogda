package com.cogda.domain.admin

/**
 * SicCode
 * A domain class describes the data object and it's mapping to the database
 */
class SicCode {

    /* Default (injected) attributes of GORM */
	Long id
	Long version

    Long code
    String description

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        code(nullable:false, unique:true)
        description(nullable:false)
    }

    /*
     * Methods of the Domain Class
     */
	@Override	// Override toString for a nicer / more descriptive UI
	public String toString() {
		return "${code} - ${description}";
	}
}

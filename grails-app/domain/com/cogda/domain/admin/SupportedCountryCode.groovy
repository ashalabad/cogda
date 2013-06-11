package com.cogda.domain.admin

/**
 * SupportedCountryCode
 * The Country Codes that are currently supported.
 * These are the country codes of the countries that COGDA serves.
 */
class SupportedCountryCode {

	/* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /**
     * A valid ISO3166_3 Country Code
     */
    String countryCode

    /**
     *
     */
    String countryDescription

	/* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	
    static mapping = {
    }
    
	static constraints = {
        countryCode(unique:true)
        countryDescription(nullable:true)
    }
	
	/*
	 * Methods of the Domain Class
	 */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}

    /**
     * Returns a List of String countryCode
     * @return List
     */
    static List retrieveSupportedCountryCodes(){
        return SupportedCountryCode.list().collect { it.countryCode }
    }
}

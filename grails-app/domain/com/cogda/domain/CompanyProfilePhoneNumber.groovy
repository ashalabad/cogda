package com.cogda.domain

/**
 * CompanyProfilePhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
class CompanyProfilePhoneNumber {
    CompanyProfile companyProfile

    boolean published
    boolean primaryPhoneNumber

    String countryCode = "usa" // ISO3166_3
    String label
    String phoneNumber

	static belongsTo	= [companyProfile:CompanyProfile]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static constraints = {
        countryCode(nullable:true)  // helps to determine the applicable constraints to apply to the phone number.
        label(nullable:true)
        published(nullable:true)
        primaryPhoneNumber(nullabel:true)
        phoneNumber(nullable:false)
    }
}

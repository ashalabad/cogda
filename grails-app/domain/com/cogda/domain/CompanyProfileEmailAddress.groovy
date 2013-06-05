package com.cogda.domain

/**
 * CompanyProfileEmailAddress
 * A domain class describes the data object and it's mapping to the database
 */
class CompanyProfileEmailAddress {

    CompanyProfile companyProfile

    Boolean published

    Boolean primaryEmailAddress

	static belongsTo	= [companyProfile:CompanyProfile]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static mapping = {

    }

    static constraints = {
        published(nullable:true)
        primaryEmailAddress(nullable:true)
    }
}

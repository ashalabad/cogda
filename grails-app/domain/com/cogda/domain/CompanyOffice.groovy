package com.cogda.domain

import com.cogda.multitenant.Company

/**
 * CompanyOffice
 * A domain class describes the data object and it's mapping to the database
 */
class CompanyOffice {

    Company company

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Address officeAddress

    String officeName

    boolean mainOffice

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo	= [company:Company]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
	static hasMany		= [phoneNumbers:PhoneNumber]	// tells GORM to associate other domain objects for a 1-n or n-m mapping

    static constraints = {
        officeAddress(nullable:true)
        officeName(nullable:true)
        mainOffice(nullable:true)
    }
}

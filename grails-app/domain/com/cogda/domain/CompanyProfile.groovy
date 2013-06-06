package com.cogda.domain

import com.cogda.multitenant.Company

/**
 * CompanyProfile
 * A domain class describes the data object and it's mapping to the database
 */
class CompanyProfile {

    Company company

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Boolean published

    String companyDescription

    String companyWebsite

    Date yearFounded

    String amBestNumber

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

	static belongsTo = [company:Company]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
    static hasMany = [companyProfileAddresses:CompanyProfileAddress,
                      companyProfileEmailAddresses:CompanyProfileEmailAddress,
                      companyProfilePhoneNumbers:CompanyProfilePhoneNumber]

    static mapping = {
        companyDescription type:'text'
    }

    static constraints = {
        companyDescription(nullable:true)
        companyWebsite(nullable:true, url:true)
        yearFounded(nullable:true)
        amBestNumber(nullable:true)
        published(nullable:true)
    }

    /**
     * Retrieves the CompanyProfileAddress associated with this CompanyProfile that has its
     * primaryAddress set to true.
     * @return CompanyProfileAddress
     */
    CompanyProfileAddress getPrimaryAddress(){
        List companyProfileAddresses = CompanyProfileAddress.executeQuery("from CompanyProfileAddress cpa where cpa.primaryAddress = :primaryAddress " +
                " and cpa.companyProfile = :companyProfile", [primaryAddress:true, companyProfile:this])
        return companyProfileAddresses?.first()
    }
}

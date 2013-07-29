package com.cogda.domain

import com.cogda.domain.admin.CompanyType
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

    String businessSpecialties

    String associations

    String companyWebsite

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static transients = ['rootCompanyProfile']
	static belongsTo = [company:Company]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
    static hasMany = [companyProfileAddresses:CompanyProfileAddress,
                      companyProfileEmailAddresses:CompanyProfileEmailAddress,
                      companyProfilePhoneNumbers:CompanyProfilePhoneNumber,
                      companyProfileContacts:CompanyProfileContact]

    static mapping = {
        companyDescription type:'text'
        businessSpecialties type:'text'
        associations type:'text'
    }

    static constraints = {
        companyWebsite(nullable:true, url:true)
        published(nullable:true)
        companyDescription(nullable:true, size:0..15000)
        businessSpecialties(nullable:true, size:0..15000)
        associations(nullable:true, size:0..15000)
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

    /**
     * Retrieves the Root Company's Company Profile
     * @return CompanyProfile
     */
    static CompanyProfile getRootCompanyProfile(){
        Company.retrieveRootCompany().companyProfile
    }
}

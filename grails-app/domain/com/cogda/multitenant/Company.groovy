package com.cogda.multitenant

import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.admin.CompanyType
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 *
 * @see http://multi-tenant.github.com/grails-multi-tenant-single-db/
 */
@MultiTenant
class Company {

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    /**
     * Company - the parent company to this company.
     */
    Company parentCompany

    CompanyType companyType

    String companyName

    String doingBusinessAs

    String federalIdNumber

    String amBestNumber

    /**
     * Indicates which level in the hierarchy this Company is.
     * <ul>
     *     <li>0 = Root (No Parent Company)
     *     <li>1 = This is a child of the Root Company
     * </ul>
     */
    Integer intCode

    /**
     * The public profile for the Company
     */
    CompanyProfile companyProfile

    /**
     * The settings for this Company
     */
    CompanySettings companySettings

    String accountId = UUID.randomUUID().toString().replaceAll('-', '')

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static transients = ['companyProfile']
    static hasMany		= [companies:Company]	// tells GORM to associate other domain objects for a 1-n or n-m mapping
    static mappedBy     = [companies:'parentCompany']

    static constraints = {
        parentCompany(nullable:true)
        companyName(nullable:false)  // TODO: Add a validator that checks for the company name if this has a ParentCompany - the name should be unique within the company
        doingBusinessAs(nullable:true)
        intCode(nullable:true, size:0..100)
        accountId(nullable:false, blank:false, unique:true)
        federalIdNumber(nullable:true)
        amBestNumber(nullable:true)
        companySettings(nullable:true)
        companyType(nullable:false)
    }

    /*
     * Methods of the Domain Class
     */
    @Override
    public String toString() {
        return "${companyName}";
    }

    /**
     * Finds the CompanyProfile associated with this
     * Company.
     * @return CompanyProfile
     */
    public CompanyProfile getCompanyProfile(){
        return CompanyProfile.findByCompany(this)
    }

    /**
     * Returns a pretty version of the Company Name for display
     * purposes.
     * @return String
     */
    public String prettyCompanyString(){
        String retString = this.companyName.trim()

        CompanyProfileAddress companyProfileAddress = this?.companyProfile?.getPrimaryAddress()
        if(companyProfileAddress){
            String location = companyProfileAddress.getCityStateCountryString()
            if(location){
                retString += "(" + companyProfileAddress.getCityStateCountryString() + ")"
            }
        }
        return retString
    }

    /**
     * Gets the top-level company from the Company hierarchy.
     */
    static Company retrieveRootCompany(){

        return Company.findWhere(parentCompany:null)

    }
}

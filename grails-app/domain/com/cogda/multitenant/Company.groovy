package com.cogda.multitenant

import com.cogda.domain.CompanyProfile
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

    String name

    String companyURLExtension

    CompanyProfile companyProfile

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static hasMany		= [companies:Company]	// tells GORM to associate other domain objects for a 1-n or n-m mapping

    static constraints = {
        parentCompany(nullable:true)
        name(nullable:false)  // TODO: Add a validator that checks for the company name if this has a ParentCompany - the name should be unique within the company
        companyURLExtension(unique:true)
        companyProfile(nullable:true)
    }

    /*
     * Methods of the Domain Class
     */
    @Override
    public String toString() {
        return "${name}";
    }
}

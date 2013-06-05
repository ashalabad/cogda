package com.cogda.multitenant
import grails.plugin.multitenant.core.Tenant
/**
 * CustomerAccount
 * A domain class describes the data object and it's mapping to the database
 */
class CustomerAccount implements Tenant {

    String subDomain

    static constraints = {
        subDomain(nullable:false, blank:false, unique:true)
    }

    Integer tenantId() {
        id
    }
	
}

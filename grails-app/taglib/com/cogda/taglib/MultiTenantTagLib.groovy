package com.cogda.taglib

import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import grails.plugin.multitenant.core.CurrentTenant

/**
 * MultiTenantTagLib
 * A taglib library provides a set of reusable tags to help rendering the views.
 */
class MultiTenantTagLib {
    static namespace = "mt"

    CurrentTenant currentTenant

    /**
     * Renders the organizationName if the tenant
     * is present.
     */
    def subDomainName = { attrs ->
        out <<  getReadOnlyTenant().subDomain.encodeAsHTML()
    }

    def hasTenant = { attrs, body ->
        if (hasCurrentTenant()){
            out << body()
        }
    }

    protected CustomerAccount getReadOnlyTenant() {
        if (currentTenant) {
            if(currentTenant.get()){
                def tenant = CustomerAccount.read(currentTenant.get().toLong())
                if (tenant){
                    return tenant
                }
            }
        }
        return null
    }

    def rootCompanyName = { attrs ->
        if (hasCurrentTenant()){
            String rootCompanyName = Company.retrieveRootCompany()?.companyName
            if(rootCompanyName){
                out << rootCompanyName
            } else {
                log.debug "rootCompanyName was not found for this tenant: ${currentTenant.get()}"
            }
        }
    }

    /**
     * Checks that there is an active currentTenant
     * @return
     */
    private Boolean hasCurrentTenant(){
        return currentTenant && currentTenant.get() && CustomerAccount.exists(currentTenant.get())
    }
}

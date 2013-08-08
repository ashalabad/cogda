package com.cogda.taglib

import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.multitenant.CustomerAccountService
import grails.plugin.multitenant.core.CurrentTenant

/**
 * MultiTenantTagLib
 * A taglib library provides a set of reusable tags to help rendering the views.
 */
class MultiTenantTagLib {
    static namespace = "mt"

    CustomerAccountService customerAccountService

    CurrentTenant currentTenant

    /**
     * Renders the organizationName if the tenant
     * is present.
     */
    def subDomainName = { attrs ->
        if(hasCurrentTenant()){
            out <<  getReadOnlyTenant()?.subDomain.encodeAsHTML()
        }
    }

    def hasTenant = { attrs, body ->
        if (hasCurrentTenant()){
            out << body()
        }
    }

    /**
     * This method will return the body of this tag only if a
     * CurrentTenant is active and the current tenant maps to a
     * CustomerAccount that has the internalCustomerAccount set to
     * true.
     */
    def isAnInternalCustomerAccount = { attrs, body ->
        if(hasCurrentTenant()){
            if(customerAccountService.isInternalCustomerAccount(getReadOnlyTenant())){
                out << body()
            }
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

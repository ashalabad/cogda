package com.cogda.multitenant

import grails.plugin.multitenant.core.resolve.TenantResolver
import grails.plugin.multitenant.core.exception.TenantResolveException
import grails.util.Environment

import javax.servlet.http.HttpServletRequest
import com.cogda.common.web.ServletRequestUtils

/**
 *
 * @see http://multi-tenant.github.com/grails-multi-tenant-single-db/
 */
class DomainTenantResolver implements TenantResolver {

    Integer resolve(HttpServletRequest request) throws TenantResolveException {
        String contextPath = request.contextPath

        switch(Environment.current){
            case [Environment.DEVELOPMENT, Environment.TEST]:
                return developmentResolver(request)
            case Environment.PRODUCTION:
                return productionResolver(request)
            default:
                return null
        }
    }

    private Integer developmentResolver(HttpServletRequest request){
        if(request.serverName.count(".") != 2) {
            request.customerAccount = -1
            request.domain = ""
            return null
        }
        else
        {
            def subDomain = request.serverName.substring(0, request.serverName.indexOf("."))
            CustomerAccount ca = CustomerAccount.findBySubDomain(subDomain)
            request.customerAccount = ca.id
            request.domain = subDomain
            return ca.id  // the customer account id is the tenant id.
        }
    }

    /**
     * Temporarily - we are hosting this system at CloudBees - the URL is the following:
     * cogda.cogda.cloudbees.net
     * @param request
     * @return
     */
    private Integer productionResolver(HttpServletRequest request){
        if(request.serverName.count(".") <= 3) {
            request.customerAccount = -1
            request.domain = ""
            return null
        }
        else
        {
            def subDomain = request.serverName.substring(0, request.serverName.indexOf("."))
            CustomerAccount ca = CustomerAccount.findBySubDomain(subDomain)
            request.customerAccount = ca.id
            request.domain = subDomain
            return ca.id  // the customer account id is the tenant id.
        }
    }
}

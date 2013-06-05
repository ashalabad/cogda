package com.cogda.multitenant

import grails.plugin.multitenant.core.resolve.TenantResolver
import grails.plugin.multitenant.core.exception.TenantResolveException
import javax.servlet.http.HttpServletRequest
import com.cogda.common.web.ServletRequestUtils

/**
 *
 * @see http://multi-tenant.github.com/grails-multi-tenant-single-db/
 */
class DomainTenantResolver implements TenantResolver {

    Integer resolve(HttpServletRequest request) throws TenantResolveException {
        String contextPath = request.contextPath
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
}

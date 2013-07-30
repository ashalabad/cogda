package com.cogda.multitenant

import grails.plugin.multitenant.core.resolve.TenantResolver
import grails.plugin.multitenant.core.exception.TenantResolveException
import grails.util.Environment
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes

import javax.servlet.http.HttpServletRequest
import com.cogda.common.web.ServletRequestUtils

/**
 * @see http://multi-tenant.github.com/grails-multi-tenant-single-db/
 */
class DomainTenantResolver implements TenantResolver {
    private static final log = LogFactory.getLog(this)

    GrailsApplication grailsApplication

    /**
     *
     * @param request
     * @return Integer
     * @throws TenantResolveException
     */
    Integer resolve(HttpServletRequest request) throws TenantResolveException {
        log.debug "Running in Environment: ${Environment.current.name}"
        return resolver(request)
    }

    private void noCustomerAccount(request){
        request.customerAccount = -1
        request.domain = ""
    }

    /**
     * Resolver determines if the User is issuing a request for a valid
     * CustomerAccount via the subDomain of the URL.  If so, it will return
     * the CustomerAccount.id that corresponds to the subDomain and it will
     * set the "domain" and "customerAccount" parameters in the request object.
     * @param request
     * @return Integer
     */
    private Integer resolver(HttpServletRequest request){
        String configDomainUrl = grailsApplication.config.grails.domainURL

        log.debug("Current configuration domainURL: " + configDomainUrl)
        log.debug("Requested serverName: " + request.serverName)
        if(request.serverName.equalsIgnoreCase(configDomainUrl)){
            noCustomerAccount(request)
            return null
        }

        if(request.serverName.count(".") <= configDomainUrl.count(".")){
            noCustomerAccount(request)
            return null
        } else {
            def subDomain = request.serverName.substring(0, request.serverName.indexOf("."))

            subDomain = subDomain.toLowerCase()

            CustomerAccount ca = CustomerAccount.findBySubDomain(subDomain)

            if(!ca){
                noCustomerAccount(request)
                return null
            }

            log.debug("Returning [${ca.id}] for subDomain [${subDomain}]")

            request.customerAccount = ca.id
            request.domain = subDomain
            return ca.id  // the customer account id is the tenant id.
        }
    }
}

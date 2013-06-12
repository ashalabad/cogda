package com.cogda

import com.cogda.multitenant.CustomerAccount
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 * BaseController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class BaseController {
    GrailsApplication grailsApplication
    /**
     * Generates a Redirect Link that is not based on the
     * @param controller
     * @param action
     * @return
     */
    protected String generateRedirectLink(String controller, String action, linkedParams = null){
        if(request.customerAccount != -1){
            String domainURL = retrieveSubDomainUrl()
            if(linkedParams){
                return createLink(base: "$request.scheme://$domainURL",
                        controller: controller, action: action, params:linkedParams)
            }else{
                return createLink(base: "$request.scheme://$domainURL",
                        controller: controller, action: action)
            }
        }else{
            return createLink(controller:controller, action:action, params:linkedParams)
        }
    }

    private String retrieveSubDomainUrl(){
        String domainURL = grailsApplication.config.grails.domainURL
        CustomerAccount customerAccount = CustomerAccount.get(request.customerAccount)
        domainURL = customerAccount.subDomain + "." + domainURL
        log.debug "Created the following subDomainUrl: " + domainURL
        return domainURL
    }

    /**
     * Generates the logout link that is caught by the Spring Security Logout Filter
     * @return String
     */
    protected String generateLogoutLink(){
        def logoutUrl = SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
        if(request.customerAccount != -1){
            String domainURL = retrieveSubDomainUrl()
            String createdLink = "$request.scheme://$domainURL" + logoutUrl
            createdLink += "?spring-security-redirect=$request.scheme://$domainURL"
            log.debug "Logout Link for customer account ${createdLink}"
            return createdLink
        }else{
            return createLink(uri:logoutUrl)
        }
    }

    /**
     * Generates a redirect link to the base of the customer's subdomain or to the
     * base of the application.
     * @return
     */
    protected String generateRedirectLink(){
        if(request.customerAccount != -1){
            String domainURL = retrieveSubDomainUrl()
            String createdLink = "$request.scheme://$domainURL/"
            return createdLink
        }else{
            return createLink(uri:"/")
        }
    }
}

package cogda

import com.cogda.multitenant.CustomerAccount
import org.apache.commons.logging.LogFactory

/**
 * DomainFilters
 * A filters class is used to execute code before and after a controller action is executed and also after a view is rendered
 */
class DomainFilters {
    private static final log = LogFactory.getLog(this)

    def filters = {

        tenantRegisterFilter(controller:'register', action:'*'){
            before = {

                String domainURL = grailsApplication.config.grails.domainURL

                log.debug("grailsApplication.config.grails.domainURL = " + domainURL)

                if(request.customerAccount != -1){ // CustomerAccount is found.
                    domainURL = "${request.scheme}://" + request.domain + "." + domainURL

                    log.debug("customerAccount found redirecting to url -> " + domainURL)

                    redirect(url:domainURL)

                    return false
                }else{
                    def subDomain = request.serverName.substring(0, request.serverName.indexOf("."))

                    log.debug("customerAccount was not found checking subDomain -> " + subDomain)

                    if(!domainURL.contains(subDomain)){
                        // There is no customerAccount found for the subDomain specified.
                        // The user specified a subDomain
                        // that does not exist in the CustomerAccount.subDomain
                        // -> redirect them to the register/index page
                        domainURL = "${request.scheme}://" + domainURL

                        log.debug("domainURL does not contain subDomain -> " + subDomain)
                        log.debug("redirecting to domainURL " + domainURL)
                        redirect(url:domainURL)
                        return false
                    }
                }
            }
        }
        registerFilter(controller:'register', action:'index', invert: true, controllerExclude:'company') {
            before = {

                // Exclusions made to this filter for the purposes of registration:
                if(request.customerAccount == -1) {
                    if(controllerName.equals("emailVerification")){
                        log.debug("Allowing controllerName emailVerification with actionName *")
                        return
                    }

                    if(controllerName.equals("company") && actionName.equals("typeahead")){
                        log.debug("Allowing controllerName company with actionName typeahead")
                        return
                    }

                    if(controllerName.equals("register") && actionName.equals("availableUsername")){
                        log.debug("Allowing controllerName register with actionName availableUsername")
                        return
                    }

                    if(controllerName.equals("register") && actionName.equals("save")){
                        log.debug("Allowing controllerName register with actionName save")
                        return
                    }

                    if (controllerName.startsWith("admin")){
                        log.debug("Allowing controllerName admin with actionName *")
                        return
                    }

                    log.debug("Redirecting the user from controllerName: [${controllerName}] actionName: [${actionName}] to /register/index ")
                    redirect(controller:"register",action:"index")
                }
            }
        }

    }
}

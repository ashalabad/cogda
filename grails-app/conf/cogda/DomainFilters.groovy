package cogda

import com.cogda.multitenant.CustomerAccount

/**
 * DomainFilters
 * A filters class is used to execute code before and after a controller action is executed and also after a view is rendered
 */
class DomainFilters {


    def filters = {

        tenantRegisterFilter(controller:'register', action:'*'){
            before = {
                String domainURL = grailsApplication.config.grails.domainURL
                if(request.customerAccount != -1){ // CustomerAccount is found.
                    domainURL = "${request.scheme}://" + request.domain + "." + domainURL
                    redirect(url:domainURL)
                    return false
                }else{
                    def subDomain = request.serverName.substring(0, request.serverName.indexOf("."))


                    if(!domainURL.contains(subDomain)){
                        // There is no customerAccount found for the subDomain specified.
                        // The user specified a subDomain
                        // that does not exist in the CustomerAccount.subDomain
                        // -> redirect them to the register/index page
                        domainURL = "${request.scheme}://" + domainURL
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
                        return
                    }

                    if(controllerName.equals("company") && actionName.equals("typeahead")){
                        return
                    }

                    if(controllerName.equals("register") && actionName.equals("availableUsername")){
                        return
                    }

                    if(controllerName.equals("register") && actionName.equals("save")){
                        return
                    }

                    if (controllerName.startsWith("admin"))
                        return

                    redirect(controller:"register",action:"index")
                }
            }
        }

    }
}

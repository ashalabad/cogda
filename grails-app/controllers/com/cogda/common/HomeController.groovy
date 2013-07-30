package com.cogda.common

import com.cogda.multitenant.Company

/**
 * HomeController
 *
 * Overriding the Kickstart plugin's HomeController
 */
class HomeController {

    def index = {
        def companyInstance = Company.retrieveRootCompany()
        log.debug ("Executing index action - rendering index view")
        render(controller:"home", view:"index", model:[companyInstance:companyInstance])
    }

}

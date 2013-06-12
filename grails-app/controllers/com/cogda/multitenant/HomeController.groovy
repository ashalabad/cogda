package com.cogda.multitenant

/**
 * HomeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class HomeController {


    def index = {
        def companyInstance = Company.retrieveRootCompany()
        def customerAccountInstance = CustomerAccount.get(request?.customerAccount)
        render(view:"index", model:[companyInstance:companyInstance, customerAccountInstance:customerAccountInstance])
    }

}

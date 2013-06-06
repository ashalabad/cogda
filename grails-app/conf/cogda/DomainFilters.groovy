package cogda

/**
 * DomainFilters
 * A filters class is used to execute code before and after a controller action is executed and also after a view is rendered
 */
class DomainFilters {


    def filters = {

        registerFilter(controller:'register', action:'index', invert: true, controllerExclude:'company') {
            before = {

                // Exclusions made to this filter for the purposes of registration:
                if(controllerName.equals("company") && actionName.equals("typeahead")){
                    return
                }


                if(request.customerAccount == -1) {
                    redirect(controller:"register",action:"index")
                }
            }
        }
    }
}

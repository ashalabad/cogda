package cogda

/**
 * DomainFilters
 * A filters class is used to execute code before and after a controller action is executed and also after a view is rendered
 */
class DomainFilters {

    def filters = {
        registerFilter(controller:'register', action:'index', invert: true) {
            before = {
                if(request.customerAccount == -1) {
                    redirect(controller:"register",action:"index")
                }
            }
        }
    }
}

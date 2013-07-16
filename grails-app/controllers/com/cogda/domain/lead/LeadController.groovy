package com.cogda.domain.lead

import com.cogda.GsonBaseController

/**
 * LeadController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LeadController extends GsonBaseController {
    def showPartial() {
        render(template:'/lead/partials/showPartial')
    }

    def editPartial() {
        render(template:'/lead/partials/editPartial')
    }
}

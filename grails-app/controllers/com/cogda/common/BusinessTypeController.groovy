package com.cogda.common

import com.cogda.GsonBaseController
import com.cogda.domain.admin.BusinessType
import grails.plugin.gson.converters.GSON

/**
 * BusinessTypeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class BusinessTypeController extends GsonBaseController {

    def list() {
        def businessTypes = BusinessType.list()
        render businessTypes as GSON
    }
}

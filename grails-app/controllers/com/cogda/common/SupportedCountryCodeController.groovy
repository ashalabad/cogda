package com.cogda.common

import com.cogda.GsonBaseController
import com.cogda.domain.admin.SupportedCountryCode
import grails.plugin.gson.converters.GSON

/**
 * SupportedCountryCodeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class SupportedCountryCodeController extends GsonBaseController {

    def list() {
        def countryCodes = SupportedCountryCode.list()
        render countryCodes as GSON
    }
}

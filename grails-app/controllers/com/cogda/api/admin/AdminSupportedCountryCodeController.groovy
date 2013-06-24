package com.cogda.api.admin

import com.cogda.domain.admin.SupportedCountryCode
import grails.converters.XML
import grails.plugin.gson.converters.GSON

/**
 * AdminSupportedCountryCodeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AdminSupportedCountryCodeController {

    def exists(String countryCode) {
        render SupportedCountryCode.list().any {
            it.countryCode == countryCode
        }
    }

    def list() {
        def supportedCountryCodes = SupportedCountryCode.list(params)
        withFormat {
            json { render supportedCountryCodes as GSON }
            xml { render supportedCountryCodes as XML }
        }
    }
}

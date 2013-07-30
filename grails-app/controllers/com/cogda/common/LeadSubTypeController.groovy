package com.cogda.common

import grails.plugin.gson.converters.GSON

/**
 * LeadSubTypeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LeadSubTypeController {
    def list() {
        def leadSubTypes = LeadSubType.values()
        def renderThis = leadSubTypes.collect {
            def result = [:]
            result.label = it.label
            result.value = it.value
            return result
        }
        render renderThis as GSON
    }
}

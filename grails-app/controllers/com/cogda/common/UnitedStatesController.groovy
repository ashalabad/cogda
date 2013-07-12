package com.cogda.common

import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON

/**
 * UnitedStatesController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UnitedStatesController extends GsonBaseController{

    def list() {
        countrySelect()
        def usStates = UsState.values()
        render UsState as GSON
        return
    }
}

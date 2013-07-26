package com.cogda.domain.admin

import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON
import static grails.plugin.gson.http.HttpConstants.*

/**
 * LineOfBusinessController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LineOfBusinessController extends GsonBaseController {

    def list() {
        def lineOfBusinessInstanceList = LineOfBusiness.list()
        response.addIntHeader X_PAGINATION_TOTAL, LineOfBusiness.count()
        render lineOfBusinessInstanceList as GSON
    }
}

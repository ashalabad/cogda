package com.cogda.domain.admin

import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON
import static grails.plugin.gson.http.HttpConstants.*

/**
 * LineOfBusinessCategoryController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class LineOfBusinessCategoryController extends GsonBaseController {

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        def lineOfBusinessCategoryInstanceList = LineOfBusinessCategory.list(params)
        response.addIntHeader X_PAGINATION_TOTAL, LineOfBusinessCategory.count()
        render lineOfBusinessCategoryInstanceList as GSON
    }
}

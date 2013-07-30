package com.cogda.common

import com.cogda.GsonBaseController
import com.cogda.domain.admin.NoteType
import grails.plugin.gson.converters.GSON

/**
 * UnitedStatesController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class NoteTypeController extends GsonBaseController {

    def list() {
        def noteTypes = NoteType.list()
        render noteTypes as GSON
    }
}

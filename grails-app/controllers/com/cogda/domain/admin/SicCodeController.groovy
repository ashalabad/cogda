package com.cogda.domain.admin

import com.cogda.BaseController
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured


/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SicCodeController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def getActiveSicCodes() {
        if (params.parentId) {
            render jsTreeify(SicCode.findAllByParentSicCodeAndActive(SicCode.get(params.parentId), true).sort { it.id }, 1) as GSON
        } else {
            render jsTreeify(SicCode.findAllByParentSicCodeAndActive(null, true).sort { it.id }, 0) as GSON
        }
    }

    def search() {
        if (params.search_string) {
            def codes = SicCode.findAllByDescriptionLikeOrCodeLike("%${params.search_string}%", "%${params.search_string}%")
            def parents = codes.collect { it.aggregateParentIds() }
            Set<SicCode> flattenedParents = new HashSet<SicCode>(parents.flatten())
            def formattedCodeIds = flattenedParents.collect { "\"#${it.id}\"" }
            render(text: formattedCodeIds, contentType: "text/json", encoding: "UTF-8")
        }
    }

    private def jsTreeify(def sicCodeList, int depth) {
        if (sicCodeList && depth >= 0) {
            def dataToRender = []

            sicCodeList.each { SicCode sicCode ->
                Map map = [:]
                map.data = sicCode.toString()
                map.attr = [:]
                map.attr.id = sicCode.id
                map.children = jsTreeify(sicCode.childSicCodes.sort { it.code }, depth - 1)
                dataToRender.add(map)
            }
            return dataToRender
        }
    }

    private def jsTreeify(def sicCodeList) {
        if (sicCodeList) {
            def dataToRender = []

            sicCodeList.each { SicCode sicCode ->
                Map map = [:]
                map.data = sicCode.toString()
                map.attr = [:]
                map.attr.id = sicCode.id
                map.children = jsTreeify(sicCode.childSicCodes.sort { it.code })
                dataToRender.add(map)
            }
            return dataToRender
        }
    }

    def selectedSicCodes() {
        JsonElement jsonElement = GSON.parse(request)
        jsonElement.checked.each {
            def sicCode = SicCode.findById(it.getAsLong())
            println sicCode.toString()
            if (SicNaicsCodeCrosswalk.countBySicCode(sicCode) != 0) {
                println "\t-----Related NAICS Codes-----"
                SicNaicsCodeCrosswalk.findAllBySicCode(sicCode).each { crosswalk ->
                    println "\t ${crosswalk.naicsCode.toString()}"
                }
            }
        }
        render "TODO"
    }
}

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
class NaicsCodeController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def getActiveNaicsCodes() {
        if (params.parentId) {
            render jsTreeify(NaicsCode.findAllByParentNaicsCodeAndActive(NaicsCode.get(params.parentId), true).sort { it.id }, 1) as GSON
        } else {
            render jsTreeify(NaicsCode.findAllByParentNaicsCodeAndActive(null, true).sort { it.id }, 0) as GSON
        }
    }

    def search() {
        if (params.search_string) {
            def codes = NaicsCode.findAllByDescriptionLikeOrCodeLike("%${params.search_string}%", "%${params.search_string}%")
            def parents = codes.collect { it.aggregateParentIds() }
            Set<NaicsCode> flattenedParents = new HashSet<NaicsCode>(parents.flatten())
            def formattedCodeIds = flattenedParents.collect { "\"#${it.id}\"" }
            render(text: formattedCodeIds, contentType: "text/json", encoding: "UTF-8")
        }
    }

    private def jsTreeify(def naicsCodeList, int depth) {
        if (naicsCodeList && depth >= 0) {
            def dataToRender = []

            naicsCodeList.each { NaicsCode naicsCode ->
                Map map = [:]
                map.data = naicsCode.toString()
                map.attr = [:]
                map.attr.id = naicsCode.id
                map.children = jsTreeify(naicsCode.childNaicsCodes.sort { it.code }, depth - 1)
                dataToRender.add(map)
            }
            return dataToRender
        }
    }

    private def jsTreeify(def naicsCodeList) {
        if (naicsCodeList) {
            def dataToRender = []

            naicsCodeList.each { NaicsCode naicsCode ->
                Map map = [:]
                map.data = naicsCode.toString()
                map.attr = [:]
                map.attr.id = naicsCode.id
                map.children = jsTreeify(naicsCode.childNaicsCodes.sort { it.code })
                dataToRender.add(map)
            }
            return dataToRender
        }
    }

    def selectedNaicsCodes() {
        JsonElement jsonElement = GSON.parse(request)
        jsonElement.checked.each {
            def naicsCode = NaicsCode.findById(it.getAsLong())
            println naicsCode.toString()
            if (SicNaicsCodeCrosswalk.countByNaicsCode(naicsCode) != 0) {
                println "\t-----Related SIC Codes-----"
                SicNaicsCodeCrosswalk.findAllByNaicsCode(naicsCode).each { crosswalk ->
                    println "\t ${crosswalk.sicCode.toString()}"
                }
            }

        }
        render "TODO"
    }
}

package com.cogda.domain.admin

import com.cogda.BaseController
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import static javax.servlet.http.HttpServletResponse.*


/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SicCodeController extends BaseController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def activeSicCodes() {
        if (params.parentId) {
            def codes = SicCode.findAllByParentSicCodeAndActive(SicCode.get(params.parentId), true).sort { it.id }
            if (!codes) {
                respondNotFound(params.parentId)
                return
            } else {
                render jsTreeify(codes, 0) as GSON
                return
            }
        } else {
            render jsTreeify(SicCode.findAllByParentSicCodeAndActive(null, true).sort { it.id }, 0) as GSON
            return
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
                map.hasChildSicCodes = sicCode.childSicCodes.any()
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
        def naicsCodes = []

        jsonElement.each {
            def sicCode = SicCode.findById(it.id.toString().toLong())
            SicNaicsCodeCrosswalk.findAllBySicCode(sicCode).each {
                naicsCodes.add(it.naicsCode.id)
            }
            sicCode.getAllChildSicCodes().each { SicCode childSicCode ->
                SicNaicsCodeCrosswalk.findAllBySicCode(childSicCode).each { subIt ->
                    naicsCodes.add(subIt.naicsCode.id)
                }
            }
        }

        render naicsCodes.unique() as GSON
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'sicCode.label', default: 'SicCode'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }
}

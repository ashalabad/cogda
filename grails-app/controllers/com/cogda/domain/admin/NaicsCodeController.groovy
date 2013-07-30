package com.cogda.domain.admin

import com.cogda.BaseController
import com.cogda.multitenant.Lead
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured

import static javax.servlet.http.HttpServletResponse.*

/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class NaicsCodeController extends BaseController {

    GsonBuilder gsonBuilder

    def activeNaicsCodes() {
        if (params.parentId) {
            def codes = NaicsCode.findAllByParentNaicsCodeAndActive(NaicsCode.get(params.parentId), true).sort { it.id }
            if (!codes) {
                respondNotFound(params.parentId)
                return
            } else {
                render jsTreeify(codes, 0) as GSON
                return
            }

        } else {
            render jsTreeify(NaicsCode.findAllByParentNaicsCodeAndActive(null, true).sort { it.id }, 0) as GSON
        }
    }

    def activeNaicsCodesByBusinessType() {
        if (params.businessTypeId) {
            def businessTypeInstance = BusinessType.get(params.businessTypeId)
            if (businessTypeInstance) {
                // include 0 to treeify if lazy loading
                render jsTreeify(NaicsCode.findByCode(businessTypeInstance.naicsCode)) as GSON
            }
        }
    }

    def activeNaicsCodesForLead() {
        if (params.id) {
            def leadInstance = Lead.get(params.id)
            def sortedCodes = leadInstance.naicsCodes.groupBy { it.level }
            sortedCodes
        } else {
            render[] as GSON
        }
    }

    def getAll() {
        def all = NaicsCode.findAllByIdInList(params.ids)
        render all as GSON
    }

    def search() {
        if (params.search_string) {
            def codes
            if (params.businessTypeId) {
                def businessTypeInstance = BusinessType.get(params.businessTypeId)
                if (businessTypeInstance) {
                    def naicsCodeInstance = NaicsCode.findByCode(businessTypeInstance.naicsCode)
                    codes = naicsCodeInstance.getAllChildNaicsCodes(params.search_string)
                }
            } else {
                codes = NaicsCode.findAllByDescriptionLikeOrCodeLike("%${params.search_string}%", "%${params.search_string}%")
            }
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
                map.hasChildNaicsCodes = naicsCode.childNaicsCodes.any()
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
        def sicCodes = []
//        jsonElement.each {
//            def naicsCode = NaicsCode.findById(it.id.toString().toLong())
//            sicCodes.addAll(SicNaicsCodeCrosswalk.findAllByNaicsCode(naicsCode).each{
//                it.sicCode = GrailsHibernateUtil.unwrapIfProxy(it.sicCode)
//                it.sicCode.parentSicCode = GrailsHibernateUtil.unwrapIfProxy(it.sicCode.parentSicCode)
//            })
//            naicsCode.getAllChildNaicsCodes().each { NaicsCode childNaicsCode ->
//                sicCodes.addAll(SicNaicsCodeCrosswalk.findAllByNaicsCode(childNaicsCode).each{subIt ->
//                    subIt.sicCode = GrailsHibernateUtil.unwrapIfProxy(subIt.sicCode)
//                    subIt.sicCode.parentSicCode = GrailsHibernateUtil.unwrapIfProxy(subIt.sicCode.parentSicCode)
//                })
//            }
//        }

        jsonElement.each {
            def naicsCode = NaicsCode.findById(it.id.toString().toLong())
            SicNaicsCodeCrosswalk.findAllByNaicsCode(naicsCode).each {
                sicCodes.add(it.sicCode.id)
            }
            naicsCode.getAllChildNaicsCodes().each { NaicsCode childNaicsCode ->
                SicNaicsCodeCrosswalk.findAllByNaicsCode(childNaicsCode).each { subIt ->
                    sicCodes.add(subIt.sicCode.id)
                }
            }
        }

        render sicCodes.unique() as GSON
    }

    def modal() {
        render(template: '/naicsCode/partials/naicsCodesPartial')
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'naicsCode.label', default: 'SicCode'), id])
        response.status = SC_NOT_FOUND // 404
        render responseBody as GSON
    }

}

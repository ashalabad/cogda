package com.cogda.domain.admin

import com.cogda.BaseController
import com.google.gson.JsonElement
import grails.plugin.gson.converters.GSON


/**
 * ContactController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class NaicsCodeController extends BaseController{

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def getActiveNaicsCodes() {
        render jsTreeify(NaicsCode.findAllByLevelAndActive(0,true)) as GSON
    }

    def jsTreeify(def naicsCodeList){
        if(naicsCodeList)
        {
            def dataToRender = []

            naicsCodeList.each { NaicsCode naicsCode ->
                Map map = [:]
                map.data = naicsCode.toString()
                map.attr = [:]
                map.attr.id = naicsCode.id
                map.children = jsTreeify(naicsCode.childNaicsCodes.sort{it.code})
                dataToRender.add(map)
            }
            return dataToRender
        }
    }

    def selectedNaicsCodes(){
        JsonElement jsonElement = GSON.parse(request)
        jsonElement.checked.each {
            def naicsCode = NaicsCode.findById(it.getAsLong())
            println naicsCode.toString()
            if(SicNaicsCodeCrosswalk.countByNaicsCode(naicsCode) != 0)
            {
                println "\t-----Related SIC Codes-----"
                SicNaicsCodeCrosswalk.findAllByNaicsCode(naicsCode).each { crosswalk ->
                    println "\t ${crosswalk.sicCode.toString()}"
                }
            }

        }
        render "TODO"
    }
}

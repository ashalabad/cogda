package com.cogda.common

import com.cogda.GsonBaseController
import grails.plugin.gson.converters.GSON
import static javax.servlet.http.HttpServletResponse.*
import static grails.plugin.gson.http.HttpConstants.*
/**
 * UnitedStatesController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UnitedStatesController extends GsonBaseController{

    final static String INSTANCE_LABEL = "default.state.label"

    def list() {
        Map usStates = UsState.getUsStatesMap()
        response.addIntHeader X_PAGINATION_TOTAL, usStates.size()
        render usStates as GSON
        return
    }

    def search(){
        Map usStates = [:]
        if(params.q){
            usStates = UsState.search(params.q)
        }else{
            usStates = UsState.getUsStatesMap()
        }
        response.addIntHeader X_PAGINATION_TOTAL, usStates.size()
        render usStates as GSON
        return
    }

    def get(){
        Map usStateMap = [:]
        // params.id is a state abbreviation
        if(params.id){
            UsState usState = UsState.getByValue(params.id)  // find by the state abbreviation
            if(usState){
                usStateMap[usState.value] = usState.description
            }
        }

        if (usStateMap) {
            response.status = SC_OK  // 200
            render usStateMap as GSON
            return
        } else {
            respondNotFound INSTANCE_LABEL
        }
        return
    }
}

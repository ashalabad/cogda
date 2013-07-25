package com.cogda.multitenant

import grails.plugins.springsecurity.Secured

/**
 * SubmissionBuilderController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SubmissionBuilderController {

    def index(){
    }

    def leadPartial(){
        render (view:'leadPartial')
    }

    def preparePartial(){
        render (view:'preparePartial')
    }


    def builderPartial(){
        render (view:'builderPartial')
    }


}

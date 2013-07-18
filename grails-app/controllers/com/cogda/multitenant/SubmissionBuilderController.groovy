package com.cogda.multitenant

import grails.plugins.springsecurity.Secured

/**
 * SubmissionBuilderController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SubmissionBuilderController {

    static defaultAction = "builder"

    def builder(){

    }

    def showBuilder(){
        render (view:'showBuilder')
    }
}

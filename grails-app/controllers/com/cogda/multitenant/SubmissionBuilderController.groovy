package com.cogda.multitenant

import com.cogda.GsonBaseController
import com.cogda.domain.Submission
import grails.converters.JSON
import grails.plugin.gson.converters.GSON
import grails.plugins.springsecurity.Secured
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * SubmissionBuilderController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
@Secured(['IS_AUTHENTICATED_FULLY'])
class SubmissionBuilderController extends GsonBaseController {

    SubmissionService submissionService
    def springSecurityService

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

    def listPartial(){
        render (view:'listPartial')
    }

    def get() {
        def submissionInstance = Submission.get(params.id)
        def parentSubmission = new Expando()

        Map lead = [:]
        lead.clientName = submissionInstance.lead.clientName
        lead.linesOfBusiness = []
        lead.docs = []
        def lineOfBusinessList = submissionInstance.lead.linesOfBusiness
        lineOfBusinessList.each { LeadLineOfBusiness leadLineOfBusiness ->
            Map lineOfBusiness = [:]
            lineOfBusiness.id = leadLineOfBusiness.id
            lineOfBusiness.expirationDate = leadLineOfBusiness.expirationDate
            lineOfBusiness.targetPremium = leadLineOfBusiness.targetPremium
            lineOfBusiness.targetDate = leadLineOfBusiness.targetDate
            lineOfBusiness.description = leadLineOfBusiness.lineOfBusiness.description
            lead.linesOfBusiness.add(lineOfBusiness)
        }

        parentSubmission.lead = lead
        render parentSubmission as JSON
    }

    def createParentSubmission(){
        def leadInstance = Lead.get(params?.id)
        if(leadInstance)
        {
            def submissionInstance = submissionService.createParentSubmission(leadInstance,springSecurityService.currentUser)
            if(submissionInstance)
            {
                response.status = SC_CREATED // 201
                response.addHeader LOCATION, createLink(action: 'get', id: submissionInstance.id)
                render submissionInstance as JSON
            }
        }
        else
            respondNotFound 'lead.label'
    }

    def createChildSubmission(){
        def submissionInstance = Submission.get(params?.id)
        if(submissionInstance)
        {
            def childSubmissionInstance = new Submission(parentSubmission: submissionInstance)
            if(childSubmissionInstance)
            {
                render childSubmissionInstance as JSON
            }
        }
        else
            respondNotFound 'submission.label'
    }

    def saveChildSubmission(){
        if (!requestIsJson()) {
            respondNotAcceptable()
            return
        }

        def jsonObject = request.GSON
        Long submissionId = jsonObject?.submission?.id?.getAsLong()

        def submissionInstance = Submission.get(submissionId)
        if(submissionInstance)
        {
            def savedSubmissionInstance = submissionService.saveChildSubmission(submissionInstance,jsonObject,springSecurityService.currentUser)
            if(savedSubmissionInstance){
                response.status = SC_OK // 200
                render savedSubmissionInstance as JSON
            }
        }
        else
            respondNotFound 'submission.label'
    }

}

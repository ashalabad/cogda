package com.cogda.multitenant

import com.cogda.domain.Submission

/**
 * SubmissionService
 * A service class encapsulates the core business logic of a Grails application
 */
class SubmissionService {

    static transactional = true

    Submission createParentSubmission(leadInstance,creator){
        Submission submission = new Submission()
            submission.lead = leadInstance
            submission.createdBy = creator
            submission.save(flush: true)
        submission
    }

    Submission saveChildSubmission(parentSubmission, jsonObject ,creator){
        println jsonObject
        println parentSubmission
        println creator
    }
}

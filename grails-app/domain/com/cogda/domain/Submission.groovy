package com.cogda.domain

import com.cogda.domain.security.User
import com.cogda.multitenant.Lead
import com.cogda.multitenant.LeadLineOfBusiness

/**
 * Submission
 * The Submission domain class.
 */
class Submission {

    String submissionId = UUID.randomUUID().toString().replaceAll('-', '')

    Lead lead

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    /**
     * The user that created the Submission
     */
    User createdBy

    /**
     * The parentSubmission
     */
    Submission parentSubmission

    static transients = ['child']

	static hasMany		= [requestForActions:RequestForAction,
                           childSubmissions:Submission,
                           leadLineOfBusinesses:LeadLineOfBusiness,
                           ]

    static constraints = {
        lead(nullable:true, validator: { Lead lead, Submission submission ->
            if(submission.parentSubmission == null){
                // validate that a Lead has been associated with the root Submission
                if(lead == null){
                    return ['submission.lead.required']
                }
            }
        })
        parentSubmission(nullable: true)
        createdBy(nullable:false)
        submissionId(nullable:false, unique:true)
        leadLineOfBusinesses(validator: { Set leadLineOfBusinesses, Submission submission ->
            if(submission.isChild() && !leadLineOfBusinesses){
                return ['submission.child.leadLineOfBusinesses.required']
            }
        })
    }

    /**
     * Indicates whether this Submission is a Child Submission
     * @return Boolean true if this is a Child Submission that has a parent
     */
    Boolean isChild(){
        return (parentSubmission != null)
    }

    /**
     * Finds the Lead associated with this Submission.
     * @return
     */
    Lead retrieveLead(){
        if(!isChild()){
            return lead
        }else{
            Submission sub = this
            while(sub.isChild()){
                sub = sub.parentSubmission
            }
            return sub.lead
        }
        return null  // TODO: Throw exception if no Lead is found on any of the Submissions.
    }
}

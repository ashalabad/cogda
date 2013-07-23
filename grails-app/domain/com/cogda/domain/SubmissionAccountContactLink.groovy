package com.cogda.domain

import com.cogda.multitenant.AccountContactLink
import groovy.transform.ToString

/**
 * SubmissionAccountContactLink
 * This domain class indicates where the Submission is being sent - it links the
 * Submission with its intended recipients.
 */
@ToString(includeNames=true, includeFields=true)
class SubmissionAccountContactLink {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    /**
     * The root Submission where submission.parentSubmission = null
     */
    Submission submission

    /**
     * AccountContactLink which will be sent this Submission
     */
    AccountContactLink accountContactLink

    static constraints = {
        submission(nullable:false)
        accountContactLink(nullable:false)
    }
}

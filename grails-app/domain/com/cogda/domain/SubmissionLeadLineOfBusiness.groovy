package com.cogda.domain

import com.cogda.multitenant.LeadLineOfBusiness

/**
 * SubmissionLeadLineOfBusiness
 * The Lines of Business and the associated meta data associated with the Lines of Business
 * that are selected and sent in the SubmissionBuilder.
 */
class SubmissionLeadLineOfBusiness {

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    /**
     * The associated Submission
     */
    Submission submission

    /**
     * The associated LeadLineOfBusiness from the
     * Lead that is associated with the ParentSubmission
     */
    LeadLineOfBusiness leadLineOfBusiness

    /**
     * Target Date to be shared with the person receiving this childSubmission
     */
    Date targetDate

    /**
     * Target Premium to be shared with the person receiving this childSubmission
     */
    BigDecimal targetPremium

    /**
     * Target Commission
     */
    BigDecimal targetCommission


    static constraints = {
        submission(nullable: false)
        leadLineOfBusiness(nullable: false)
        targetDate( nullable: true,
            validator: { Date targetDate, SubmissionLeadLineOfBusiness submissionLeadLineOfBusiness ->
                // verify that the targetDate is less than submissionLeadLineOfBusiness.leadLineOfBusiness.expirationDate
                if(targetDate >= submissionLeadLineOfBusiness.leadLineOfBusiness.expirationDate){
                    return ['submissionLeadLineOfBusiness.leadLineOfBusiness.targetDate.exceeds.expirationDate']
                }
            }
        )
        targetPremium(nullable: true)
        targetCommission(nullable: true)
    }

}

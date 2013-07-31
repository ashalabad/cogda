package com.cogda.domain

import com.cogda.multitenant.LeadLineOfBusiness

/**
 * SubmissionLeadLineOfBusiness
 * A domain class describes the data object and it's mapping to the database
 */
class SubmissionLeadLineOfBusiness {

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    Submission submission
    LeadLineOfBusiness leadLineOfBusiness

    Date targetDate

    BigDecimal targetPremium

    BigDecimal targetCommission

    static constraints = {
        submission nullable: false
        leadLineOfBusiness nullable: false
        targetDate nullable: true
        targetPremium nullable: true
        targetCommission nullable: true
    }

}

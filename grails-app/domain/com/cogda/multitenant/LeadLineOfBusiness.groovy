package com.cogda.multitenant

import com.cogda.domain.admin.LineOfBusiness

/**
 * LeadLineOfBusiness
 * A domain class describes the data object and it's mapping to the database
 */
class LeadLineOfBusiness {

    LineOfBusiness lineOfBusiness
    Date targetDate
    Date expirationDate
    BigDecimal targetPremium
    BigDecimal targetCommission
    Company writingCompany
    Company billingCompany
    Boolean renewal
    Boolean remarket

    static embedded = ['lineOfBusiness']

    static constraints = {
        targetDate(nullable: true)
        expirationDate(nullable: true)
        targetPremium(nullable: true)
        targetCommission(nullable: true)
        writingCompany(nullable: true)
        billingCompany(nullable: true)
        renewal(nullable: true)
        remarket(nullable: true)
    }
}

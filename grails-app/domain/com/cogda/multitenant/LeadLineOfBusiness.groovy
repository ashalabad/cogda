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
    String writingCompany
    String billingCompany
    Boolean renewal
    Boolean remarket

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

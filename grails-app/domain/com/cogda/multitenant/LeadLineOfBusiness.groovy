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
    BigDecimal commissionRate
    String currentCarrier
    Boolean remarket

    static belongsTo = [lead: Lead]

    static constraints = {
        targetDate(nullable: true)
        expirationDate(nullable: true)
        targetPremium(nullable: true)
        targetCommission(nullable: true)
        commissionRate(nullable: true)
        currentCarrier(nullable: true)
        remarket(nullable: true)
    }
}

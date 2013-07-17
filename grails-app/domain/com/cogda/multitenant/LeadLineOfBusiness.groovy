package com.cogda.multitenant

import com.cogda.domain.admin.LineOfBusiness

/**
 * LeadLineOfBusiness
 * A domain class describes the data object and it's mapping to the database
 */
class LeadLineOfBusiness {

    LineOfBusiness lineOfBusiness
    Date expirationDate
    BigDecimal targetPremium
    BigDecimal targetCommision
    Company writingCompany
    Company billingCompany
    Boolean renewal
    Boolean remarket

    static embedded = ['lineOfBusiness']
}

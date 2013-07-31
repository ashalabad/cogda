package com.cogda.multitenant

import com.cogda.domain.admin.LineOfBusiness

/**
 * LeadLineOfBusiness
 * A domain class describes the data object and it's mapping to the database
 */
class LeadLineOfBusiness {

    /**
     * inject LeadService
     */
    LeadService leadService

    LineOfBusiness lineOfBusiness
    Date targetDate
    Date expirationDate
    BigDecimal targetPremium
    BigDecimal targetCommission
    BigDecimal commissionRate
    String currentCarrier
    Boolean renewal
    Boolean remarket

    static belongsTo = [lead: Lead]

    static constraints = {
        targetDate(nullable: true, validator: { Date targetDate, LeadLineOfBusiness leadLineOfBusiness ->
            if(targetDate > leadLineOfBusiness.expirationDate){
                return ['leadLineOfBusiness.targetDate.max.exceeded']
            }
        })
        expirationDate(nullable: false, validator: { Date expirationDate, LeadLineOfBusiness leadLineOfBusiness ->
            Date today = new Date()
            Date todayPlus180 = today + 180
            if(expirationDate < (new Date())){
                return ['leadLineOfBusiness.expirationDate.min.exceeded']
            }
            // If there is another expiration date for the same line of business on this lead that is within 180 days time
            // frame - do not allow them to enter this LeadLineOfBusiness.
            def leadLinesOfBusinessWithMatchingLobWithinTimeConstraint =
                leadLineOfBusiness.leadService.findLeadLineOfBusinessesByLobAndExpirationDate(
                    leadLineOfBusiness.lead.id, leadLineOfBusiness.lineOfBusiness, todayPlus180)
            if(leadLinesOfBusinessWithMatchingLobWithinTimeConstraint){
                if(expirationDate < todayPlus180){
                    return ['leadLineOfBusiness.lineOfBusiness.expirationDate.min.exceeded']
                }
            }
        })
        targetPremium(nullable: true)
        targetCommission(nullable: true, validator: { BigDecimal targetCommission, LeadLineOfBusiness leadLineOfBusiness ->
            if(leadLineOfBusiness.targetPremium < targetCommission){
                return ['leadLineOfBusiness.targetCommission.exceeds.targetPremium']
            }
        })
        commissionRate(nullable: true)
        currentCarrier(nullable: true)
        remarket(nullable: true)
        renewal(nullable:true)
        lineOfBusiness(nullable:false)
    }
}

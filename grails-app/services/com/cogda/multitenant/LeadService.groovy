package com.cogda.multitenant

import com.cogda.domain.admin.LineOfBusiness

/**
 * LeadService
 * A service class encapsulates the core business logic of a Grails application
 */
class LeadService {

    static transactional = true

    def saveLeadAddress(LeadAddress leadAddress) {
        return leadAddress.save(flush: true)
    }

    def findLeadById(id, Map params) {
        return Lead.findById(id, params);
    }

    def getLead(id) {
        return Lead.get(id);
    }

    /**
     * Retrieves a List of LeadLineOfBusiness objects that match
     * the passed in LineOfBusiness.
     * @param id  the Lead.id
     * @param lineOfBusiness  a LineOfBusiness object
     * @return List - a set of LeadLineOfBusiness objects if found
     */
    def findLeadLineOfBusinessesByLob(id, LineOfBusiness lineOfBusiness){
        Lead lead = Lead.findById(id, [fetch:[linesOfBusiness:"eager"]])
        def leadLinesOfBusinessWithMatchingLob = new HashSet()
        if(lead){
            leadLinesOfBusinessWithMatchingLob = lead.linesOfBusiness.findAll { LeadLineOfBusiness leadLineOfBusiness ->
                    leadLineOfBusiness.lineOfBusiness == lineOfBusiness
                }
        }
        return leadLinesOfBusinessWithMatchingLob
    }

    /**
     * Retrieves a List of LeadLineOfBusiness objects that match
     * the passed in LineOfBusiness and have an LeadLineOfBusiness.expirationDate that is less than the
     * passed in expiration date.
     * @param id  the Lead.id
     * @param lineOfBusiness a LineOfBusiness object
     * @param expirationDate - a set of LeadLineOfBusiness objects if found
     */
    def findLeadLineOfBusinessesByLobAndExpirationDate(id, LineOfBusiness lineOfBusiness, Date expirationDate){
        def leadLinesOfBusinessWithMatchingLob = findLeadLineOfBusinessesByLob(id, lineOfBusiness)
        def dateConditionMatches = new HashSet()
        if(leadLinesOfBusinessWithMatchingLob){
            dateConditionMatches = leadLinesOfBusinessWithMatchingLob.findAll { LeadLineOfBusiness leadLineOfBusiness ->
                    leadLineOfBusiness.expirationDate <= expirationDate
               }
        }
        return dateConditionMatches
    }
}

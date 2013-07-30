package com.cogda.multitenant

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
}

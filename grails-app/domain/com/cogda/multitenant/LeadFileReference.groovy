package com.cogda.multitenant

import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadFileReference
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadFileReference {

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static mapping = {
    }

    static constraints = {

    }

    Lead lead

    static belongsTo = [lead:Lead]
}

package com.cogda.multitenant

import com.cogda.domain.admin.NoteType
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * LeadNote
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class LeadNote {

    Lead lead

    Note note

    NoteType noteType

    Date dateCreated
    Date lastUpdated

    static belongsTo = [lead: Lead]
    static embedded = ['note']

    static constraints = {
        noteType(nullable: true)
        note(nullable: false)
    }

}

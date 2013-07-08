package com.cogda.multitenant

import com.cogda.domain.Note
import com.cogda.domain.admin.NoteType
import grails.plugin.multitenant.core.annotation.MultiTenant

/**
 * AccountNote
 * A domain class describes the data object and it's mapping to the database
 */
@MultiTenant
class AccountNote {

    /**
     * Account account
     */
    Account account

    /**
     * Note note
     */
    Note note

    /* Automatic timestamping of GORM */
    Date dateCreated
    Date lastUpdated

    /**
     * NoteType noteType
     */
    NoteType noteType

    static belongsTo = [account:Account]
    static embedded = ['note']

    static constraints = {
        noteType(nullable:true)
        note(nullable:false)
    }

}

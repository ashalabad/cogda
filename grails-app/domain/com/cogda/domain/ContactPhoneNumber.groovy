package com.cogda.domain

import groovy.transform.ToString

/**
 * ContactPhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class ContactPhoneNumber {

    Contact contact

    boolean published
    boolean primaryPhoneNumber
    String description

    String phoneNumber

    static belongsTo	= [contact:Contact]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static constraints = {
        published(nullable:true)
        primaryPhoneNumber(nullable:true)
        description(nullable:true)
        phoneNumber(nullable:false, blank:false)
    }
}

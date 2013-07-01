package com.cogda.domain

import groovy.transform.ToString

/**
 * ContactEmailAddress
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class ContactEmailAddress {

    Contact contact

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    String emailAddress
    boolean primaryEmailAddress
    boolean published

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    static belongsTo	= [contact:Contact]

    static constraints = {
        emailAddress(nullable:false, email:true)
        published(nullable:true)
    }
}

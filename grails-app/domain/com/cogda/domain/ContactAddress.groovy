package com.cogda.domain

import groovy.transform.ToString

/**
 * ContactAddress
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class ContactAddress {

    Contact contact

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    boolean published
    boolean primaryAddress

    String addressType

    Address address

    static embedded = ['address']

    static belongsTo	= [contact:Contact]

    static mapping = {

    }

    static constraints = {
        addressType(nullable:true)
        published(nullable:true)
        primaryAddress(nullable:true)
    }
}

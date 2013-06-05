package com.cogda.domain

/**
 * UserProfileAddress
 * A domain class describes the data object and it's mapping to the database
 */
class UserProfileAddress {

    UserProfile userProfile

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

    static belongsTo	= [userProfile:UserProfile]

    static mapping = {

    }

    static constraints = {
        addressType(nullable:true)
        published(nullable:true)
        primaryAddress(nullable:true)
    }
}

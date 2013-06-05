package com.cogda.domain

/**
 * UserProfilePhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
class UserProfilePhoneNumber {
    UserProfile userProfile

    boolean published
    boolean primaryPhoneNumber
    String description

    PhoneNumber phoneNumber

    static embedded = ['phoneNumber']

	static belongsTo	= [userProfile:UserProfile]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static constraints = {
        published(nullable:true)
        primaryPhoneNumber(nullable:true)
        description(nullable:true)
        phoneNumber(nullable:true)
    }
}

package com.cogda.domain

/**
 * ContactEmailAddress
 * A domain class describes the data object and it's mapping to the database
 */
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
        primaryEmailAddress(nullable:true)
        published(nullable:true)
    }

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        return "${emailAddress}";
    }
}

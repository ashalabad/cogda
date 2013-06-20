package com.cogda.domain

import com.cogda.common.GenderEnum
import groovy.transform.ToString

/**
 * Contact
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class Contact {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    String jobTitle
    String website
    String companyName
    String firstName
    String middleName
    String lastName
    Date dateOfBirth
    GenderEnum gender
    String initials
    String title
    UserProfile userProfile  // Is this contact on Cogda?

    static transients   = ['primaryEmailAddress']

    static hasMany		= [contactEmailAddresses:ContactEmailAddress,
            contactPhoneNumbers:ContactPhoneNumber,
            contactAddresses:ContactAddress]

    static constraints = {
        jobTitle(nullable:true)
        website(nullable:true, url:true)
        companyName(nullable:true)
        firstName(nullable:false, blank:false)
        middleName(nullable:true)
        lastName(nullable:false, blank:false)
        dateOfBirth(nullable:true)
        gender(nullable:true, inList:GenderEnum.values().toList())
        initials(nullable:true)
        title(nullable:true)
        userProfile(nullable:true)
    }

    /**
     * Get the Primary Email Address for this UserProfile
     * @return String
     */
    public String getPrimaryEmailAddress(){
        ContactEmailAddress contactEmailAddress = this.contactEmailAddresses.find {
            it.primaryEmailAddress == Boolean.TRUE
        }
        return contactEmailAddress?.emailAddress
    }

    /**
     *
     * @param params
     * @return
     */
    static List<Map> displayContactList(params){
        List contacts = Contact.list(params)
        List displayContacts = []
        contacts.each { Contact contact ->
            List contactInfoList = []
            contactInfoList.add(contact.id)
            contactInfoList.add(contact.version)
            contactInfoList.add(contact.lastName)
            contactInfoList.add(contact.firstName)
            contactInfoList.add(contact.companyName)
            contactInfoList.add(contact.jobTitle)
            contactInfoList.add(contact.getPrimaryEmailAddress())
            displayContacts.add(contactInfoList.toArray())
        }
        return displayContacts
    }

}

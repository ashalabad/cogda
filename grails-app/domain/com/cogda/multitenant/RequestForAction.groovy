package com.cogda.multitenant

import com.cogda.domain.security.User

/**
 * RequestForAction
 * A domain class describes the data object and it's mapping to the database
 */
class RequestForAction {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated
    Date    dueDate
    Date    dateCompleted

    Submission submission

    User createdBy

    RequestForActionStatus requestForActionStatus

    RequestForAction parentRequestForAction

    String message

//	static belongsTo	= []	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
	static hasMany		= [assignees:User,requestForActionTypes:RequestForActionType,requestForActionDocuments:RequestForActionDocument]	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static mappedBy		= []	// specifies which property should be used in a mapping 

    static mapping = {
        message type:'text'
    }

    static constraints = {
        message maxSize:15000
        submission nullable: true
    }

    /*
     * Methods of the Domain Class
     */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}

package com.cogda.domain

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

	static hasMany		= [assignees:User,requestForActionTypes:RequestForActionType]	// tells GORM to associate other domain objects for a 1-n or n-m mapping


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

package com.cogda.multitenant

import com.cogda.domain.security.User

/**
 * RequestForActionLog
 * A domain class describes the data object and it's mapping to the database
 */
class RequestForActionLog {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    RequestForActionStatus requestForActionStatus

    String response

    User assignee

	static belongsTo	= [requestForAction:RequestForAction]	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.

    static constraints = {
        response nullable: true
    }

    /*
     * Methods of the Domain Class
     */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}

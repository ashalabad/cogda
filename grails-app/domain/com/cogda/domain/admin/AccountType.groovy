package com.cogda.domain.admin

/**
 * AccountType
 * Describes the type of Account.
 */
class AccountType {

    /* Default (injected) attributes of GORM */
	Long	id
	Long	version

    Integer intCode

    String code

    String description

    /* Automatic timestamping of GORM */
	Date	dateCreated
	Date	lastUpdated

    static constraints = {
        intCode(nullable:false, size:0..30)
        code(nullable:false, maxSize: 50, unique:true)
        description(nullable:true, maxSize: 255)
    }

    String toString(){
        code
    }

}

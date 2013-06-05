package com.cogda.domain

/**
 * PhoneNumber
 * A domain class describes the data object and it's mapping to the database
 */
class PhoneNumber {

    String phoneNumber

    static constraints = {
        phoneNumber(nullable:false)
    }
}

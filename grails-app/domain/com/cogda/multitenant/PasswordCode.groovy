package com.cogda.multitenant

/**
 * PasswordCode
 * A domain class describes the data object and it's mapping to the database
 */
class PasswordCode {

    String username
    String token = UUID.randomUUID().toString().replaceAll('-', '')
    Date dateCreated

    static mapping = {
        version false
    }

    static constraints = {
        username()
        token()
    }
}

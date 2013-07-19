package com.cogda.domain

/**
 * Note
 * A domain class describes the data object and it's mapping to the database
 */
class Note {

    String notes

    static mapping = {
        version false
        notes type:'text'
    }

    static constraints = {
        notes(maxSize:15000)
    }

}

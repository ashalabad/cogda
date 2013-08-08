package com.cogda.storage.exceptions

/**
 * User: igor_pol
 * Date: 8/3/13
 * Time: 5:48 PM
 * This exception is getting thrown when a Company does not have a key pair associated
 */
class KeyPairMissingException extends Exception{
    /**
     * Default constructor
     */
    KeyPairMissingException() {
    }
    /**
     *
     * @param s the exception message
     */
    KeyPairMissingException(String s) {
        super(s)
    }
}

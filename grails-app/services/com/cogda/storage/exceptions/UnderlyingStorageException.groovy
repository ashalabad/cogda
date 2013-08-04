package com.cogda.storage.exceptions

/**
 * Created with IntelliJ IDEA.
 * User: igor_pol
 * Date: 8/3/13
 * Time: 5:51 PM
 * Underlying storage exception is getting thrown when something goes bad in underlying storage
 *
 */
class UnderlyingStorageException extends Exception {

    UnderlyingStorageException(String s, Throwable throwable) {
        super(s, throwable)
    }

    UnderlyingStorageException(Throwable throwable) {
        super(throwable)
    }

    UnderlyingStorageException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1)
    }
}

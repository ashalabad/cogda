package com.cogda.common.marshallers

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/18/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
class CustomObjectMarshallers {

    List marshallers = []

    def register() {
        marshallers.each { it.register() }
    }
}

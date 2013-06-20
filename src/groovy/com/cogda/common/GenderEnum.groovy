package com.cogda.common

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 5/29/13
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
enum GenderEnum {

    MALE("M"), FEMALE("F")

    private final String value

    private GenderEnum(String value){
        this.value = value
    }

    String value() { value }
}

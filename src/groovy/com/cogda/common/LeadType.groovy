package com.cogda.common

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 6/25/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LeadType {
    SUSPECT(0), PROSPECT(1)

    LeadType(int value) {
        this.value = value
    }

    private final int value

    public int value() { return value }
}

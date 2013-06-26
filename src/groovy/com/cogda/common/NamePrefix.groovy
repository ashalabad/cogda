package com.cogda.common

/**
 * NamePrefix is used on the Contact page as the Title field.
 */
public enum NamePrefix {
    MR("Mr."), MRS("Mrs."), MS("Ms."), MISS("Miss."), DR("Dr.")

    NamePrefix(String value) {
        this.value = value
    }

    private final String value

    public String value() { return value }
}
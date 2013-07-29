package com.cogda

import spock.lang.Specification

abstract class ConstraintUnitSpec extends Specification {

    String getLongString(Integer length) {
        'a' * length
    }

    String getEmail(Boolean valid) {
        valid ? "dexter@miamipd.gov" : "dexterm@m"
    }

    String getUrl(Boolean valid){
        valid ? "http://www.google.com" : "sl.kd.s"
    }

    void validateConstraints(obj, field, error) {
        def validated = obj.validate()
        if (error && error != 'valid') {
            assert !validated
            assert obj.errors[field]
            assert error == obj.errors[field]
        } else {
            assert !obj.errors[field]
        }
    }
}

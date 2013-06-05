package com.cogda.domain

import com.cogda.common.AddressType

/**
 * Address
 * A domain class describes the data object and it's mapping to the database
 */
class Address {

    String addressOne
    String addressTwo
    String addressThree
    BigDecimal longitude
    BigDecimal latitude
    String city
    String state
    String country
    String county
    String zipcode

    static constraints = {
        addressOne(nullable:true)
        addressTwo(nullable:true)
        addressThree(nullable:true)
        longitude(nullable:true)
        latitude(nullable:true)
        city(nullable:true)
        state(nullable:true)
        country(nullable:true)
        county(nullable:true)
        zipcode(nullable:true)
    }
}

package com.cogda.domain

/**
 * CompanyProfileAddress
 * A domain class describes the data object and it's mapping to the database
 */
class CompanyProfileAddress {

    CompanyProfile companyProfile

    /* Default (injected) attributes of GORM */
    Long	id
    Long	version

    /* Automatic timestamping of GORM */
    Date	dateCreated
    Date	lastUpdated

    boolean published
    boolean primaryAddress

    String addressType

    Address address

    static embedded = ['address']

    static belongsTo	= [companyProfile:CompanyProfile]

    static constraints = {
        addressType(nullable:true)
        published(nullable:true)
        primaryAddress(nullable:true)
    }

    /**
     * Returns a
     * city state country combination in string format.
     * or
     * city and state
     * or
     * state and country
     * @return
     */
    String getCityStateCountryString(){
        def city = this.address.city?.trim()
        def state = this.address.state?.trim()
        def country = this.address.country?.toUpperCase()
        if(city && state && country){
            return "$city, $state $country"
        }
        if(city && state){
            return "$city, $state"
        }
        if(state && country){
            return "$state $country"
        }
        return ""
    }
}

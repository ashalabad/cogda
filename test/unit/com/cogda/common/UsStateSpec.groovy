package com.cogda.common

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class UsStateSpec extends spock.lang.Specification {

    def "findByKeyOrValue(q) returns a list of usStates based on the passed in query returns the correct list of UsState enums"() {
        expect:
        UsState.findByKeyOrValue(q) == usStates

        where:
        q        | usStates
        "G"      | [UsState.Georgia, UsState.Michigan, UsState.Oregon, UsState.Virginia, UsState.Washington, UsState.WestVirginia, UsState.Wyoming]
        "GA"     | [UsState.Georgia, UsState.Michigan]
        "g"      | [UsState.Georgia, UsState.Michigan, UsState.Oregon, UsState.Virginia, UsState.Washington, UsState.WestVirginia, UsState.Wyoming]
        "gA"     | [UsState.Georgia, UsState.Michigan]
        "ga"     | [UsState.Georgia, UsState.Michigan]
        "Ga"     | [UsState.Georgia, UsState.Michigan]
        "Geor"   | [UsState.Georgia]
    }

    def "search(q) returns map of matches"() {
        expect:
        UsState.search(q) == usStates

        where:
        q        | usStates
        "G"      | ["GA":"Georgia", "MI":"Michigan", "OR":"Oregon", "VA":"Virginia", "WA":"Washington", "WV":"West Virginia", "WY":"Wyoming"]
        "GA"     | ["GA":"Georgia", "MI":"Michigan"]
        "g"      | ["GA":"Georgia", "MI":"Michigan", "OR":"Oregon", "VA":"Virginia", "WA":"Washington", "WV":"West Virginia", "WY":"Wyoming"]
        "gA"     | ["GA":"Georgia", "MI":"Michigan"]
        "ga"     | ["GA":"Georgia", "MI":"Michigan"]
        "Ga"     | ["GA":"Georgia", "MI":"Michigan"]
        "wyoming"| ["WY":"Wyoming"]
    }

    def "valueOf(enumValue) returns UsState"(){
        expect:
        UsState.valueOf(enumValue) == usState

        where:
        enumValue          | usState
        "Georgia"          | UsState.Georgia
        "Michigan"         | UsState.Michigan
        "Oregon"           | UsState.Oregon
        "WestVirginia"     | UsState.WestVirginia
        "Wyoming"          | UsState.Wyoming
    }


    def "getByValue(value) returns UsState or null if UsState not found"(){
        expect:
        UsState.getByValue(value) == usState

        where:
        value              | usState
        "GA"               | UsState.Georgia
        "gA"               | UsState.Georgia
        "Ga"               | UsState.Georgia
        "ga"               | UsState.Georgia
        "MI"               | UsState.Michigan
        "OR"               | UsState.Oregon
        "WV"               | UsState.WestVirginia
        "WY"               | UsState.Wyoming
        "NotGoingToBeFound"| null

    }

    def "getByKey(key)"(){
        expect:
        UsState.getByKey(key) == usState

        where:
        key                     | usState
        "Georgia"               | UsState.Georgia
        "Georgia".toLowerCase() | UsState.Georgia
        "Georgia".toUpperCase() | UsState.Georgia
        "GeOrgia"               | UsState.Georgia
        "Michigan"              | UsState.Michigan
        "Oregon"                | UsState.Oregon
        "WestVirginia"          | UsState.WestVirginia
        "Wyoming"               | UsState.Wyoming
        "NotGoingToBeFound"     | null
    }

    def "findByKey(key)"(){
        expect:
        UsState.findByKey(key) == usStates

        where:
        key                     | usStates
        "Georgia"               | [UsState.Georgia]
        "Georgia".toLowerCase() | [UsState.Georgia]
        "Georgia".toUpperCase() | [UsState.Georgia]
        "GeOrgia"               | [UsState.Georgia]
        "Michigan"              | [UsState.Michigan]
        "Oregon"                | [UsState.Oregon]
        "WestVirginia"          | [UsState.WestVirginia]
        "Wyoming"               | [UsState.Wyoming]
        "NotGoingToBeFound"     | []
    }

    def "findByValue(value)"(){
        expect:
        UsState.findByValue(value) == usStates

        where:
        value                     | usStates
        "GA"                      | [UsState.Georgia]
        "gA"                      | [UsState.Georgia]
        "Ga"                      | [UsState.Georgia]
        "ga"                      | [UsState.Georgia]
        "MI"                      | [UsState.Michigan]
        "OR"                      | [UsState.Oregon]
        "WV"                      | [UsState.WestVirginia]
        "WY"                      | [UsState.Wyoming]
        "NotGoingToBeFound"       | []
    }

    def "getUsStatesMap()"(){
        given:
        Map usStates = ["AL":"Alabama",
                        "AK":"Alaska",
                        "AZ":"Arizona",
                        "AR":"Arkansas",
                        "CA":"California",
                        "CO":"Colorado",
                        "CT":"Connecticut",
                        "DE":"Delaware",
                        "FL":"Florida",
                        "GA":"Georgia",
                        "HI":"Hawaii",
                        "ID":"Idaho",
                        "IL":"Illinois",
                        "IN":"Indiana",
                        "IA":"Iowa",
                        "KS":"Kansas",
                        "KY":"Kentucky",
                        "LA":"Louisiana",
                        "ME":"Maine",
                        "MD":"Maryland",
                        "MA":"Massachusetts",
                        "MI":"Michigan",
                        "MN":"Minnesota",
                        "MS":"Mississippi",
                        "MO":"Missouri",
                        "MT":"Montana",
                        "NE":"Nebraska",
                        "NV":"Nevada",
                        "NH":"New Hampshire",
                        "NJ":"New Jersey",
                        "NM":"New Mexico",
                        "NY":"New York",
                        "NC":"North Carolina",
                        "ND":"North Dakota",
                        "OH":"Ohio",
                        "OK":"Oklahoma",
                        "OR":"Oregon",
                        "PA":"Pennsylvania",
                        "RI":"Rhode Island",
                        "SC":"South Carolina",
                        "SD":"South Dakota",
                        "TN":"Tennessee",
                        "TX":"Texas",
                        "UT":"Utah",
                        "VT":"Vermont",
                        "VA":"Virginia",
                        "WA":"Washington",
                        "WV":"West Virginia",
                        "WI":"Wisconsin",
                        "WY":"Wyoming"]

        expect:
        UsState.getUsStatesMap() == usStates


    }
}

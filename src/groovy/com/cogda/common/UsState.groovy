package com.cogda.common

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/6/13
 * Time: 9:31 AM
 * To change this template use File | Settings | File Templates.
 */
public enum UsState {

    Alabama("AL"),
    Alaska("AK"),
    Arizona("AZ"),
    Arkansas("AR"),
    California("CA"),
    Colorado("CO"),
    Connecticut("CT"),
    Delaware("DE"),
    Florida("FL"),
    Georgia("GA"),
    Hawaii("HI"),
    Idaho("ID"),
    Illinois("IL"),
    Indiana("IN"),
    Iowa("IA"),
    Kansas("KS"),
    Kentucky("KY"),
    Louisiana("LA"),
    Maine("ME"),
    Maryland("MD"),
    Massachusetts("MA"),
    Michigan("MI"),
    Minnesota("MN"),
    Mississippi("MS"),
    Missouri("MO"),
    Montana("MT"),
    Nebraska("NE"),
    Nevada("NV"),
    NewHampshire("NH"),
    NewJersey("NJ"),
    NewMexico("NM"),
    NewYork("NY"),
    NorthCarolina("NC"),
    NorthDakota("ND"),
    Ohio("OH"),
    Oklahoma("OK"),
    Oregon("OR"),
    Pennsylvania("PA"),
    RhodeIsland("RI"),
    SouthCarolina("SC"),
    SouthDakota("SD"),
    Tennessee("TN"),
    Texas("TX"),
    Utah("UT"),
    Vermont("VT"),
    Virginia("VA"),
    Washington("WA"),
    WestVirginia("WV"),
    Wisconsin("WI"),
    Wyoming("WY")

    final String value

    UsState(String value) {
        this.value = value
    }

    String toString() {
        value
    }

    String getKey() {
        name()
    }
}
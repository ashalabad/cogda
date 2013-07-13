package com.cogda.common

import org.apache.commons.lang.StringUtils

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

    /**
     * Given a UsState usState calling usState.toString() will return
     * e.g. "WV", "WI", "WY"
     * @return String
     */
    String toString() {
        value
    }

    /**
     * Given a UsState usState calling usState.getKey() will return
     * e.g. WestVirginia, Wisconsin, Wyoming
     * @return String
     */
    String getKey() {
        name()
    }

    /**
     * Finds a UsState by the value passed in where the Value
     * is a state abbreviation WI, WY
     *
     * This method ignores case
     * @param value
     * @return List
     */
    public static List<UsState> findByValue(String value){
        List<UsState> usStates = []
        for(UsState usState : values()){
            if(usState.value.equalsIgnoreCase(value)){
                usStates.add(usState)
            }
        }
        return usStates;
    }

    /**
     * Finds a UsState by the key passed in where the key
     * is WestVirginia or Wisconsin or Wyoming
     * This method ignores case
     * @param key
     * @return List
     */
    public static List<UsState> findByKey(String key){
        List<UsState> usStates = []
        for(UsState usState : values()){
            if(usState.key.equalsIgnoreCase(key)){
                usStates.add(usState)
            }
        }
        return usStates;
    }

    /**
     * Find method for UsState enum
     * @param q
     * @return List
     */
    public static List<UsState> findByKeyOrValue(String q){
        List<UsState> usStates = []
        for(UsState usState : values()){
            if(StringUtils.containsIgnoreCase(usState.value, q) ||
                    StringUtils.containsIgnoreCase(usState.key, q)){
                usStates.add(usState)
            }
        }
        return usStates;
    }
    /**
     * search method for UsState enum
     * @param q
     * @return Map
     */
    public static Map search(String q){
        Map<String, String> matches = [:]

        List<UsState> usStates = UsState.findByKeyOrValue(q)
        usStates.each{ UsState usState ->
            matches.put(usState.value, usState.key)
        }

        return matches
    }

    /**
     * passing in the value WV
     * will return UsState.WestVirginia.
     * passing in a value that is not a UsState will result in a null
     * @param value
     * @return UsState or null
     */
    public static UsState getByValue(String value){
        UsState usState = null
        List usStates = UsState.findByValue(value)
        if(usStates){
            usState = usStates.first()
        }
        return usState
    }

    /**
     * passing in the key Wyoming or wyoMINg
     * will return UsState.Wyoming.
     * passing in a key that is not a UsState will result in a null
     * @param key
     * @return UsState or null
     */
    public static UsState getByKey(String key){
        UsState usState
        List usStates = UsState.findByKey(key)
        if(usStates){
            usState = usStates.first()
        }
        return usState
    }
}
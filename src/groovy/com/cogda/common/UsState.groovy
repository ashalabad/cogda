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

    Alabama("AL", "Alabama"),
    Alaska("AK", "Alaska"),
    Arizona("AZ", "Arizona"),
    Arkansas("AR", "Arkansas"),
    California("CA", "California"),
    Colorado("CO", "Colorado"),
    Connecticut("CT", "Connecticut"),
    Delaware("DE", "Delaware"),
    Florida("FL", "Florida"),
    Georgia("GA", "Georgia"),
    Hawaii("HI", "Hawaii"),
    Idaho("ID", "Idaho"),
    Illinois("IL", "Illinois"),
    Indiana("IN", "Indiana"),
    Iowa("IA", "Iowa"),
    Kansas("KS", "Kansas"),
    Kentucky("KY", "Kentucky"),
    Louisiana("LA", "Louisiana"),
    Maine("ME", "Maine"),
    Maryland("MD", "Maryland"),
    Massachusetts("MA", "Massachusetts"),
    Michigan("MI", "Michigan"),
    Minnesota("MN", "Minnesota"),
    Mississippi("MS", "Mississippi"),
    Missouri("MO", "Missouri"),
    Montana("MT", "Montana"),
    Nebraska("NE", "Nebraska"),
    Nevada("NV", "Nevada"),
    NewHampshire("NH", "New Hampshire"),
    NewJersey("NJ", "New Jersey"),
    NewMexico("NM", "New Mexico"),
    NewYork("NY", "New York"),
    NorthCarolina("NC", "North Carolina"),
    NorthDakota("ND", "North Dakota"),
    Ohio("OH", "Ohio"),
    Oklahoma("OK", "Oklahoma"),
    Oregon("OR", "Oregon"),
    Pennsylvania("PA", "Pennsylvania"),
    RhodeIsland("RI", "Rhode Island"),
    SouthCarolina("SC", "South Carolina"),
    SouthDakota("SD", "South Dakota"),
    Tennessee("TN", "Tennessee"),
    Texas("TX", "Texas"),
    Utah("UT", "Utah"),
    Vermont("VT", "Vermont"),
    Virginia("VA", "Virginia"),
    Washington("WA", "Washington"),
    WestVirginia("WV", "West Virginia"),
    Wisconsin("WI", "Wisconsin"),
    Wyoming("WY", "Wyoming")

    final String value
    final String description

    UsState(String value, String description) {
        this.value = value
        this.description = description
    }

    /**
     * Given a UsState usState calling usState.toString() will return
     * e.g. "WV", "WI", "WY"
     * @return String
     */
    String toString() {
        value + " " + description
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
            matches.put(usState.value, usState.description)
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

    /**
     * Returns a Map that consists of the following:
     * <br/>
     * key:   State Abbreviation e.g. NY
     * value: State description e.g. New York
     * @return
     */
    public static Map getUsStatesMap(){
        Map<String, String> usStatesMap = [:]
        for(UsState usState : values()){
            usStatesMap.put(usState.value, usState.description)
        }
        return usStatesMap
    }
}
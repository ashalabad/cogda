package com.cogda.security

import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.StringUtils

/**
 * This class features methods that create auto-generated passwords.
 * @author christopher
 */
class PasswordGenerator {
    final static String PASSWORD_REGEX = '^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'
    final static int COUNT = 12
    final static boolean LETTERS = true
    final static boolean NUMBERS = true
    final static String SPECIAL_CHARACTERS = /!@#$%^&/
    final static String NUMBER_CHARACTERS = /0123456789/
    final static String ALPHANUMERIC_CHARACTERS = /ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz/
    final static String ALLOWED_PASSWORD_CHARACTERS = SPECIAL_CHARACTERS + NUMBER_CHARACTERS + ALPHANUMERIC_CHARACTERS

    /**
     * <p>Creates a temporary password that meets the
     * security constraints of the system as set forth in
     * the PasswordGenerator.PASSWORD_REGEX
     * <p>The password returned will have at least one accepted
     * special character from SPECIAL_CHARACTERS
     * <p>The password returned will have at least one accepted
     * Integer number from  NUMBER_CHARACTERS
     * <p>The password generated will be 12 characters in length
     * <p>Our policy on passwords is that we:
     * <ul>
     *     <li>require a minimum length of at least 8 characters
     *     <li>a mix of upper and lower case alpha characters
     *     <li>at least one special char !@#$%^&
     *     <li>at least one number character 0123456789
     * </ul>
     * @return String
     */
    public static String createTempPassword(){
        // Create a random string with the count provided
        String password = RandomStringUtils.random(COUNT, 0,
                ALLOWED_PASSWORD_CHARACTERS.size(),
                LETTERS, NUMBERS,
                ALLOWED_PASSWORD_CHARACTERS as char[])
        while( !(password ==~ PASSWORD_REGEX) ){
            password = injectRandomNumber(password)
            password = injectRandomSpecialCharacter(password)
        }
        return password
    }

    /**
     * Generates a random number in the range of min..max
     * @param min
     * @param max
     * @return Integer
     */
    private static Integer generateRandomIntegerInRange(Integer min = 0, Integer max){
        Random rand = new Random()
        rand.nextInt(max - min + 1) + min;
    }

    private static String generateRandomBoundInteger(){
        return generateRandomCharacter(NUMBER_CHARACTERS)
    }

    private static String generateRandomSpecialCharacter(){
        return generateRandomCharacter(SPECIAL_CHARACTERS)
    }

    private static String generateRandomCharacter(String text){
        Integer max = text.size() - 1
        Integer min = 0
        Integer randIdx = generateRandomIntegerInRange(min, max)
        return text.charAt(randIdx)
    }

    /**
     *
     * @param pass
     * @return
     */
    private static String injectRandomSpecialCharacter(String pass){
        Integer injectionSite = generateRandomIntegerInRange(pass.size()-1)
        return StringUtils.overlay(pass, generateRandomSpecialCharacter(),
                injectionSite, injectionSite+1)
    }

    /**
     *
     * @param pass
     * @return
     */
    private static String injectRandomNumber(String pass){
        Integer injectionSite = generateRandomIntegerInRange(pass.size()-1)
        return StringUtils.overlay(pass, generateRandomBoundInteger(),
                injectionSite, injectionSite+1)
    }


}


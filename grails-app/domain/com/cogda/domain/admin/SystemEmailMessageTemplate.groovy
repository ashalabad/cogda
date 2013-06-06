package com.cogda.domain.admin

import com.petebevin.markdown.MarkdownProcessor
import com.cogda.common.MarkupLanguage
import java.util.Date;
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.commons.collections.CollectionUtils
/**
 * IMPORTANT: System maintained.
 * Can not be changed by a client.
 * EmailMessage
 * The SystemEmailMessage maintains the email
 * message body information that we use for
 * sending messages to clients.
 */
class SystemEmailMessageTemplate {
    /**
     * The PARAMETER_KEY_PATTERN is the compiled regex pattern
     * that is used to extract dynamic keys from the SystemEmailMessage
     * body.  The format for a key is {key}.
     */
    static final Pattern PARAMETER_KEY_PATTERN = Pattern.compile("\\{(.+?)\\}")

    /**
     * The PARAMETER_NAME_PATTERN is the compiled regex pattern
     * that is used to validate the individual Strings in the
     * requiredParameterNames List
     */
    static final Pattern PARAMETER_NAME_PATTERN = Pattern.compile("^[a-z][a-zA-Z]*\$")

    /**
     * The Markup language used to build this email
     * message.
     */
    MarkupLanguage markupLanguage

    /**
     * A unique message Title used for programmatic lookups.
     */
    String title

    /**
     * A short description of what this SystemEmailMessage is
     * used for.
     */
    String description

    /**
     * The message body that is sent to the customer.
     */
    String body

    /**
     * Subject
     */
    String subject

    /**
     * The email that this message should be sent from.
     */
    String fromEmail

    /**
     * If acceptsParameters = true then the body of this
     * message can be passed a Map of named parameters and
     * the renderBodyArgs
     */
    Boolean acceptsParameters

    /**
     * If acceptsParameters = true then the body of this
     * message must contain the requiredParameterNames
     * specified in the list.  Each parameter in requiredParameterNames
     * must be specified at least once.
     */
    List<String> requiredParameterNames

    static hasMany		= [requiredParameterNames:String]

    /* Automatic timestamping by Hibernate */

    /**
     * Date dateCreated<br>
     * Hibernate managed audit-logging
     */
    Date dateCreated

    /**
     * Date lastUpdated
     * Hibernate managed audit-logging
     */
    Date lastUpdated

    static mapping = {
        body type:'text'
    }

    static constraints = {
        markupLanguage(nullable:false, inList: (MarkupLanguage.values() as List))
        requiredParameterNames(nullable:true, validator: { val, obj ->
            if(obj.acceptsParameters && !val){
                return ['parameters.notfound']
            }
            if(!obj.acceptsParameters && val){
                return ['acceptsParameters.isfalse']
            }
            if(val){
                // validate each String in the list
                // for camel case alphanumeric
                def invalidParameterNames = []
                val.each {
                    if(!SystemEmailMessage.PARAMETER_NAME_PATTERN.matcher(it).matches()){
                        invalidParameterNames.add(it)
                    }
                }
                if(invalidParameterNames.isEmpty() == false){
                    return ['invalid.parameters', invalidParameterNames.toString()]
                }
            }
        })
        title(nullable:false, size:0..35, unique:true, matches: "[A-Z0-9_]+")
        description(nullable:false, size:0..255)
        subject(nulllable:false, size:0..255)
        fromEmail(nullable:false, email:true)
        body(nullable:false)
        acceptsParameters(nullable:true, validator: { val, obj ->
            if(val){
                if(!obj.requiredParameterNames){
                    return ['parameters.required', val]
                }
            }
        })
    }

    /*
     * Methods of the Domain Class
     */
    @Override	// Override toString for a nicer / more descriptive UI
    public String toString() {
        """${this.class.name}
markupLanguage: $markupLanguage
title: $title
description: $description
body: $body
subject: $subject
fromEmail: $fromEmail
acceptsParameters: $acceptsParameters
requiredParameterNames: $requiredParameterNames
dateCreated: $dateCreated
lastUpdated: $lastUpdated"""
    }

    /**
     * Returns a Map<String, List> that contains the results
     * of a comparison of whether the parameters that currently
     * exist in the body of this SystemEmailMessage are valid.
     * <ul>
     * <li>paramCheck.valid = The valid keys that match those found in the requiredParameterNames
     * <li>paramCheck.missing = The keys that are specified as a required in requiredParameterNames but were not found in the SystemEmailMessage body
     * <li>paramCheck.invalid = The keys that were found in the SystemEmailMessage body, but were not found in the requiredParameterNames
     * </ul>
     * <br/>
     * TODO: Unit test
     * @return Map
     */
    private Map retrieveParamCheck(){
        // if this instance does not accept parameters then
        // always return empty Map
        if(!acceptsParameters) return [:]

        List currentParameterNames = retrieveCurrentParameterNames()

        Map paramCheck = [:]

        paramCheck.valid = CollectionUtils.retainAll(this.requiredParameterNames, currentParameterNames)
        paramCheck.missing = CollectionUtils.subtract(this.requiredParameterNames, currentParameterNames)
        paramCheck.invalid = CollectionUtils.subtract(currentParameterNames, this.requiredParameterNames)

        return paramCheck
    }

    /**
     * Retrieves the parameter keys from the
     * body of this SystemEmailMessage based on
     * the PARAMETER_KEY_PATTERN.
     * <br/>
     * @return List
     */
    private List retrieveCurrentParameterNames(){
        Matcher matcher = this.body =~ PARAMETER_KEY_PATTERN
        List keys = matcher.collect { it[1] }
        keys = keys.unique()
        keys.each { assert it instanceof String }
        return keys
    }

    /**
     * Returns true if the body contains all of the parameters
     * required.  Returns true if this SystemEmailMessage
     * does not accept parameters.
     * <br/>
     * @return boolean
     */
    private boolean hasAllRequiredParameters(){
        // if this instance does not accept parameters then
        // always return true
        if(!acceptsParameters) return true

        List keys = retrieveCurrentParameterNames()

        return requiredParameterNames.size() == keys.size() && requiredParameterNames.containsAll(keys)
    }

    /**
     * Writes the email message output.
     * @param (optional) Map replacements
     * @return String
     */
    String writeMessageOutput(Map replacements = null){
        if(this.acceptsParameters){
            if(!replacements){
                throw new IllegalArgumentException("The method parameter replacements is null, " +
                        " but the SystemEmailMessage indicates that this instance acceptsParameters")
            }
            String postApplyParams = applyMapArgs(replacements)
            return convertMarkup(postApplyParams)
        }else{
            return convertMarkup()
        }
    }

    /**
     * Converts the body text to HTML based on the
     * defined markupLanguage.
     * @param String messageBody
     * @return String
     */
    private String convertMarkup(String messageBody){
        switch(markupLanguage){
            case MarkupLanguage.MARKDOWN:
                MarkdownProcessor processor = new MarkdownProcessor()
                return processor.markdown(messageBody)
            case MarkupLanguage.PLAINTEXT:
                return messageBody
            default:
                log.warn("SystemEmailMessage could not find a markup language to process text-to-html.")
                return messageBody
        }
    }

    /**
     * Templates can be stored in the body property of this domain class
     * passing a map in will match any body parameter with the
     * following format {key} will be replaced with the associated value
     * in the map.  Any {key} that does not have a match in the passed in
     * mapArgs will be removed and null text will replace them.
     *
     * @param map
     * @return String
     */
    String applyMapArgs(Map replacements){
        Matcher matcher = PARAMETER_KEY_PATTERN.matcher(this.body);
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String replacement = replacements.get(matcher.group(1));
            builder.append(this.body.substring(i, matcher.start()));
            if (replacement == null){
                builder.append("");  // append a null string
            }else{
                builder.append(replacement);
            }
            i = matcher.end();
        }
        builder.append(this.body.substring(i, this.body.length()));
        return builder.toString()
    }
}

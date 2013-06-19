package com.cogda.command

import au.com.bytecode.opencsv.CSVParser
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.security.UserService
import grails.validation.Validateable
import org.codehaus.groovy.grails.web.context.ServletContextHolder as SCH
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes as GA
/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/18/13
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Validateable
class UserImportCommand {
    def ctx = SCH.servletContext.getAttribute(GA.APPLICATION_CONTEXT)
    UserService userService = ctx.userService

    String username
    String firstName
    String lastName
    String emailAddress
    String securityRoles
    List<String> parsedSecurityRoles = []

    String suggestedUsername

    String getSuggestedUsername(){
        if(emailAddress?.trim()){
            return emailAddress?.substring(0, emailAddress?.indexOf('@'))
        }
    }

    static constraints = {
        importFrom Registration, include: ["firstName", "lastName", "emailAddress"]
        username(nullable:false, blank:false, minSize:2, matches: "[A-Za-z0-9]+", validator: { val, obj ->
            if(!obj.userService.availableUsername(val)){
                return ['registration.username.taken']
            }
        })
    }

    /**
     * Parses the securityRoles from the column in the CSV file.
     * @param securityRoles
     * @return List
     */
    public List<String> getParsedSecurityRoles(){
        CSVParser parser = new CSVParser()
        String[] securityRolesArr = parser.parseLine(securityRoles)
        return securityRolesArr.toList()
    }

    /**
     *
     * @return
     */
    public String toString(){
        def s = """${this.class.name}
username: $username
firstName: $firstName
lastName: $lastName
emailAddress: $emailAddress
securityRoles: $securityRoles
suggestedUsername: $suggestedUsername
parsedSecurityRoles: $parsedSecurityRoles"""
        return s
    }
}

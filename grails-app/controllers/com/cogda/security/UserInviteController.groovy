package com.cogda.security

import com.cogda.domain.onboarding.AvailableUsernameCommand
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.PendingUser
import com.cogda.BaseController
import com.google.gson.GsonBuilder
import grails.plugin.gson.converters.GSON

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * UserInviteController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class UserInviteController extends BaseController{

    static allowedMethods = [save: "POST"]

    UserService userService
    GsonBuilder gsonBuilder

    def index(){
        String token = params.t?.trim()

        if(!token){
            // respond that this page does not exist if the token is not found
            response.status = SC_NOT_FOUND
            return
        }

        PendingUser importedUser = token ? PendingUser.retrievePendingByToken(token) : null

        if(!importedUser){
            importedUser = PendingUser.findByToken(token)
            if(importedUser){
                // Check the current status and give the user a meaningful response.
                switch(importedUser.pendingUserStatus){
                    case PendingUserStatus.COMPLETED:
                        flash.message = message(code:'userInvite.status.completed')
                        return
                }
            }

            flash.error = message(code:'userInvite.token.invalid')
            return
        }

        flash.message = message(code:'userInvite.welcome')

        UserInviteCommand userInviteCommand = new UserInviteCommand()
        userInviteCommand.firstName = importedUser.firstName
        userInviteCommand.lastName = importedUser.lastName
        userInviteCommand.emailAddress = importedUser.emailAddress
        userInviteCommand.t = params.t
        [userInviteCommand:userInviteCommand]
    }

    /**
     * Renders the string response true if the Username is available.
     * Renders the string response "false" if the Username is unavailable.
     * @param availableUsernameCommand
     * @return String
     */
    def availableUsername(AvailableUsernameCommand availableUsernameCommand) {
        boolean valid = Boolean.FALSE
        if (!availableUsernameCommand.hasErrors()) {
            valid = Boolean.TRUE
        }
        render valid
        return
    }

    /**
     * Creates a new user based on the passed in information.
     * Upon successful save of the User redirects the user to the login screen.
     */
    def save(UserInviteCommand userInviteCommand){
        userInviteCommand.validate()
        if (!userInviteCommand.hasErrors()) {
            userService.createFromUserInvite(userInviteCommand)
            respondCreated userInviteCommand
        } else {
            respondUnprocessableEntity userInviteCommand
        }
    }

    def success(){
        render(template:"successful")
    }

    private void respondCreated(UserInviteCommand userInviteCommand) {
        response.status = SC_OK // 200
        response.addHeader LOCATION, createLink(controller:"login", params:["username":userInviteCommand.username])
        def responseBody = [:]
        responseBody.messageTitle = message(code: 'default.success.message.title')
        responseBody.message = message(code: 'userInvite.create.success')
        response.status = SC_OK  // 200
        render responseBody as GSON
    }

    private void respondUnprocessableEntity(UserInviteCommand userInviteCommand) {
        def responseBody = [:]
        responseBody.errors = userInviteCommand.errors.allErrors.collect {
            message(error: it)
        }
        response.status = SC_UNPROCESSABLE_ENTITY // 422
        render responseBody as GSON
    }

}

class UserInviteCommand{
    UserService userService

    String username
    String firstName
    String middleName
    String lastName
    String emailAddress
    String password
    String passwordTwo
    String t                // the PendingUser.token

    static constraints = {
        importFrom Registration, include: ["firstName", "lastName", "emailAddress", "password", "passwordTwo"]
        username(nullable:false, blank:false, matches: "[A-Za-z0-9]+", minSize:2, validator: { val, obj ->
            if(!obj.userService.availableUsername(val)){
                return ['registration.username.taken']
            }
        })
        t(nullable:false, blank:false, validator: { val, obj ->
            if(!PendingUser.findByToken(val)){
                ['userInvite.token.invalid']
            }
        })
    }
}

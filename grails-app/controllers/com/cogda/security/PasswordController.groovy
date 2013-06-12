package com.cogda.security

import com.cogda.BaseController
import com.cogda.domain.UserProfile
import com.cogda.domain.admin.CustomerNotificationService
import com.cogda.domain.security.User
import com.cogda.domain.security.PasswordCode
import grails.plugins.springsecurity.SpringSecurityService
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 * PasswordController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class PasswordController extends BaseController{
    SpringSecurityService springSecurityService
    CustomerNotificationService customerNotificationService

    static allowedMethods = [forgotPassword:'POST']

    def index(){
        render(view:'forgotPassword')
    }

    def forgotPassword() {
        String username = params.username

        if (!username) {
            flash.error = message(code: 'spring.security.forgotPassword.username.missing')
            return
        }

        User user = User.findByUsername(username)
        if (!user) {
            flash.error = message(code: 'spring.security.forgotPassword.user.notFound')
            return
        }

        UserProfile userProfile = UserProfile.findByUser(user)

        // If the user already has a PasswordCode out in the system then
        // re-use it.
        PasswordCode passwordCode = PasswordCode.findByUsername(user.username)
        if(!passwordCode){
            passwordCode = new PasswordCode(username: user.username)
            passwordCode.save(flush: true)
        }

        String resetPasswordUrl = generateLink('password', 'resetPassword', [t: passwordCode.token])

        customerNotificationService.prepareResetPasswordMessage(userProfile, resetPasswordUrl)

        flash.message = message(code:'spring.security.resetPassword.emailSent', args:[userProfile.primaryEmailAddress])

        [emailSent: true]
    }

    def resetPassword(ResetPasswordCommand command){

        String token = params.t

        PasswordCode passwordCode = token ? PasswordCode.findByToken(token) : null
        if (!passwordCode) {
            flash.error = message(code: 'spring.security.resetPassword.badCode')
            redirect uri: "/"
            return
        }

        if (!request.post) {
            flash.message = ""
            return [token: token, command: new ResetPasswordCommand()]
        }

        command.username = passwordCode.username

        command.validate()

        if (command.hasErrors()) {
            return [token: token, command: command]
        }

        PasswordCode.withTransaction { status ->
            def user = User.findByUsername(passwordCode.username)
            user.password = springSecurityService.encodePassword(command.password)
            user.save()
            passwordCode.delete()
        }

        springSecurityService.reauthenticate passwordCode.username

        flash.message = message(code: 'spring.security.resetPassword.success')

        redirect(url:generateRedirectLink())
    }

    static final passwordValidator = { String password, command ->
        if (command.username && command.username.equals(password)) {
            return 'command.password.error.username'
        }

        if (!checkPasswordMinLength(password, command) ||
                !checkPasswordMaxLength(password, command)) {
            return 'command.password.error.strength'
        }
    }

    static boolean checkPasswordMinLength(String password, command) {
        def conf = SpringSecurityUtils.securityConfig

        int minLength = conf.ui.password.minLength instanceof Number ? conf.ui.password.minLength : 6

        password && password.length() >= minLength
    }

    static boolean checkPasswordMaxLength(String password, command) {
        def conf = SpringSecurityUtils.securityConfig

        int maxLength = conf.ui.password.maxLength instanceof Number ? conf.ui.password.maxLength : 64

        password && password.length() <= maxLength
    }

    static boolean checkPasswordRegex(String password, command) {
        def conf = SpringSecurityUtils.securityConfig

        String passValidationRegex = conf.ui.password.validationRegex ?:
            '^.*(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$'

        password && password.matches(passValidationRegex)
    }

    static final passwordTwoValidator = { value, command ->
        if (command.password != command.passwordTwo) {
            return 'command.passwordTwo.error.mismatch'
        }
    }

    static final forgotPasswordFormValidator = { value, command ->
        if (!command.username && !command.emailAddress) {
            return 'forgotPasswordCommand.onefield.required'
        }
    }

    protected String generateLink(String controller, String action, linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                controller: controller, action: action,
                params: linkParams)
    }
}

class ForgotPasswordCommand {
    String username
    String emailAddress

    static constraints = {
        username()
        emailAddress(email:true)
    }
}

class ResetPasswordCommand {
    String username
    String password
    String passwordTwo

    static constraints = {
        username nullable: false
        password blank: false, nullable: false, validator: PasswordController.passwordValidator
        passwordTwo validator: PasswordController.passwordTwoValidator
    }
}
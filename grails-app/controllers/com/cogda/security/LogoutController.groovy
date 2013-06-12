package com.cogda.security

import com.cogda.BaseController
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class LogoutController extends BaseController{

    /**
     * Index action. Redirects to the Spring security logout uri.
     */
    def index = {
        redirect url: generateLogoutLink() // '/j_spring_security_logout'
    }

}

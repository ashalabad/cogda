package com.cogda.common

import grails.util.Environment
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*
/**
 * ErrorController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class ErrorController {

    /**
     * Handling some errors for different environments
     */
	def index(){

        Exception exception = request.exception
        String errorMessage = request?.'javax.servlet.error.message'

        log.error "Response Status at time of Exception: ${request.'javax.servlet.error.status_code'}"
        log.error "Error Message: ${errorMessage}"
        log.error "Servlet Name: ${request.'javax.servlet.error.servlet_name'}"
        log.error "Request URI: ${request.'javax.servlet.error.request_uri'}"
        log.error "Request URL: "  + request.requestURL
        log.error "exception.message: ${exception?.message}"
        log.error "exception.cause.message: ${exception?.cause?.message}"
        log.error "exception.className: ${exception?.className}"
        log.error "exception.lineNumber: ${exception?.lineNumber}"

        request.getHeaderNames().each { String headerName ->
            log.debug headerName + ": " + request.getHeader(headerName)
        }

        if( (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.TEST)
            || Environment.CUSTOM.name == "develop"
        ){
            // Render the Error page with the exception and the stacktrace in the event that we are in the environment
            // development, test, or develop so that we can get the most out of the error messages.
            render(controller:"error", view:"error")
            return
        }else{
            switch(response.status){
                case SC_FORBIDDEN:             // 403
                    render(controller:"error", view:SC_FORBIDDEN.toString())
                    return
                case SC_NOT_FOUND:             // 404
                    render(controller:"error", view:SC_NOT_FOUND.toString())
                    return
                case SC_SERVICE_UNAVAILABLE:   // 503
                    render(controller:"error", view:SC_SERVICE_UNAVAILABLE.toString())
                    return
                case SC_INTERNAL_SERVER_ERROR: // 500
                    // mea clupa
                    render(controller:"error", view:SC_INTERNAL_SERVER_ERROR.toString())
                    return
                default:
                    render(controller:"error", view:"unknown")
                    return
            }
        }
        // Just In Case of a fall through...
        log.error "An Exception was thrown - but no page was served to handle the exception!"
        render(controller:"error", view:"unknown")
        return
    }
}

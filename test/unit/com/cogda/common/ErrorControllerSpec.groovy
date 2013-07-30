package com.cogda.common

import grails.util.Environment
import grails.util.Metadata
import org.codehaus.groovy.grails.web.errors.GrailsWrappedRuntimeException
import spock.lang.Specification

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*
import grails.test.mixin.*
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ErrorController)
@TestMixin([ControllerUnitTestMixin])
class ErrorControllerSpec extends Specification{

    def cleanup() {
        System.setProperty(Environment.KEY, "")
        System.setProperty(Environment.RELOAD_ENABLED, "")
        System.setProperty(Environment.RELOAD_LOCATION, "")
    }

    def "403 error thrown in PRODUCTION environment results in correct page"(){
        given: "A response status code of $SC_FORBIDDEN"
        response.setStatus(SC_FORBIDDEN)

        and: "execution environment is production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        when: "ErrorController index action is called"
        controller.index()

        then: "Renders the view with name '403'"
        view == "/error/$SC_FORBIDDEN"
    }

    def "404 error thrown in PRODUCTION environment results in correct page"(){
        given: "A response status code of $SC_NOT_FOUND"
        response.setStatus(SC_NOT_FOUND)
        response.sendError(SC_NOT_FOUND)

        and: "execution environment is production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        when: "ErrorController index action is called"
        controller.index()

        then: "Renders the view with name '404'"
        view == "/error/$SC_NOT_FOUND"
    }

    def "503 error thrown in PRODUCTION environment results in correct page"(){
        given: "A response status code of $SC_SERVICE_UNAVAILABLE"
        response.setStatus(SC_SERVICE_UNAVAILABLE)

        and: "execution environment is production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        when: "ErrorController index action is called"
        controller.index()

        then: "Renders the view with name '503'"
        view == "/error/$SC_SERVICE_UNAVAILABLE"
    }

    def "500 error thrown in PRODUCTION environment results in correct page"(){
        given: "A response status code of $SC_INTERNAL_SERVER_ERROR"
        response.setStatus(SC_INTERNAL_SERVER_ERROR)

        and: "execution environment is production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        when: "ErrorController index action is called"
        controller.index()

        then: "Renders the view with name '500'"
        view == "/error/$SC_INTERNAL_SERVER_ERROR"
    }

    def "Unknown error thrown in PRODUCTION environment results in correct page"(){
        given: "A response status code of $SC_HTTP_VERSION_NOT_SUPPORTED"
        response.setStatus(SC_HTTP_VERSION_NOT_SUPPORTED)

        and: "execution environment is production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        when: "ErrorController index action is called"
        controller.index()

        then: "Renders the view with name '505'"
        view == "/error/unknown"
    }

    def "errors thrown in environment development are shown on the detailed error page"(){
        given: "A response status code of $SC_HTTP_VERSION_NOT_SUPPORTED"
        response.setStatus(SC_HTTP_VERSION_NOT_SUPPORTED)

        and: "execution environment is production"
        setExecutionEnvironment(Environment.DEVELOPMENT.name)

        when: "ErrorController index action is called"
        controller.index()

        then: "Renders the view with name error"
        view == "/error/error"
    }

    private setExecutionEnvironment(String environmentName){
        System.setProperty("grails.env", environmentName)
    }

}

package com.cogda.common

import com.google.gson.Gson
import grails.test.mixin.*
import org.junit.*
import grails.test.mixin.web.ControllerUnitTestMixin
import grails.plugin.gson.test.GsonUnitTestMixin
import grails.test.mixin.*
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

import static grails.plugin.gson.http.HttpConstants.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UnitedStatesController)
@TestMixin([ControllerUnitTestMixin, GsonUnitTestMixin])
class UnitedStatesControllerSpec extends Specification{

    void "list() returns JSON UsState"() {
        given:
        Gson gson = new Gson()

        when:
        controller.list()

        then:
        assert response.containsHeader(X_PAGINATION_TOTAL)
        assert response.getHeader(X_PAGINATION_TOTAL).toInteger() == UsState.getUsStatesMap().size()
//        println "Response JSON: " + response.getContentAsString()
        assert response.getContentAsString() == gson.toJson(UsState.getUsStatesMap())

        response.json.each { k, v ->
            assert v == UsState.getByValue(k).description
        }
    }

    void "search() with params.q returns JSON UsState results"(){
        given:
        Gson gson = new Gson()

        when:
        params.q = "g"
        controller.search()

        then:
        assert response.containsHeader(X_PAGINATION_TOTAL)
        assert response.getHeader(X_PAGINATION_TOTAL).toInteger() == UsState.search(params.q).size()
//        println "Response JSON: " + response.getContentAsString()
        assert response.getContentAsString() == gson.toJson(UsState.search(params.q))
        assert response.json.GA == UsState.Georgia.description
        assert response.json.MI == UsState.Michigan.description
        assert response.json.OR == UsState.Oregon.description
        assert response.json.VA == UsState.Virginia.description
        assert response.json.WA == UsState.Washington.description
        assert response.json.WV == UsState.WestVirginia.description
        assert response.json.WY == UsState.Wyoming.description

        assertNull response.json.NY
    }

    void "search() with params.q = null string returns all JSON UsState results"(){
        given:
        Gson gson = new Gson()

        when:
        params.q = ""
        controller.search()

        then:
        assert response.containsHeader(X_PAGINATION_TOTAL)
        assert response.getHeader(X_PAGINATION_TOTAL).toInteger() == UsState.getUsStatesMap().size()
//        println "Response JSON: " + response.getContentAsString()
        assert response.getContentAsString() == gson.toJson(UsState.getUsStatesMap())

        response.json.each { k, v ->
            assert v == UsState.getByValue(k).description
        }
    }

    void "get() with valid state abbreviation returns Json for single state "(){
        given:
        Gson gson = new Gson()
        params.id = "GA"

        when:
        controller.get()

        then:
        assert response.status == HttpServletResponse.SC_OK
        println "Response JSON: " + response.getContentAsString()
        assert response.json.GA == UsState.getByValue(params.id).description

    }

    void "get() with INvalid state abbreviation returns Json response message and 404 status "(){
        given:
        Gson gson = new Gson()
        params.id = "01010101"

        when:
        controller.get()

        then:
        assert response.status == HttpServletResponse.SC_NOT_FOUND
        assert response.json.message == "default.not.found.message"
        println "Response JSON: " + response.getContentAsString()
    }
}

package com.cogda

import com.google.gson.Gson
import grails.test.mixin.*
import grails.test.mixin.web.ControllerUnitTestMixin
import org.junit.*
import org.springframework.http.HttpMethod
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(GsonBaseController)
@TestMixin([ControllerUnitTestMixin])
class GsonBaseControllerSpec extends Specification {

    Gson gson = new Gson()
    static final String JSON_CONTENT_TYPE_TEXT = "text/json"
    static final String JSON_CONTENT_TYPE_APPLICATION = "application/json"

    public String getValidJSON(){
         return gson.toJson([])
    }

    /**
     * Returns an invalid JSON string
     * @return
     */
    public String getInvalidJSON(){
        return gson.toJson(Integer.MAX_VALUE) + "}}}}]]"
    }

    /**
     * sets an invalid JSON request on the
     */
    void setInvalidJsonRequest(){
        request.JSON = getInvalidJSON()
    }

    void setValidJsonRequest(){
        request.JSON = getValidJSON()
    }

    @Unroll("test requestIsJson is #valid for #contentType")
    def "requestIsJson true when application/json contentType"() {
        when:
        request.setContentType("$contentType")

        then:
        valid == controller.requestIsJson()

        where:
        valid             | contentType
        false             | 'json'
        false             | 'text'
        true              | JSON_CONTENT_TYPE_TEXT
        true              | JSON_CONTENT_TYPE_APPLICATION
    }




}

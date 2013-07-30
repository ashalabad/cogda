package com.cogda

import com.cogda.domain.security.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.adapters.GrailsDomainDeserializer
import grails.plugin.gson.adapters.GrailsDomainSerializer
import grails.plugin.gson.api.ArtefactEnhancer
import grails.plugin.gson.spring.GsonBuilderFactory
import grails.plugin.gson.support.proxy.DefaultEntityProxyHandler
import grails.plugin.gson.test.GsonUnitTestMixin
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.*
import org.springframework.http.HttpMethod
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.mop.ConfineMetaClassChanges

import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*
/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@ConfineMetaClassChanges(HttpServletResponse)
@TestFor(GsonBaseController)
@TestMixin([ControllerUnitTestMixin, GsonUnitTestMixin, DomainClassUnitTestMixin])
@Mock(User)
class GsonBaseControllerSpec extends Specification {
    GsonBuilder gsonBuilder
    Gson gson
    static final String JSON_CONTENT_TYPE_TEXT = "text/json"
    static final String JSON_CONTENT_TYPE_APPLICATION = "application/json"
    def instance
    String entityLabel = "user.label"


    void setupSpec() {
        defineBeans {
            proxyHandler DefaultEntityProxyHandler
            domainSerializer GrailsDomainSerializer, ref('grailsApplication'), ref('proxyHandler')
            domainDeserializer GrailsDomainDeserializer, ref('grailsApplication')
            gsonBuilder(GsonBuilderFactory) {
                pluginManager = ref('pluginManager')
            }
        }
    }

    void setup(){
        GrailsApplication grailsApplication = applicationContext.getBean("grailsApplication", GrailsApplication)
        gsonBuilder = applicationContext.getBean('gsonBuilder', GsonBuilder)
        def domainDeserializer = applicationContext.getBean('domainDeserializer', GrailsDomainDeserializer)
        def enhancer = new ArtefactEnhancer(grailsApplication, gsonBuilder, domainDeserializer)
        enhancer.enhanceDomains()
        enhancer.enhanceRequest()
        controller.gsonBuilder = gsonBuilder
        gson = gsonBuilder.create()

        instance = new User(username:"newusername", enabled:true, accountExpired: false, password:"password")
        def mockSpringSecurityService = mockFor(SpringSecurityService, false)
        mockSpringSecurityService.demand.encodePassword(0..1) { String username ->
            "encodedpassword"
        }
        instance.springSecurityService = mockSpringSecurityService.createMock()
    }

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

    def "respondFound sets status 200 serializes instance to JSON"(){
        given:
        assert instance.save()

        when:
        controller.respondFound(instance)

        then:
        response.status == SC_OK
        response.json.id             == instance.id
        response.json.enabled        == instance.enabled
        response.json.username       == instance.username
        response.json.accountExpired == instance.accountExpired
    }

    def "respondCreated sets status 201 serializes instance to JSON"(){
        given:
        assert instance.save()

        when:
        controller.respondCreated(instance)

        then:
        response.status == SC_CREATED
        response.getHeader(LOCATION)
        response.getHeader(LOCATION) == "/gsonBase/get/${instance.id}"
        response.json.id             == instance.id
        response.json.enabled        == instance.enabled
        response.json.username       == instance.username
        response.json.accountExpired == instance.accountExpired
    }

    def "respondUpdated sets status 200 serializes instance to JSON"(){
        given:
        assert instance.save()     // create
        instance.enabled = false
        assert instance.save()     // update

        when:
        controller.respondUpdated(instance)

        then:
        response.status == SC_OK
        response.json.id             == instance.id
        response.json.enabled        == instance.enabled
        response.json.username       == instance.username
        response.json.accountExpired == instance.accountExpired
    }

    def "getErrorStringsByField for a domain class"(){
        given:
        instance.username = null
        assert !instance.save()  // expect this to fail and add errors

        when:
        Map errorStringsByField = controller.getErrorStringsByField(instance)

        then:
        errorStringsByField
        errorStringsByField.username
    }

    def "respondUnprocessableEntity sets status 422 returns errorStrings"(){
        given:
        instance.username = null
        assert !instance.save()  // expect this to fail and add errors

        when:
        controller.respondUnprocessableEntity(instance)

        then:
        response.status == SC_UNPROCESSABLE_ENTITY
        response.json.errors
        response.json.errors.username
    }

    def "respondNotFound sets status 404 and sets a not found message object"(){
        when:
        controller.respondNotFound(entityLabel)

        then:
        response.status == SC_NOT_FOUND
        response.json.message
        response.json.message == "default.not.found.message"
    }

    def "respondConflict sets status 409 returns errorStrings"(){
        given:
        assert instance.save()

        when:
        controller.respondConflict(instance)

        then:
        response.status == SC_CONFLICT
        response.json.errors
        response.json.errors.version.toString().contains("Another user has updated this item while you were editing")
    }

    def "respondDeleted sets status 200 returns default delete message"(){
        when:
        controller.respondDeleted(entityLabel)

        then:
        response.status == SC_OK
        response.json.message
        response.json.message == "default.deleted.message"
    }

    def "respondNotDeleted sets status to 500 returns not deleted message"(){
        when:
        controller.respondNotDeleted(entityLabel)

        then:
        response.status ==  SC_INTERNAL_SERVER_ERROR
        response.json.message
        response.json.message == "default.not.deleted.message"
    }

    def "respondNotAcceptable sets status to 406 and flushes closes the outputStream"(){
        given:
        response.outputStream.write([1,2,3] as byte[]) // write something to the response.outputStream

        when:
        controller.respondNotAcceptable()

        then:
        response.status == SC_NOT_ACCEPTABLE
        response.contentLength == 0
    }

    def "respondNotAcceptable closes the outputstream"(){
        given: "a call has been made to respondNotAcceptable"
        controller.respondNotAcceptable()

        when: "attempt to write to the outputstream "
        response.outputStream.write([1,2,3] as byte[])

        then: " throws IOException "
        notThrown(IOException)  // TODO: ctk look into this behavior - I would think this should throw an error if the outputstream has been closed
    }

}

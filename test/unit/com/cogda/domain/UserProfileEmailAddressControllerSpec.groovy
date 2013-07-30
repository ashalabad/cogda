package com.cogda.domain

import com.cogda.domain.security.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.adapters.GrailsDomainDeserializer
import grails.plugin.gson.adapters.GrailsDomainSerializer
import grails.plugin.gson.api.ArtefactEnhancer
import grails.plugin.gson.spring.GsonBuilderFactory
import grails.plugin.gson.support.proxy.DefaultEntityProxyHandler
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.junit.*
import grails.test.mixin.*
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification


// static imports
import static javax.servlet.http.HttpServletResponse.*
import static org.codehaus.groovy.grails.web.servlet.HttpHeaders.*
import static grails.plugin.gson.http.HttpConstants.*

/**
 * UserProfileEmailAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(UserProfileEmailAddressController)
@Mock([UserProfileEmailAddress, UserProfile, User])
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin])
class UserProfileEmailAddressControllerSpec extends Specification {
    GsonBuilder gsonBuilder
    Gson gson

    UserProfileEmailAddress userProfileEmailAddress
    UserProfile userProfile
    User user

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

        def mockSpringSecurityService = mockFor(SpringSecurityService, false)
        mockSpringSecurityService.demand.encodePassword(0..1) { String username ->
            "encodedpassword"
        }

        user = new User()
        user.springSecurityService = mockSpringSecurityService.createMock()
        user.username = "aficionado"
        user.enabled = true
        user.accountExpired = false
        user.password = "password"
        assert user.save()

        userProfile = new UserProfile()
        userProfile.user = user
        userProfile.firstName = "Servey"
        userProfile.lastName = "Nothing"
        assert userProfile.save()
        assert userProfile.user

        userProfileEmailAddress = new UserProfileEmailAddress()
        userProfileEmailAddress.emailAddress = "test@cogda.com"
        userProfileEmailAddress.primaryEmailAddress = true
        userProfileEmailAddress.published = false
        userProfileEmailAddress.userProfile = userProfile
        assert userProfileEmailAddress.save()
    }


    def "save a new UserProfileEmailAddress"() {
        given: "a new valid entity in json format "
        request.JSON = getNewEntityJson()

        when: " save is called "
        controller.save()

        then: " the entity is created and returned in json format"
        response.status == SC_CREATED
        response.getHeader(LOCATION)
        response.getHeader(LOCATION) == "/userProfileEmailAddress/get/${response.json.id}"
        response.json.id
        response.json.userProfile.id        == userProfile.id
        response.json.emailAddress          == newEntityMap.emailAddress
        response.json.primaryEmailAddress   == newEntityMap.primaryEmailAddress
        response.json.published             == newEntityMap.published

        assert UserProfileEmailAddress.count() == 2
    }

    def "save a new UserProfileEmailAddress with errors"() {
        given: "a new INVALID entity in json format "
        request.JSON = newInvalidEntityJson

        when: " save is called "
        controller.save()

        then: " the entity is NOT created and status set to 422 and errors returned in json format"
        response.status == SC_UNPROCESSABLE_ENTITY
        response.json.errors
        response.json.errors.emailAddress
        println response.getContentAsString()
        assert UserProfileEmailAddress.count() == 1
    }

    def "get an existing UserProfileEmailAddress"(){
        given: "an id of an existing UserProfileEmailAddress"
        params.id = userProfileEmailAddress.id

        when: "get is called"
        controller.get()

        then: "status 200 and domain class serialized correctly"
        response.status == SC_OK
        response.json.id                   == userProfileEmailAddress.id
        response.json.emailAddress         == userProfileEmailAddress.emailAddress
        response.json.primaryEmailAddress  == userProfileEmailAddress.primaryEmailAddress
        response.json.published            == userProfileEmailAddress.published
        response.json.userProfile.id       == userProfileEmailAddress.userProfile.id
    }

    def "get a UserProfileEmailAddress that does not exist"(){
        given: "an id that does not exist"
        params.id = Integer.MAX_VALUE

        when: "get is called"
        controller.get()

        then: "404'd and default not found message returned"
        response.status == SC_NOT_FOUND
        response.json.message
        response.json.message == "default.not.found.message"
    }

    def "update a valid existing UserProfileEmailAddress"(){
        given: "valid json userProfileEmailAddress"
        request.json = getUpdatedEntityMapJson(userProfileEmailAddress.id, "updated@cogda.com")

        when: "update is called"
        controller.update()

        then: "status 200 and only elements that were updated have been changed "
        response.status                    == SC_OK
        response.json.id                   == userProfileEmailAddress.id
        response.json.emailAddress         == "updated@cogda.com"
        response.json.primaryEmailAddress  == userProfileEmailAddress.primaryEmailAddress
        response.json.published            == userProfileEmailAddress.published
        response.json.userProfile.id       == userProfileEmailAddress.userProfile.id
    }

    def "update a UserProfileEmailAddress with errors"() {
        given: "an INVALID entity in json format "
        request.JSON = getUpdatedEntityMapJson(userProfileEmailAddress.id, "notanemailaddress")

        when: " save is called "
        controller.save()

        then: " the entity is NOT updated and status set to 422 and errors returned in json format"
        response.status == SC_UNPROCESSABLE_ENTITY
        response.json.errors
        response.json.errors.emailAddress
        println response.getContentAsString()
        UserProfileEmailAddress.get(userProfileEmailAddress.id).emailAddress == userProfileEmailAddress.emailAddress

    }

    def "delete an existing UserProfileEmailAddress"(){
        given: "an existing id"
        params.id = userProfileEmailAddress.id

        when: " delete is called "
        controller.delete()

        then:
        response.status == SC_OK
        response.json.message
        response.json.message == "default.deleted.message"


    }

    def "delete a UserProfileEmailAddress that does not exist"(){
        given: " an id that does not exist "
        params.id = Long.MAX_VALUE

        when: " delete is called "
        controller.delete()

        then:
        response.status == SC_NOT_FOUND
        response.json.message
        response.json.message == "default.not.found.message"
    }

//    def "delete a UserProfileEmailAddress results in DataIntegrityViolationException "(){
//        given: " an id "
//        params.id = userProfileEmailAddress.id
//        and: " delete method throws DataIntegrityViolationException "
//        UserProfileEmailAddress.metaClass.'static'.delete = { boolean flush -> throw new DataIntegrityViolationException("mocking the delete method") }
//        UserProfileEmailAddress.metaClass.delete = { boolean flush -> throw new DataIntegrityViolationException("mocking the delete method") }
//
//        when: " delete is called "
//        controller.delete()
//
//        then:
//        response.status ==  SC_INTERNAL_SERVER_ERROR
//        response.json.message
//        response.json.message == "default.not.deleted.message"
//    }

    def "update a non existing UserProfileEmailAddress"(){
        given: "non existing userProfileEmailAddress"
        request.json = getUpdatedEntityMapJson(Long.MAX_VALUE, "updated@cogda.com")

        when: "update is called"
        controller.update()

        then: "404'd and default not found message returned"
        response.status == SC_NOT_FOUND
        response.json.message
        response.json.message == "default.not.found.message"
    }

    def "update a UserProfileEmailAddress with concurrency issue"(){
        given: "a couple updates have been made"
        userProfileEmailAddress.primaryEmailAddress = false
        assert userProfileEmailAddress.save(flush:true)
        userProfileEmailAddress.emailAddress = "no@cogda.com"
        assert userProfileEmailAddress.save(flush:true)
        assert userProfileEmailAddress.version > 0

        and: "version passed in is less than the current version"
        request.json = getUpdatedEntityMapJson(userProfileEmailAddress.id, "updated@cogda.com", userProfileEmailAddress.version - 1)

        when: "update is called"
        controller.update()

        then: "status 409 and errors are serialized to JSON"
        response.status == SC_CONFLICT
        response.json.errors
        response.json.errors.version.toString().contains("Another user has updated this item while you were editing")
    }

    def "formAdd returns the right model"(){
        given:
        request.json = newEntityJson
        and: "mocked view template "
        views['/userProfileEmailAddress/_userProfileEmailAddressForm.gsp'] = 'mock contents'
        when:
        controller.formAdd()

        then:
        response.status == SC_OK
        response.text == "mock contents"  // just make sure that it looks for _userProfileEmailAddressForm
    }

    def "form returns the right model"(){
        given:
        params.id = userProfileEmailAddress.id
        and: "mocked view template"
        views['/userProfileEmailAddress/_userProfileEmailAddressForm.gsp'] = 'mock contents'

        when:
        controller.form()

        then:
        response.status == SC_OK
        response.text == "mock contents"  // just make sure that it looks for _userProfileEmailAddressForm
    }

    def "respondUserProfileNotFound returns a not found message "(){
        when:
        controller.respondUserProfileNotFound(1)

        then:
        response.status ==  SC_NOT_FOUND
        response.json.message == 'default.not.found.message'
    }

    private Map getNewInvalidEntityMap(){
        Map invalidEntityMap = newEntityMap
        invalidEntityMap.emailAddress = "nullnull.com"
        invalidEntityMap
    }

    private String getUpdatedEntityMapJson(id, emailAddress, version = null){
        return gson.toJson(getUpdatedEntityMap(id, emailAddress, version))
    }

    private Map getUpdatedEntityMap(id, emailAddress, version = null){
        Map map = [
                userProfile: [id:userProfile.id],
                emailAddress: emailAddress,
                primaryEmailAddress: false,
                published: true,
                id: id
        ]
        if(version){
            map.version = version
        }
        return map
    }

    /**
     * Creates a new Entity Map
     * @return
     */
    private Map getNewEntityMap(){
        Map map = [
                userProfile: [id:userProfile.id],
                emailAddress: "new@cogda.com",
                primaryEmailAddress: false,
                published: true
        ]
        return map
    }

    /**
     * Returns a new Json entity for the domain class under test
     * @return String
     */
    private String getNewEntityJson(){
        return gson.toJson(newEntityMap)
    }
    /**
     * Returns a new invalid Json entity for the domain class under test
     * @return String
     */
    private String getNewInvalidEntityJson(){
        return gson.toJson(newInvalidEntityMap)
    }

}

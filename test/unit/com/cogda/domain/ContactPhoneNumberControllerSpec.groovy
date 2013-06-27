package com.cogda.domain

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.adapters.GrailsDomainDeserializer
import grails.plugin.gson.adapters.GrailsDomainSerializer
import grails.plugin.gson.api.ArtefactEnhancer
import grails.plugin.gson.spring.GsonBuilderFactory
import grails.plugin.gson.support.proxy.DefaultEntityProxyHandler
import grails.plugin.gson.test.GsonUnitTestMixin
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

import static javax.servlet.http.HttpServletResponse.*
import javax.servlet.http.HttpServletResponse
/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@ConfineMetaClassChanges(HttpServletResponse)
@TestFor(ContactPhoneNumberController)
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin, GsonUnitTestMixin])
@Mock([Contact, ContactAddress, ContactPhoneNumber, ContactEmailAddress, Address, UserProfile])
class ContactPhoneNumberControllerSpec extends Specification {
    Gson gson
    GsonBuilder gsonBuilder
    Contact contact
    ContactPhoneNumber contactPhoneNumber

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
//        controller.gsonBuilder = gsonBuilder

        gson = gsonBuilder.create()

        contact = ContactControllerSpec.createValidContact()
        contactPhoneNumber = contact.contactPhoneNumbers.first()
    }

    void "save creates a new child of existing contact"() {
        given:
        Integer beforeContactPhoneNumberCount = ContactPhoneNumber.count()
        ContactPhoneNumber newContactPhoneNumber = new ContactPhoneNumber()
        newContactPhoneNumber.phoneNumber = "brandspankingnew@cogda.com"
        newContactPhoneNumber.primaryPhoneNumber = true
        // only serialize the contact id - not the full contact
        newContactPhoneNumber.contact = new Contact()
        newContactPhoneNumber.contact.id = contact.id

        String jsonString = gson.toJson(newContactPhoneNumber)
        println "Request JSON: " + jsonString
        request.json = jsonString
        request.setContentType("application/json")

        when:
        controller.save()

        then:
        assert response.status == SC_CREATED
        println "Response JSON: " + response.getContentAsString()

        // check that the response JSON indicates the insert was successful
        assert response.json.id, "there should be a new identifier for the saved ContactPhoneNumber"
        assert response.json.phoneNumber == newContactPhoneNumber.phoneNumber
        assert response.json.primaryPhoneNumber == newContactPhoneNumber.primaryPhoneNumber
        assert response.json.contact
        assert response.json.contact.id == contact.id // created contactPhoneNumber for the right contact
        assertNull response.json.contact.contactAddresses
        assertNull response.json.contact.contactEmailAddresses
        assertNull response.json.contact.contactPhoneNumbers

        Integer afterContactPhoneNumberCount = ContactPhoneNumber.count()
        assert beforeContactPhoneNumberCount != afterContactPhoneNumberCount
        assert beforeContactPhoneNumberCount < afterContactPhoneNumberCount
        contact.refresh()

        // check thta the save (insert) in the database was successful
        ContactPhoneNumber addedContactPhoneNumber = contact.contactPhoneNumbers.find { it.phoneNumber == newContactPhoneNumber.phoneNumber }
        assert response.json.id == addedContactPhoneNumber.id
        assert addedContactPhoneNumber.phoneNumber == newContactPhoneNumber.phoneNumber
        assert addedContactPhoneNumber.primaryPhoneNumber == newContactPhoneNumber.primaryPhoneNumber
    }


    void "update as child with existing contact"(){
        given:
        Integer beforeContactPhoneNumberCount = ContactPhoneNumber.count()
        ContactPhoneNumber updateContactPhoneNumber = new ContactPhoneNumber()
        updateContactPhoneNumber.phoneNumber = "graduatedandupgraded@cogda.com"
        updateContactPhoneNumber.primaryPhoneNumber = Boolean.TRUE
        updateContactPhoneNumber.id = contact.contactPhoneNumbers.first().id
        // only serialize the contact id - not the full contact
//        updateContactPhoneNumber.contact = new Contact()
//        updateContactPhoneNumber.contact.id = contact.id

        String jsonString = gson.toJson(updateContactPhoneNumber)
        println "Request JSON: " + jsonString
        request.json = jsonString
        request.setContentType("application/json")

        when:
        controller.update()

        then:

        assert response.status == SC_OK

        println "Response JSON: " + response.getContentAsString()

        // check that the response JSON indicates the update was successful
        assert response.json.id == updateContactPhoneNumber.id, "there should be a new identifier for the saved ContactPhoneNumber"
        assert response.json.phoneNumber == updateContactPhoneNumber.phoneNumber
        assert response.json.primaryPhoneNumber == updateContactPhoneNumber.primaryPhoneNumber
        assert response.json.contact
        assert response.json.contact.id == contact.id // updated the correct contact's contactPhoneNumber
        assertNull response.json.contact.contactPhoneNumbers
        assertNull response.json.contact.contactEmailAddresses
        assertNull response.json.contact.contactAddresses

        // check update in the database was successful
        Integer afterContactPhoneNumberCount = ContactPhoneNumber.count()
        assert beforeContactPhoneNumberCount == afterContactPhoneNumberCount
        contactPhoneNumber.refresh()
        assert contactPhoneNumber.phoneNumber == updateContactPhoneNumber.phoneNumber
        assert contactPhoneNumber.primaryPhoneNumber == updateContactPhoneNumber.primaryPhoneNumber
    }

}

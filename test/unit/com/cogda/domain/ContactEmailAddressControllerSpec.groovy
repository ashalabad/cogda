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
@TestFor(ContactEmailAddressController)
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin, GsonUnitTestMixin])
@Mock([Contact, ContactAddress, ContactEmailAddress, ContactPhoneNumber, Address, UserProfile])
class ContactEmailAddressControllerSpec extends Specification {
    Gson gson
    GsonBuilder gsonBuilder
    Contact contact
    ContactEmailAddress contactEmailAddress

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
        contactEmailAddress = contact.contactEmailAddresses.first()
    }

    void "save creates a new child of existing contact"() {
        given:
        Integer beforeContactEmailAddressCount = ContactEmailAddress.count()
        ContactEmailAddress newContactEmailAddress = new ContactEmailAddress()
        newContactEmailAddress.emailAddress = "brandspankingnew@cogda.com"
        newContactEmailAddress.primaryEmailAddress = true
        // only serialize the contact id - not the full contact
        newContactEmailAddress.contact = new Contact()
        newContactEmailAddress.contact.id = contact.id

        String jsonString = gson.toJson(newContactEmailAddress)
        println "Request JSON: " + jsonString
        request.json = jsonString
        request.setContentType("application/json")

        when:
        controller.save()

        then:
        assert response.status == SC_CREATED
        println "Response JSON: " + response.getContentAsString()

        // check that the response JSON indicates the insert was successful
        assert response.json.id, "there should be a new identifier for the saved ContactEmailAddress"
        assert response.json.emailAddress == newContactEmailAddress.emailAddress
        assert response.json.primaryEmailAddress == newContactEmailAddress.primaryEmailAddress
        assert response.json.contact
        assert response.json.contact.id == contact.id // created contactEmailAddress for the right contact
        assertNull response.json.contact.contactAddresses
        assertNull response.json.contact.contactPhoneNumbers
        assertNull response.json.contact.contactEmailAddresses

        Integer afterContactEmailAddressCount = ContactEmailAddress.count()
        assert beforeContactEmailAddressCount != afterContactEmailAddressCount
        assert beforeContactEmailAddressCount < afterContactEmailAddressCount
        contact.refresh()

        // check thta the save (insert) in the database was successful
        ContactEmailAddress addedContactEmailAddress = contact.contactEmailAddresses.find { it.emailAddress == newContactEmailAddress.emailAddress }
        assert response.json.id == addedContactEmailAddress.id
        assert addedContactEmailAddress.emailAddress == newContactEmailAddress.emailAddress
        assert addedContactEmailAddress.primaryEmailAddress == newContactEmailAddress.primaryEmailAddress
    }


    void "update as child with existing contact"(){
        given:
        Integer beforeContactEmailAddressCount = ContactEmailAddress.count()
        ContactEmailAddress updateContactEmailAddress = new ContactEmailAddress()
        updateContactEmailAddress.emailAddress = "graduatedandupgraded@cogda.com"
        updateContactEmailAddress.primaryEmailAddress = Boolean.TRUE
        updateContactEmailAddress.id = contact.contactEmailAddresses.first().id
        // only serialize the contact id - not the full contact
//        updateContactEmailAddress.contact = new Contact()
//        updateContactEmailAddress.contact.id = contact.id

        String jsonString = gson.toJson(updateContactEmailAddress)
        println "Request JSON: " + jsonString
        request.json = jsonString
        request.setContentType("application/json")

        when:
        controller.update()

        then:

        assert response.status == SC_OK

        println "Response JSON: " + response.getContentAsString()

        // check that the response JSON indicates the update was successful
        assert response.json.id == updateContactEmailAddress.id, "there should be a new identifier for the saved ContactEmailAddress"
        assert response.json.emailAddress == updateContactEmailAddress.emailAddress
        assert response.json.primaryEmailAddress == updateContactEmailAddress.primaryEmailAddress
        assert response.json.contact
        assert response.json.contact.id == contact.id // updated the correct contact's contactEmailAddress
        assertNull response.json.contact.contactEmailAddresses
        assertNull response.json.contact.contactPhoneNumbers
        assertNull response.json.contact.contactEmailAddresses

        // check update in the database was successful
        Integer afterContactEmailAddressCount = ContactEmailAddress.count()
        assert beforeContactEmailAddressCount == afterContactEmailAddressCount
        contactEmailAddress.refresh()
        assert contactEmailAddress.emailAddress == updateContactEmailAddress.emailAddress
        assert contactEmailAddress.primaryEmailAddress == updateContactEmailAddress.primaryEmailAddress
    }

}

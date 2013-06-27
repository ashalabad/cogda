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
@TestFor(ContactAddressController)
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin, GsonUnitTestMixin])
@Mock([Contact, ContactAddress, ContactEmailAddress, ContactPhoneNumber, Address, UserProfile])
class ContactAddressControllerSpec extends Specification {
    Gson gson
    GsonBuilder gsonBuilder
    Contact contact
    ContactAddress contactAddress

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
        contactAddress = contact.contactAddresses.first()
    }

    void "save creates a new child of existing contact"() {
        given:
        Integer beforeContactAddressCount = ContactAddress.count()
        ContactAddress newContactAddress = new ContactAddress()
        Address newAddress = new Address()
        newAddress.addressOne = "Room 17"
        newAddress.addressTwo = "Floor Two"
        newAddress.addressThree = "2 Press Place"
        newAddress.city = "Athens"
        newAddress.state = "GA"
        newAddress.county = "Clarke"
        newAddress.country = "usa"
        newContactAddress.address = newAddress
        newContactAddress.addressType = "work"
        // only serialize the contact id - not the full contact
        newContactAddress.contact = new Contact()
        newContactAddress.contact.id = contact.id

        String jsonString = gson.toJson(newContactAddress)
        println "Request JSON: " + jsonString
        request.json = jsonString
        request.setContentType("application/json")

        when:
        controller.save()

        then:
        assert response.status == SC_CREATED
        println "Response JSON: " + response.getContentAsString()

        // check that the response JSON indicates the insert was successful
        assert response.json.id, "there should be a new identifier for the saved ContactAddress"
        assert response.json.address.addressOne == newAddress.addressOne
        assert response.json.address.addressTwo == newAddress.addressTwo
        assert response.json.address.addressThree == newAddress.addressThree
        assert response.json.address.city == newAddress.city
        assert response.json.address.state == newAddress.state
        assert response.json.address.county == newAddress.county
        assert response.json.address.country == newAddress.country
        assert response.json.addressType == newContactAddress.addressType
        assert response.json.contact
        assert response.json.contact.id == contact.id // created contactAddress for the right contact
        assertNull response.json.contact.contactAddresses
        assertNull response.json.contact.contactPhoneNumbers
        assertNull response.json.contact.contactEmailAddresses

        Integer afterContactAddressCount = ContactAddress.count()
        assert beforeContactAddressCount != afterContactAddressCount
        assert beforeContactAddressCount < afterContactAddressCount
        contact.refresh()

        // check thta the save (insert) in the database was successful
        ContactAddress addedContactAddress = contact.contactAddresses.find { it.address.addressOne == newContactAddress.address.addressOne }
        assert response.json.id == addedContactAddress.id
        assert addedContactAddress.address.addressOne == newAddress.addressOne
        assert addedContactAddress.address.addressTwo == newAddress.addressTwo
        assert addedContactAddress.address.addressThree == newAddress.addressThree
        assert addedContactAddress.address.city == newAddress.city
        assert addedContactAddress.address.state == newAddress.state
        assert addedContactAddress.address.county == newAddress.county
        assert addedContactAddress.address.country == newAddress.country
        assert addedContactAddress.addressType == newContactAddress.addressType

    }


    void "update as child with existing contact"(){
        given:
        Integer beforeContactAddressCount = ContactAddress.count()
        ContactAddress updateContactAddress = new ContactAddress()
        Address updateAddress = new Address()
        updateAddress.addressOne = "Change Room 17"
        updateAddress.addressTwo = "Change Floor Two"
        updateAddress.addressThree = "Change 2 Press Place"
        updateAddress.city = "ChangeAthens"
        updateAddress.state = "FL"
        updateAddress.county = "Largo"
        updateAddress.country = "ger"
        updateContactAddress.address = updateAddress
        updateContactAddress.addressType = "home"
        updateContactAddress.id = contact.contactAddresses.first().id
        // only serialize the contact id - not the full contact
        updateContactAddress.contact = new Contact()
        updateContactAddress.contact.id = contact.id

        String jsonString = gson.toJson(updateContactAddress)
        println "Request JSON: " + jsonString
        request.json = jsonString
        request.setContentType("application/json")

        when:
        controller.update()

        then:

        assert response.status == SC_OK

        println "Response JSON: " + response.getContentAsString()

        // check that the response JSON indicates the update was successful
        assert response.json.id == updateContactAddress.id
        assert response.json.address.addressOne == updateAddress.addressOne
        assert response.json.address.addressTwo == updateAddress.addressTwo
        assert response.json.address.addressThree == updateAddress.addressThree
        assert response.json.address.city == updateAddress.city
        assert response.json.address.state == updateAddress.state
        assert response.json.address.county == updateAddress.county
        assert response.json.address.country == updateAddress.country
        assert response.json.addressType == updateContactAddress.addressType
        assert response.json.contact
        assert response.json.contact.id == contact.id // updated the correct contact's contactAddress
        assertNull response.json.contact.contactAddresses
        assertNull response.json.contact.contactPhoneNumbers
        assertNull response.json.contact.contactEmailAddresses

        // check update in the database was successful
        Integer afterContactAddressCount = ContactAddress.count()
        assert beforeContactAddressCount == afterContactAddressCount
        contactAddress.refresh()
        assert contactAddress.address.addressOne == updateAddress.addressOne
        assert contactAddress.address.addressTwo == updateAddress.addressTwo
        assert contactAddress.address.addressThree == updateAddress.addressThree
        assert contactAddress.address.city == updateAddress.city
        assert contactAddress.address.state == updateAddress.state
        assert contactAddress.address.county == updateAddress.county
        assert contactAddress.address.country == updateAddress.country
        assert contactAddress.addressType == updateContactAddress.addressType

    }

}

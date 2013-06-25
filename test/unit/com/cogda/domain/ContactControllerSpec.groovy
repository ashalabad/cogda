package com.cogda.domain

import com.cogda.common.GenderEnum
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.adapters.GrailsDomainDeserializer
import grails.plugin.gson.adapters.GrailsDomainSerializer
import grails.plugin.gson.api.ArtefactEnhancer
import grails.plugin.gson.spring.GsonBuilderFactory
import grails.plugin.gson.support.proxy.DefaultEntityProxyHandler
import org.codehaus.groovy.grails.commons.GrailsApplication
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges
import sun.org.mozilla.javascript.internal.json.JsonParser

import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.*

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

@ConfineMetaClassChanges(HttpServletResponse)
@TestFor(ContactController)
@Mock([Contact, ContactAddress, ContactEmailAddress, ContactPhoneNumber])
class ContactControllerSpec extends Specification {

    Contact contact
    ContactEmailAddress contactEmailAddress
    ContactPhoneNumber contactPhoneNumber
    ContactAddress contactAddress
    Address address
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

        contact = new Contact( firstName: "First Name", lastName: "Last Name", gender:GenderEnum.MALE, tenantId:1).save(failOnError: true)
        contactEmailAddress = new ContactEmailAddress(contact:contact, emailAddress: "chris@cogda.com", primaryEmailAddress: true, tenantId:1)
        contactEmailAddress.save(failOnError: true)
        contactPhoneNumber = new ContactPhoneNumber(phoneNumber: "(706) 867-5309", primaryPhoneNumber: true, contact:contact)
        contactPhoneNumber.save(failOnError: true)
        contactAddress = new ContactAddress()

//        address = new Address(addressOne: "1 Street", addressTwo: "2 Street", addressThree: "3 Street", city:"Athens", state:"GA", zipcode: "30601", country: "usa")
        contactAddress.primaryAddress = true
//        contactAddress.address = address
        contactAddress.addressType = "home"
        contactAddress.address = new Address()
        contactAddress.contact = contact
        contactAddress.save(failOnError: true)
        contact.addToContactPhoneNumbers(contactPhoneNumber)
        contact.addToContactEmailAddresses(contactEmailAddress)
        contact.addToContactAddresses(contactAddress)
    }

    def "can get and serialize Contact"(){
        given:
        GsonBuilder gsonBuilder = applicationContext.getBean('gsonBuilder', GsonBuilder)
        controller.gsonBuilder = gsonBuilder
        params.id = contact.id
        request.setContentType("application/json")

        when:
        controller.get()


        then:
        println response.getContentAsString()
        assert response.status == SC_OK

        Gson gson = gsonBuilder.create()
        Contact deserializedContact = gson.fromJson(response.getContentAsString(), Contact)
        assert deserializedContact.id == contact.id
        assert deserializedContact.firstName == contact.firstName
        assert deserializedContact.lastName == contact.lastName
        assert deserializedContact.gender == GenderEnum.MALE
        assert deserializedContact.contactEmailAddresses.size() == 1
        assert deserializedContact.contactPhoneNumbers.size() == 1
        assert deserializedContact.contactAddresses.size() == 1
    }


    def "get results in not found"(){
        given:
        GsonBuilder gsonBuilder = applicationContext.getBean('gsonBuilder', GsonBuilder)
        controller.gsonBuilder = gsonBuilder
        params.id = 999999999
        request.setContentType("application/json")

        when:
        controller.get()

        then:
        assert response.getContentAsString() == "{\"message\":\"default.not.found.message\"}"
        assert response.status == SC_NOT_FOUND
    }

}
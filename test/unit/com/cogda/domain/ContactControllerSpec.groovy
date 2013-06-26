package com.cogda.domain

import com.cogda.common.GenderEnum
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import grails.plugin.gson.adapters.GrailsDomainDeserializer
import grails.plugin.gson.adapters.GrailsDomainSerializer
import grails.plugin.gson.api.ArtefactEnhancer
import grails.plugin.gson.spring.GsonBuilderFactory
import grails.plugin.gson.support.proxy.DefaultEntityProxyHandler
import grails.plugin.gson.test.GsonUnitTestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges


import javax.servlet.http.HttpServletResponse

import static javax.servlet.http.HttpServletResponse.*

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

@ConfineMetaClassChanges(HttpServletResponse)
@TestFor(ContactController)
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin, GsonUnitTestMixin])
@Mock([Contact, ContactAddress, ContactEmailAddress, ContactPhoneNumber, Address, UserProfile])
class ContactControllerSpec extends Specification{

    Contact contact
    ContactEmailAddress contactEmailAddress
    ContactPhoneNumber contactPhoneNumber
    ContactAddress contactAddress

    Contact contactWithTwoEmailAddresses
    Address address
    Gson gson
    GsonBuilder gsonBuilder

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
        contact = new Contact( firstName: "First Name", lastName: "Last Name", gender:GenderEnum.MALE, tenantId:1)
        contact.save(failOnError: true)
        contactEmailAddress = new ContactEmailAddress(contact:contact, emailAddress: "chris@cogda.com", primaryEmailAddress: true, tenantId:1)
        contactEmailAddress.save(failOnError: true)
        contactPhoneNumber = new ContactPhoneNumber(phoneNumber: "(706) 867-5309", primaryPhoneNumber: true, contact:contact)
        contactPhoneNumber.save(failOnError: true)
        contactAddress = new ContactAddress()

        address = new Address(addressOne: "1 Street", addressTwo: "2 Street", addressThree: "3 Street", city:"Athens", state:"GA", zipcode: "30601", country: "usa")
        contactAddress.primaryAddress = true
        contactAddress.address = address
        contactAddress.addressType = "home"
//        contactAddress.address = new Address()
        contactAddress.contact = contact
        contactAddress.save(failOnError: true)
        contact.addToContactPhoneNumbers(contactPhoneNumber)
        contact.addToContactEmailAddresses(contactEmailAddress)
        contact.addToContactAddresses(contactAddress)


        contactWithTwoEmailAddresses = new Contact(firstName: "Two Email Adresses First Name", lastName: "Two Email Adresses Last Name", gender:GenderEnum.FEMALE, tenantId:1)
        contactWithTwoEmailAddresses.addToContactEmailAddresses(new ContactEmailAddress(contact:contactWithTwoEmailAddresses, emailAddress: "123456789@cogda.com", primaryEmailAddress: true, tenantId:1))
        contactWithTwoEmailAddresses.addToContactEmailAddresses(new ContactEmailAddress(contact:contactWithTwoEmailAddresses, emailAddress: "987654321@cogda.com", primaryEmailAddress: false, tenantId:1))
        contactWithTwoEmailAddresses.save(failOnError:true)

    }

    def "can get and serialize Contact"(){
        given:
        params.id = contact.id
        request.setContentType("application/json")

        when:
        controller.get()

        then:
        assert response.status == SC_OK
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
        params.id = 999999999
        request.setContentType("application/json")

        when:
        controller.get()

        then:
        assert response.getContentAsString() == "{\"message\":\"default.not.found.message\"}"
        assert response.status == SC_NOT_FOUND
    }

    def "list results using Integer max"(){
        given:
        request.setContentType("application/json")
        Contact ncontact = new Contact( firstName: "Another First Name", lastName: "Another Last Name", gender:GenderEnum.FEMALE, tenantId:1)
        ncontact.save(failOnError: true)


        when:
        controller.jlist(1.toInteger())

        then:
        assert response.status == SC_OK
        assert !response.contentAsString.contains(ncontact.lastName)
        assert response.contentAsString.contains(contact.lastName)
    }

    def "dataTables list"(){
        given:
        request.setContentType("application/json")
        Contact ncontact = new Contact( firstName: "Another First Name", lastName: "Another Last Name", gender:GenderEnum.FEMALE, tenantId:1)
        ncontact.save(failOnError: true)

        when:
        controller.list()

        then:
        assert response.contentAsString.contains(/{"aaData":[/)

    }

    def "save successfully saves"(){
        given:

        Contact contactInstance = createFullContact()
        request.json = gson.toJson(contactInstance)
        request.setContentType("application/json")

        when:
        controller.save()

        then:
        assert response.status == SC_CREATED
        assert response.json.id, "No id found - must not have created the Contact"
        Contact createdContact = Contact.get(response.json.id)
        assert createdContact != null
        assert createdContact.id != null
        assert createdContact.firstName == contactInstance.firstName
        assert createdContact.middleName == contactInstance.middleName
        assert createdContact.lastName == contactInstance.lastName
        assert createdContact.gender == contactInstance.gender
        assert createdContact.initials == contactInstance.initials
        assert createdContact.title == contactInstance.title
        assert createdContact.jobTitle == contactInstance.jobTitle
        assert createdContact.website == contactInstance.website
        assert createdContact.contactEmailAddresses != null

        assert createdContact.contactEmailAddresses.size() == contactInstance.contactEmailAddresses.size()
        createdContact.contactEmailAddresses.eachWithIndex { ContactEmailAddress createdContactEmailAddress, int i ->
            ContactEmailAddress compareContactEmailAddress = contactInstance.contactEmailAddresses.find {
                it.emailAddress.equals(createdContactEmailAddress.emailAddress)
            }
            assertNotNull "couldn't find a contact email address that matches", createdContactEmailAddress
            assert createdContactEmailAddress.emailAddress == compareContactEmailAddress.emailAddress
            assert createdContactEmailAddress.primaryEmailAddress == compareContactEmailAddress.primaryEmailAddress
        }

        assert createdContact.contactAddresses.size() == contactInstance.contactAddresses.size()
        createdContact.contactAddresses.eachWithIndex { ContactAddress createdContactAddress, int i ->
            ContactAddress compareContactAddress = contactInstance.contactAddresses.find {
                it.address.addressOne.equals(createdContactAddress.address.addressOne)
            }
            assertNotNull "couldn't find a contact address that matches", createdContactAddress

            assert createdContactAddress.addressType == compareContactAddress.addressType
            assert createdContactAddress.primaryAddress == compareContactAddress.primaryAddress
            assert createdContactAddress.address.addressOne == compareContactAddress.address.addressOne
            assert createdContactAddress.address.addressTwo == compareContactAddress.address.addressTwo
            assert createdContactAddress.address.addressThree == compareContactAddress.address.addressThree
            assert createdContactAddress.address.city == compareContactAddress.address.city
            assert createdContactAddress.address.state == compareContactAddress.address.state
            assert createdContactAddress.address.zipcode == compareContactAddress.address.zipcode
            assert createdContactAddress.address.country == compareContactAddress.address.country
            assert createdContactAddress.address.county == compareContactAddress.address.county
        }

        assert createdContact.contactPhoneNumbers.size() == contactInstance.contactPhoneNumbers.size()
        createdContact.contactPhoneNumbers.eachWithIndex { ContactPhoneNumber createdContactPhoneNumber, int i ->
            ContactPhoneNumber compareContactPhoneNumber = contactInstance.contactPhoneNumbers.find {
                it.phoneNumber.equals(createdContactPhoneNumber.phoneNumber)
            }
            assertNotNull "couldn't find a contact phone number that matches", createdContactPhoneNumber
            assert createdContactPhoneNumber.phoneNumber == compareContactPhoneNumber.phoneNumber
            assert createdContactPhoneNumber.primaryPhoneNumber == compareContactPhoneNumber.primaryPhoneNumber
        }

    }

    def "save only a contact and no children"(){
        given:

        Contact contactInstance = createFullContact()
        contactInstance.contactEmailAddresses.clear()
        contactInstance.contactPhoneNumbers.clear()
        contactInstance.contactAddresses.clear()

        request.json = gson.toJson(contactInstance)
        request.setContentType("application/json")

        when:
        controller.save()

        then:
        assert response.status == SC_CREATED
        assert response.json.id, "No id found - must not have created the Contact"
        Contact createdContact = Contact.get(response.json.id)
        assert createdContact != null
        assert createdContact.id != null
        assert createdContact.firstName == contactInstance.firstName
        assert createdContact.middleName == contactInstance.middleName
        assert createdContact.lastName == contactInstance.lastName
        assert createdContact.gender == contactInstance.gender
        assert createdContact.initials == contactInstance.initials
        assert createdContact.title == contactInstance.title
        assert createdContact.jobTitle == contactInstance.jobTitle
        assert createdContact.website == contactInstance.website
        assert createdContact.contactAddresses.size() == 0
        assert createdContact.contactEmailAddresses.size() == 0
        assert createdContact.contactPhoneNumbers.size() == 0
    }

    def "update a contact no children"(){
        given:
        contact.companyName = "Sanford & Sons"
        contact.firstName = "Fred"
        contact.lastName = "Sanford"
        request.json = gson.toJson(contact)
        request.setContentType("application/json")
        def addressSize = contact.contactAddresses.size()
        def phoneNumberSize = contact.contactPhoneNumbers.size()
        def emailAddressSize = contact.contactEmailAddresses.size()


        when:
        controller.update()

        then:
        assert response.status == SC_OK
        assert contact.contactAddresses.size() == addressSize
        assert contact.contactEmailAddresses.size() == emailAddressSize
        assert contact.contactPhoneNumbers.size() == phoneNumberSize

    }

    def "update a contact new children"(){
        given:
        contact.companyName = "Sanford & Sons"
        contact.firstName = "Fred"
        contact.lastName = "Sanford"
        def emailAddressSize = contact.contactEmailAddresses.size()

        ContactEmailAddress newContactEmailAddress = new ContactEmailAddress(emailAddress:"jumanji@cogda.com", contact:contact)
        contact.contactEmailAddresses.add(newContactEmailAddress)
        def addressSize = contact.contactAddresses.size()
        def phoneNumberSize = contact.contactPhoneNumbers.size()

        request.json = gson.toJson(contact)
        request.setContentType("application/json")

        when:
        controller.update()

        then:
        assert response.status == SC_OK
        assert response.json.id, "No id found"
        Contact updatedContact = Contact.get(response.json.id)

        assert updatedContact.contactAddresses.size() == addressSize
        assert updatedContact.contactEmailAddresses.size() != emailAddressSize
        assert updatedContact.contactPhoneNumbers.size() == phoneNumberSize
        assert updatedContact.contactEmailAddresses.find { it.emailAddress.equals(newContactEmailAddress.emailAddress) }

    }

    def "update a contact with no changes to Contact but has changes in one of the two children email addresses"(){
        given:
        def emailAddressSize = contact.contactEmailAddresses.size()  // before the addition of a new ContactEmailAddress

        ContactEmailAddress newContactEmailAddress = new ContactEmailAddress(emailAddress:"jumanji@cogda.com", contact:contact)
        contact.addToContactEmailAddresses(newContactEmailAddress)

        ContactEmailAddress updatedContactEmailAddress = contact.contactEmailAddresses.first()
        assert updatedContactEmailAddress, "The updatedContactEmailAddress was not found"
        updatedContactEmailAddress.emailAddress = "updatingyourchildren@cogda.com"

        def addressSize = contact.contactAddresses.size()
        def phoneNumberSize = contact.contactPhoneNumbers.size()

        request.json = gson.toJson(contact)
        println request.getJSON().toString()
        request.setContentType("application/json")

        when:
        controller.update()

        then:
        assert response.status == SC_OK
        assert response.json.id, "No id found"
        Contact updatedContact = Contact.get(response.json.id)

        assert updatedContact.contactAddresses.size() == addressSize
        assert updatedContact.contactEmailAddresses.size() != emailAddressSize
        assert updatedContact.contactPhoneNumbers.size() == phoneNumberSize
        assert updatedContact.contactEmailAddresses.find { it.emailAddress.equals(updatedContactEmailAddress.emailAddress) }
        assert updatedContact.contactEmailAddresses.find { it.emailAddress.equals(newContactEmailAddress.emailAddress) }
    }
//    Likely won't ever hit this test condition - but if we need to handle it we will need to adjust the ContactController.update method accordingly
//    def "update a contact with no changes to Contact but has changes in one of its pre-existing two children email address classes"(){
//        given:
//        def emailAddressSize = contactWithTwoEmailAddresses.contactEmailAddresses.size()  // before the update
//        def addressSize = contact.contactAddresses.size()
//        def phoneNumberSize = contact.contactPhoneNumbers.size()
//        params.id = contactWithTwoEmailAddresses.id
//        String expectedUpdatedEmailAddress = "updatedthehelloutofthis@cogda.com"
//        String jsonString =   /{"id":${contactWithTwoEmailAddresses.id}, "contactEmailAddresses":[{"id":${contactWithTwoEmailAddresses.contactEmailAddresses.first().id}, "primaryEmailAddress":false, "emailAddress":"updatedthehelloutofthis@cogda.com"}]}/
//        println ">>>>>>" + jsonString
//        request.json = jsonString
//        request.setContentType("application/json")
//
//        when:
//        controller.update()
//
//        then:
//        assert response.status == SC_OK
//        assert response.json.id, "No id found"
//        Contact updatedContact = Contact.get(response.json.id)
//        println ">>>>>>" + response.json.contactEmailAddresses
//        assert response.json.contactEmailAddresses
//        assert updatedContact.contactAddresses.size() == addressSize
//        assert updatedContact.contactEmailAddresses.size() == emailAddressSize
//        assert updatedContact.contactPhoneNumbers.size() == phoneNumberSize
//        assert updatedContact.contactEmailAddresses.find { it.emailAddress.equals(expectedUpdatedEmailAddress) }
//    }

    def "successfully delete a contact"(){
        given:
        Long contactId = contact.id
        params.id = contactId

        when:
        controller.delete()

        then:
        assert response.status == SC_OK
        assertNull Contact.get(contactId)
        assert response.getContentAsString().equals(/{"message":"default.deleted.message"}/)

    }

    private Contact createFullContact(boolean valid = true){
        Contact contact = new Contact()
        contact.firstName = "ThisIsSoRandom" + ((Math.random() * 100) + 1)
        contact.middleName = "Middle"
        contact.lastName = "Fury"
        contact.dateOfBirth = new Date()
        contact.gender = GenderEnum.FEMALE
        contact.initials = "FML"
        contact.title = "Miss"
        contact.jobTitle = "Job title"
        contact.website = "http://cogda.com"
        contact.contactEmailAddresses = []
        contact.contactAddresses = []
        contact.contactPhoneNumbers = []
        assert contact.validate()
        ["1111@cogda.com", "2222@cogda.com", "3333@cogda.com"].eachWithIndex { email, i ->
            ContactEmailAddress contactEmailAddress = new ContactEmailAddress()
            contactEmailAddress.emailAddress = email
            contactEmailAddress.primaryEmailAddress = i == 0
            contactEmailAddress.contact = contact
            assert contactEmailAddress.validate()

            contact.contactEmailAddresses.add(contactEmailAddress)

        }

        ["1111 Street", "2222 Street", "3333 Street"].eachWithIndex{ String addressOne, int i ->
            ContactAddress contactAddress = new ContactAddress()
            Address address = new Address()
            address.addressOne = addressOne
            address.addressTwo = "P.O. Box 123"
            address.addressThree = "Basement"
            address.city = "Athens"
            address.state = "GA"
            address.zipcode = "30601"
            address.county = "Clarke"
            contactAddress.primaryAddress = i == 0
            contactAddress.contact = contact
            contactAddress.address = address
            contactAddress.addressType = "commercial"
            assert contactAddress.validate()

            contact.contactAddresses.add(contactAddress)

        }

        ["1-706-123-4567 4321", "1-706-123-5678 1234"].eachWithIndex{ String phoneNumber, int i ->
            ContactPhoneNumber contactPhoneNumber = new ContactPhoneNumber()
            contactPhoneNumber.phoneNumber = phoneNumber
            contactPhoneNumber.primaryPhoneNumber = i == 0
            contactPhoneNumber.contact = contact
            assert contactPhoneNumber.validate()

            contact.contactPhoneNumbers.add(contactPhoneNumber)

        }

        return contact
    }

}
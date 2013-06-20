package com.cogda.domain

import grails.converters.JSON
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*
import grails.test.mixin.*

/**
 * ContactControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(ContactController)
@TestMixin(DomainClassUnitTestMixin)
class ContactControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex(){
        controller.index()

        assertNull response.redirectedUrl
    }

    void testList(){

        mockDomain(Contact)

        new Contact(firstName:"First", lastName:"Last", companyName: "Company Name").save()
        new Contact(firstName:"A", lastName:"L", companyName:"Another Company").save()

        controller.list()
        def expectedJsonString =        /{"aaData":[{"id":1,"lastName":"Last","primaryEmailAddress":null,"firstName":"First","companyName":"Company Name","jobTitle":null,"version":0},{"id":2,"lastName":"L","primaryEmailAddress":null,"firstName":"A","companyName":"Another Company","jobTitle":null,"version":0}],"sEcho":1}/
        assert response.getJson().toString() == expectedJsonString
    }

    void testSave(){
        mockDomain(Contact)

        Contact contact = new Contact(firstName:"Chris", lastName:"Kwiatkowski", companyName:"Cogda Solutions, LLC.")

        def converter = contact as JSON

        String jsonContactString = converter.toString(true)
//        println  jsonContactString

        params.contact = jsonContactString

        controller.save()

        assert Contact.list().size() == 1, "Contact should have been saved"
        Contact c = Contact.first()
        assert c.firstName == contact.firstName
        assert c.lastName == contact.lastName
        assert c.companyName == contact.companyName

        assert response.getJson()?.success, "The Response says we failed to save the Contact"
        assert response.getJson()?.messages.first() == "contact.save.successful"
    }

    void testGet(){
        mockDomain(Contact)
        Contact contact = new Contact(firstName:"Chris", lastName:"Kwiatkowski", companyName:"Cogda Solutions, LLC.")
        assert contact.save()
        assert contact.id
        params.id = contact.id

        controller.get()

        assert response.getJson()?.success, "The response says we could not find the Contact"
        assert response.getJson()?.modelObject, "The response says we don't have a Contact Instance"
        assert response.getJson()?.modelObject.id == contact.id
        assert response.getJson()?.modelObject.firstName == contact.firstName
        assert response.getJson()?.modelObject.lastName == contact.lastName
        assert response.getJson()?.modelObject.companyName == contact.companyName
    }

    void testGetNotFound(){
        mockDomain(Contact)

        params.id = 99999999

        controller.get()
        assert !response.getJson()?.success, "The response says we found a Contact and we shouldn't have"
        assert response.getJson()?.errors.id == "contact.not.found"
    }

    void testUpdate(){
        mockDomain(Contact)
        Contact contact = new Contact(firstName:"Christopher".reverse(), lastName:"Kwiatkowski".reverse(), companyName:"Cogda Solutions, LLC.".reverse())
        assert contact.save()
        assert contact.id
        params.id = contact.id

        Contact updatedContact = new Contact(id:contact.id, version:contact.version, firstName:"Christopher", lastName:"Kwiatkowski", companyName:"Cogda Solutions, LLC.")

        Map contactMap = [contact:updatedContact]

        def converter = contactMap as JSON

        String jsonUpdatedContactString = converter.toString(true)
        println  jsonUpdatedContactString

        params.contact = jsonUpdatedContactString

        controller.update()

        println response.getJson()

        assert response.getJson()?.success, "The response says we could not find the Contact"
        assert response.getJson()?.modelObject, "The response says we don't have a Contact Instance"
        assert response.getJson()?.modelObject.id == contact.id
        assert response.getJson()?.modelObject.firstName == updatedContact.firstName
        assert response.getJson()?.modelObject.lastName == updatedContact.lastName
        assert response.getJson()?.modelObject.companyName == updatedContact.companyName
    }
}

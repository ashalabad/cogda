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
        println  jsonContactString

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




}

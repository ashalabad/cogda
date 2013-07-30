package com.cogda.multitenant

import com.cogda.multitenant.lead.contact.LeadContactController
import grails.test.mixin.*

/**
 * LeadContactControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadContactController)
@Mock(LeadContact)
class LeadContactControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadContact/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadContactInstanceList.size() == 0
        assert model.leadContactInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadContactInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadContactInstance != null
        assert view == '/leadContact/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadContact/show/1'
        assert controller.flash.message != null
        assert LeadContact.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContact/list'


        populateValidParams(params)
        def leadContact = new LeadContact(params)

        assert leadContact.save() != null

        params.id = leadContact.id

        def model = controller.show()

        assert model.leadContactInstance == leadContact
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContact/list'


        populateValidParams(params)
        def leadContact = new LeadContact(params)

        assert leadContact.save() != null

        params.id = leadContact.id

        def model = controller.edit()

        assert model.leadContactInstance == leadContact
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContact/list'

        response.reset()


        populateValidParams(params)
        def leadContact = new LeadContact(params)

        assert leadContact.save() != null

        // test invalid parameters in update
        params.id = leadContact.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadContact/edit"
        assert model.leadContactInstance != null

        leadContact.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadContact/show/$leadContact.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadContact.clearErrors()

        populateValidParams(params)
        params.id = leadContact.id
        params.version = -1
        controller.update()

        assert view == "/leadContact/edit"
        assert model.leadContactInstance != null
        assert model.leadContactInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadContact/list'

        response.reset()

        populateValidParams(params)
        def leadContact = new LeadContact(params)

        assert leadContact.save() != null
        assert LeadContact.count() == 1

        params.id = leadContact.id

        controller.delete()

        assert LeadContact.count() == 0
        assert LeadContact.get(leadContact.id) == null
        assert response.redirectedUrl == '/leadContact/list'
    }
}

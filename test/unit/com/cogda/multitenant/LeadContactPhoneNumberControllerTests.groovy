package com.cogda.multitenant

import com.cogda.domain.lead.contact.LeadContactPhoneNumberController
import grails.test.mixin.*

/**
 * LeadContactPhoneNumberControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadContactPhoneNumberController)
@Mock(LeadContactPhoneNumber)
class LeadContactPhoneNumberControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadContactPhoneNumber/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadContactPhoneNumberInstanceList.size() == 0
        assert model.leadContactPhoneNumberInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadContactPhoneNumberInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadContactPhoneNumberInstance != null
        assert view == '/leadContactPhoneNumber/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadContactPhoneNumber/show/1'
        assert controller.flash.message != null
        assert LeadContactPhoneNumber.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactPhoneNumber/list'


        populateValidParams(params)
        def leadContactPhoneNumber = new LeadContactPhoneNumber(params)

        assert leadContactPhoneNumber.save() != null

        params.id = leadContactPhoneNumber.id

        def model = controller.show()

        assert model.leadContactPhoneNumberInstance == leadContactPhoneNumber
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactPhoneNumber/list'


        populateValidParams(params)
        def leadContactPhoneNumber = new LeadContactPhoneNumber(params)

        assert leadContactPhoneNumber.save() != null

        params.id = leadContactPhoneNumber.id

        def model = controller.edit()

        assert model.leadContactPhoneNumberInstance == leadContactPhoneNumber
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactPhoneNumber/list'

        response.reset()


        populateValidParams(params)
        def leadContactPhoneNumber = new LeadContactPhoneNumber(params)

        assert leadContactPhoneNumber.save() != null

        // test invalid parameters in update
        params.id = leadContactPhoneNumber.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadContactPhoneNumber/edit"
        assert model.leadContactPhoneNumberInstance != null

        leadContactPhoneNumber.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadContactPhoneNumber/show/$leadContactPhoneNumber.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadContactPhoneNumber.clearErrors()

        populateValidParams(params)
        params.id = leadContactPhoneNumber.id
        params.version = -1
        controller.update()

        assert view == "/leadContactPhoneNumber/edit"
        assert model.leadContactPhoneNumberInstance != null
        assert model.leadContactPhoneNumberInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadContactPhoneNumber/list'

        response.reset()

        populateValidParams(params)
        def leadContactPhoneNumber = new LeadContactPhoneNumber(params)

        assert leadContactPhoneNumber.save() != null
        assert LeadContactPhoneNumber.count() == 1

        params.id = leadContactPhoneNumber.id

        controller.delete()

        assert LeadContactPhoneNumber.count() == 0
        assert LeadContactPhoneNumber.get(leadContactPhoneNumber.id) == null
        assert response.redirectedUrl == '/leadContactPhoneNumber/list'
    }
}

package com.cogda.multitenant

import com.cogda.domain.lead.contact.LeadContactEmailAddressController
import grails.test.mixin.*

/**
 * LeadContactEmailAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadContactEmailAddressController)
@Mock(LeadContactEmailAddress)
class LeadContactEmailAddressControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadContactEmailAddress/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadContactEmailAddressInstanceList.size() == 0
        assert model.leadContactEmailAddressInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadContactEmailAddressInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadContactEmailAddressInstance != null
        assert view == '/leadContactEmailAddress/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadContactEmailAddress/show/1'
        assert controller.flash.message != null
        assert LeadContactEmailAddress.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactEmailAddress/list'


        populateValidParams(params)
        def leadContactEmailAddress = new LeadContactEmailAddress(params)

        assert leadContactEmailAddress.save() != null

        params.id = leadContactEmailAddress.id

        def model = controller.show()

        assert model.leadContactEmailAddressInstance == leadContactEmailAddress
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactEmailAddress/list'


        populateValidParams(params)
        def leadContactEmailAddress = new LeadContactEmailAddress(params)

        assert leadContactEmailAddress.save() != null

        params.id = leadContactEmailAddress.id

        def model = controller.edit()

        assert model.leadContactEmailAddressInstance == leadContactEmailAddress
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactEmailAddress/list'

        response.reset()


        populateValidParams(params)
        def leadContactEmailAddress = new LeadContactEmailAddress(params)

        assert leadContactEmailAddress.save() != null

        // test invalid parameters in update
        params.id = leadContactEmailAddress.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadContactEmailAddress/edit"
        assert model.leadContactEmailAddressInstance != null

        leadContactEmailAddress.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadContactEmailAddress/show/$leadContactEmailAddress.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadContactEmailAddress.clearErrors()

        populateValidParams(params)
        params.id = leadContactEmailAddress.id
        params.version = -1
        controller.update()

        assert view == "/leadContactEmailAddress/edit"
        assert model.leadContactEmailAddressInstance != null
        assert model.leadContactEmailAddressInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadContactEmailAddress/list'

        response.reset()

        populateValidParams(params)
        def leadContactEmailAddress = new LeadContactEmailAddress(params)

        assert leadContactEmailAddress.save() != null
        assert LeadContactEmailAddress.count() == 1

        params.id = leadContactEmailAddress.id

        controller.delete()

        assert LeadContactEmailAddress.count() == 0
        assert LeadContactEmailAddress.get(leadContactEmailAddress.id) == null
        assert response.redirectedUrl == '/leadContactEmailAddress/list'
    }
}

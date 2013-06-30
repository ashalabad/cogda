package com.cogda.multitenant

import com.cogda.domain.lead.contact.LeadContactAddressController
import grails.test.mixin.*

/**
 * LeadContactAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadContactAddressController)
@Mock(LeadContactAddress)
class LeadContactAddressControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadContactAddress/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadContactAddressInstanceList.size() == 0
        assert model.leadContactAddressInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadContactAddressInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadContactAddressInstance != null
        assert view == '/leadContactAddress/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadContactAddress/show/1'
        assert controller.flash.message != null
        assert LeadContactAddress.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactAddress/list'


        populateValidParams(params)
        def leadContactAddress = new LeadContactAddress(params)

        assert leadContactAddress.save() != null

        params.id = leadContactAddress.id

        def model = controller.show()

        assert model.leadContactAddressInstance == leadContactAddress
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactAddress/list'


        populateValidParams(params)
        def leadContactAddress = new LeadContactAddress(params)

        assert leadContactAddress.save() != null

        params.id = leadContactAddress.id

        def model = controller.edit()

        assert model.leadContactAddressInstance == leadContactAddress
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadContactAddress/list'

        response.reset()


        populateValidParams(params)
        def leadContactAddress = new LeadContactAddress(params)

        assert leadContactAddress.save() != null

        // test invalid parameters in update
        params.id = leadContactAddress.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadContactAddress/edit"
        assert model.leadContactAddressInstance != null

        leadContactAddress.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadContactAddress/show/$leadContactAddress.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadContactAddress.clearErrors()

        populateValidParams(params)
        params.id = leadContactAddress.id
        params.version = -1
        controller.update()

        assert view == "/leadContactAddress/edit"
        assert model.leadContactAddressInstance != null
        assert model.leadContactAddressInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadContactAddress/list'

        response.reset()

        populateValidParams(params)
        def leadContactAddress = new LeadContactAddress(params)

        assert leadContactAddress.save() != null
        assert LeadContactAddress.count() == 1

        params.id = leadContactAddress.id

        controller.delete()

        assert LeadContactAddress.count() == 0
        assert LeadContactAddress.get(leadContactAddress.id) == null
        assert response.redirectedUrl == '/leadContactAddress/list'
    }
}

package com.cogda.multitenant

import com.cogda.domain.lead.LeadAddressController
import grails.test.mixin.*

/**
 * LeadAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadAddressController)
@Mock(LeadAddress)
class LeadAddressControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadAddress/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadAddressInstanceList.size() == 0
        assert model.leadAddressInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadAddressInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadAddressInstance != null
        assert view == '/leadAddress/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadAddress/show/1'
        assert controller.flash.message != null
        assert LeadAddress.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadAddress/list'


        populateValidParams(params)
        def leadAddress = new LeadAddress(params)

        assert leadAddress.save() != null

        params.id = leadAddress.id

        def model = controller.show()

        assert model.leadAddressInstance == leadAddress
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadAddress/list'


        populateValidParams(params)
        def leadAddress = new LeadAddress(params)

        assert leadAddress.save() != null

        params.id = leadAddress.id

        def model = controller.edit()

        assert model.leadAddressInstance == leadAddress
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadAddress/list'

        response.reset()


        populateValidParams(params)
        def leadAddress = new LeadAddress(params)

        assert leadAddress.save() != null

        // test invalid parameters in update
        params.id = leadAddress.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadAddress/edit"
        assert model.leadAddressInstance != null

        leadAddress.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadAddress/show/$leadAddress.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadAddress.clearErrors()

        populateValidParams(params)
        params.id = leadAddress.id
        params.version = -1
        controller.update()

        assert view == "/leadAddress/edit"
        assert model.leadAddressInstance != null
        assert model.leadAddressInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadAddress/list'

        response.reset()

        populateValidParams(params)
        def leadAddress = new LeadAddress(params)

        assert leadAddress.save() != null
        assert LeadAddress.count() == 1

        params.id = leadAddress.id

        controller.delete()

        assert LeadAddress.count() == 0
        assert LeadAddress.get(leadAddress.id) == null
        assert response.redirectedUrl == '/leadAddress/list'
    }
}

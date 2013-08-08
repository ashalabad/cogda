package com.cogda.multitenant

import com.cogda.multitenant.lead.LeadLineOfBusinessController
import grails.test.mixin.*

/**
 * LeadLineOfBusinessControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadLineOfBusinessController)
@Mock(LeadLineOfBusiness)
class LeadLineOfBusinessControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadLineOfBusiness/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadLineOfBusinessInstanceList.size() == 0
        assert model.leadLineOfBusinessInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadLineOfBusinessInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadLineOfBusinessInstance != null
        assert view == '/leadLineOfBusiness/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadLineOfBusiness/show/1'
        assert controller.flash.message != null
        assert LeadLineOfBusiness.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadLineOfBusiness/list'


        populateValidParams(params)
        def leadLineOfBusiness = new LeadLineOfBusiness(params)

        assert leadLineOfBusiness.save() != null

        params.id = leadLineOfBusiness.id

        def model = controller.show()

        assert model.leadLineOfBusinessInstance == leadLineOfBusiness
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadLineOfBusiness/list'


        populateValidParams(params)
        def leadLineOfBusiness = new LeadLineOfBusiness(params)

        assert leadLineOfBusiness.save() != null

        params.id = leadLineOfBusiness.id

        def model = controller.edit()

        assert model.leadLineOfBusinessInstance == leadLineOfBusiness
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadLineOfBusiness/list'

        response.reset()


        populateValidParams(params)
        def leadLineOfBusiness = new LeadLineOfBusiness(params)

        assert leadLineOfBusiness.save() != null

        // test invalid parameters in update
        params.id = leadLineOfBusiness.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadLineOfBusiness/edit"
        assert model.leadLineOfBusinessInstance != null

        leadLineOfBusiness.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadLineOfBusiness/show/$leadLineOfBusiness.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadLineOfBusiness.clearErrors()

        populateValidParams(params)
        params.id = leadLineOfBusiness.id
        params.version = -1
        controller.update()

        assert view == "/leadLineOfBusiness/edit"
        assert model.leadLineOfBusinessInstance != null
        assert model.leadLineOfBusinessInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadLineOfBusiness/list'

        response.reset()

        populateValidParams(params)
        def leadLineOfBusiness = new LeadLineOfBusiness(params)

        assert leadLineOfBusiness.save() != null
        assert LeadLineOfBusiness.count() == 1

        params.id = leadLineOfBusiness.id

        controller.delete()

        assert LeadLineOfBusiness.count() == 0
        assert LeadLineOfBusiness.get(leadLineOfBusiness.id) == null
        assert response.redirectedUrl == '/leadLineOfBusiness/list'
    }
}

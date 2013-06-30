package com.cogda.multitenant

import com.cogda.domain.lead.LeadFileReferenceController
import grails.test.mixin.*

/**
 * LeadFileReferenceControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadFileReferenceController)
@Mock(LeadFileReference)
class LeadFileReferenceControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadFileReference/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadFileReferenceInstanceList.size() == 0
        assert model.leadFileReferenceInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadFileReferenceInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadFileReferenceInstance != null
        assert view == '/leadFileReference/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadFileReference/show/1'
        assert controller.flash.message != null
        assert LeadFileReference.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadFileReference/list'


        populateValidParams(params)
        def leadFileReference = new LeadFileReference(params)

        assert leadFileReference.save() != null

        params.id = leadFileReference.id

        def model = controller.show()

        assert model.leadFileReferenceInstance == leadFileReference
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadFileReference/list'


        populateValidParams(params)
        def leadFileReference = new LeadFileReference(params)

        assert leadFileReference.save() != null

        params.id = leadFileReference.id

        def model = controller.edit()

        assert model.leadFileReferenceInstance == leadFileReference
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadFileReference/list'

        response.reset()


        populateValidParams(params)
        def leadFileReference = new LeadFileReference(params)

        assert leadFileReference.save() != null

        // test invalid parameters in update
        params.id = leadFileReference.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadFileReference/edit"
        assert model.leadFileReferenceInstance != null

        leadFileReference.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadFileReference/show/$leadFileReference.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadFileReference.clearErrors()

        populateValidParams(params)
        params.id = leadFileReference.id
        params.version = -1
        controller.update()

        assert view == "/leadFileReference/edit"
        assert model.leadFileReferenceInstance != null
        assert model.leadFileReferenceInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadFileReference/list'

        response.reset()

        populateValidParams(params)
        def leadFileReference = new LeadFileReference(params)

        assert leadFileReference.save() != null
        assert LeadFileReference.count() == 1

        params.id = leadFileReference.id

        controller.delete()

        assert LeadFileReference.count() == 0
        assert LeadFileReference.get(leadFileReference.id) == null
        assert response.redirectedUrl == '/leadFileReference/list'
    }
}

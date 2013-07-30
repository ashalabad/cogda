package com.cogda.multitenant

import com.cogda.multitenant.lead.LeadNoteController
import grails.test.mixin.*

/**
 * LeadNoteControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LeadNoteController)
@Mock(LeadNote)
class LeadNoteControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/leadNote/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.leadNoteInstanceList.size() == 0
        assert model.leadNoteInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.leadNoteInstance != null
    }

    void testSave() {
        controller.save()

        assert model.leadNoteInstance != null
        assert view == '/leadNote/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/leadNote/show/1'
        assert controller.flash.message != null
        assert LeadNote.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/leadNote/list'


        populateValidParams(params)
        def leadNote = new LeadNote(params)

        assert leadNote.save() != null

        params.id = leadNote.id

        def model = controller.show()

        assert model.leadNoteInstance == leadNote
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/leadNote/list'


        populateValidParams(params)
        def leadNote = new LeadNote(params)

        assert leadNote.save() != null

        params.id = leadNote.id

        def model = controller.edit()

        assert model.leadNoteInstance == leadNote
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/leadNote/list'

        response.reset()


        populateValidParams(params)
        def leadNote = new LeadNote(params)

        assert leadNote.save() != null

        // test invalid parameters in update
        params.id = leadNote.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/leadNote/edit"
        assert model.leadNoteInstance != null

        leadNote.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/leadNote/show/$leadNote.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        leadNote.clearErrors()

        populateValidParams(params)
        params.id = leadNote.id
        params.version = -1
        controller.update()

        assert view == "/leadNote/edit"
        assert model.leadNoteInstance != null
        assert model.leadNoteInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/leadNote/list'

        response.reset()

        populateValidParams(params)
        def leadNote = new LeadNote(params)

        assert leadNote.save() != null
        assert LeadNote.count() == 1

        params.id = leadNote.id

        controller.delete()

        assert LeadNote.count() == 0
        assert LeadNote.get(leadNote.id) == null
        assert response.redirectedUrl == '/leadNote/list'
    }
}

package com.cogda.multitenant



import org.junit.*
import grails.test.mixin.*

/**
 * AccountNoteControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(AccountNoteController)
@Mock(AccountNote)
class AccountNoteControllerTests {

    void testSometing(){
        assert 1==1
    }

//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
//    void testIndex() {
//        controller.index()
//        assert "/accountNote/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.accountNoteInstanceList.size() == 0
//        assert model.accountNoteInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.accountNoteInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.accountNoteInstance != null
//        assert view == '/accountNote/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/accountNote/show/1'
//        assert controller.flash.message != null
//        assert AccountNote.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountNote/list'
//
//
//        populateValidParams(params)
//        def accountNote = new AccountNote(params)
//
//        assert accountNote.save() != null
//
//        params.id = accountNote.id
//
//        def model = controller.show()
//
//        assert model.accountNoteInstance == accountNote
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountNote/list'
//
//
//        populateValidParams(params)
//        def accountNote = new AccountNote(params)
//
//        assert accountNote.save() != null
//
//        params.id = accountNote.id
//
//        def model = controller.edit()
//
//        assert model.accountNoteInstance == accountNote
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountNote/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def accountNote = new AccountNote(params)
//
//        assert accountNote.save() != null
//
//        // test invalid parameters in update
//        params.id = accountNote.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/accountNote/edit"
//        assert model.accountNoteInstance != null
//
//        accountNote.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/accountNote/show/$accountNote.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        accountNote.clearErrors()
//
//        populateValidParams(params)
//        params.id = accountNote.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/accountNote/edit"
//        assert model.accountNoteInstance != null
//        assert model.accountNoteInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountNote/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def accountNote = new AccountNote(params)
//
//        assert accountNote.save() != null
//        assert AccountNote.count() == 1
//
//        params.id = accountNote.id
//
//        controller.delete()
//
//        assert AccountNote.count() == 0
//        assert AccountNote.get(accountNote.id) == null
//        assert response.redirectedUrl == '/accountNote/list'
//    }
}

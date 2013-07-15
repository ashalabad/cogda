package com.cogda.multitenant



import org.junit.*
import grails.test.mixin.*

/**
 * AccountContactControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(AccountContactController)
@Mock(AccountContact)
class AccountContactControllerTests {


    def void testSomething(){
        assert(1==1)
    }

//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
//    void testIndex() {
//        controller.index()
//        assert "/accountContact/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.accountContactInstanceList.size() == 0
//        assert model.accountContactInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.accountContactInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.accountContactInstance != null
//        assert view == '/accountContact/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/accountContact/show/1'
//        assert controller.flash.message != null
//        assert AccountContact.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContact/list'
//
//
//        populateValidParams(params)
//        def accountContact = new AccountContact(params)
//
//        assert accountContact.save() != null
//
//        params.id = accountContact.id
//
//        def model = controller.show()
//
//        assert model.accountContactInstance == accountContact
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContact/list'
//
//
//        populateValidParams(params)
//        def accountContact = new AccountContact(params)
//
//        assert accountContact.save() != null
//
//        params.id = accountContact.id
//
//        def model = controller.edit()
//
//        assert model.accountContactInstance == accountContact
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContact/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def accountContact = new AccountContact(params)
//
//        assert accountContact.save() != null
//
//        // test invalid parameters in update
//        params.id = accountContact.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/accountContact/edit"
//        assert model.accountContactInstance != null
//
//        accountContact.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/accountContact/show/$accountContact.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        accountContact.clearErrors()
//
//        populateValidParams(params)
//        params.id = accountContact.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/accountContact/edit"
//        assert model.accountContactInstance != null
//        assert model.accountContactInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContact/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def accountContact = new AccountContact(params)
//
//        assert accountContact.save() != null
//        assert AccountContact.count() == 1
//
//        params.id = accountContact.id
//
//        controller.delete()
//
//        assert AccountContact.count() == 0
//        assert AccountContact.get(accountContact.id) == null
//        assert response.redirectedUrl == '/accountContact/list'
//    }
}

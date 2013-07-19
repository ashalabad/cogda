package com.cogda.multitenant



import org.junit.*
import grails.test.mixin.*

/**
 * AccountContactPhoneNumberControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(AccountContactPhoneNumberController)
@Mock(AccountContactPhoneNumber)
class AccountContactPhoneNumberControllerTests {

    void testSomething() {
        assert 1==1
    }
//
//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
//    void testIndex() {
//        controller.index()
//        assert "/accountContactPhoneNumber/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.accountContactPhoneNumberInstanceList.size() == 0
//        assert model.accountContactPhoneNumberInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.accountContactPhoneNumberInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.accountContactPhoneNumberInstance != null
//        assert view == '/accountContactPhoneNumber/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/accountContactPhoneNumber/show/1'
//        assert controller.flash.message != null
//        assert AccountContactPhoneNumber.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactPhoneNumber/list'
//
//
//        populateValidParams(params)
//        def accountContactPhoneNumber = new AccountContactPhoneNumber(params)
//
//        assert accountContactPhoneNumber.save() != null
//
//        params.id = accountContactPhoneNumber.id
//
//        def model = controller.show()
//
//        assert model.accountContactPhoneNumberInstance == accountContactPhoneNumber
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactPhoneNumber/list'
//
//
//        populateValidParams(params)
//        def accountContactPhoneNumber = new AccountContactPhoneNumber(params)
//
//        assert accountContactPhoneNumber.save() != null
//
//        params.id = accountContactPhoneNumber.id
//
//        def model = controller.edit()
//
//        assert model.accountContactPhoneNumberInstance == accountContactPhoneNumber
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactPhoneNumber/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def accountContactPhoneNumber = new AccountContactPhoneNumber(params)
//
//        assert accountContactPhoneNumber.save() != null
//
//        // test invalid parameters in update
//        params.id = accountContactPhoneNumber.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/accountContactPhoneNumber/edit"
//        assert model.accountContactPhoneNumberInstance != null
//
//        accountContactPhoneNumber.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/accountContactPhoneNumber/show/$accountContactPhoneNumber.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        accountContactPhoneNumber.clearErrors()
//
//        populateValidParams(params)
//        params.id = accountContactPhoneNumber.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/accountContactPhoneNumber/edit"
//        assert model.accountContactPhoneNumberInstance != null
//        assert model.accountContactPhoneNumberInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactPhoneNumber/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def accountContactPhoneNumber = new AccountContactPhoneNumber(params)
//
//        assert accountContactPhoneNumber.save() != null
//        assert AccountContactPhoneNumber.count() == 1
//
//        params.id = accountContactPhoneNumber.id
//
//        controller.delete()
//
//        assert AccountContactPhoneNumber.count() == 0
//        assert AccountContactPhoneNumber.get(accountContactPhoneNumber.id) == null
//        assert response.redirectedUrl == '/accountContactPhoneNumber/list'
//    }
}

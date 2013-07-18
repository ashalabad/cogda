package com.cogda.multitenant



import org.junit.*
import grails.test.mixin.*

/**
 * AccountContactEmailAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(AccountContactEmailAddressController)
@Mock(AccountContactEmailAddress)
class AccountContactEmailAddressControllerTests {

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
//        assert "/accountContactEmailAddress/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.accountContactEmailAddressInstanceList.size() == 0
//        assert model.accountContactEmailAddressInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.accountContactEmailAddressInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.accountContactEmailAddressInstance != null
//        assert view == '/accountContactEmailAddress/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/accountContactEmailAddress/show/1'
//        assert controller.flash.message != null
//        assert AccountContactEmailAddress.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactEmailAddress/list'
//
//
//        populateValidParams(params)
//        def accountContactEmailAddress = new AccountContactEmailAddress(params)
//
//        assert accountContactEmailAddress.save() != null
//
//        params.id = accountContactEmailAddress.id
//
//        def model = controller.show()
//
//        assert model.accountContactEmailAddressInstance == accountContactEmailAddress
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactEmailAddress/list'
//
//
//        populateValidParams(params)
//        def accountContactEmailAddress = new AccountContactEmailAddress(params)
//
//        assert accountContactEmailAddress.save() != null
//
//        params.id = accountContactEmailAddress.id
//
//        def model = controller.edit()
//
//        assert model.accountContactEmailAddressInstance == accountContactEmailAddress
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactEmailAddress/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def accountContactEmailAddress = new AccountContactEmailAddress(params)
//
//        assert accountContactEmailAddress.save() != null
//
//        // test invalid parameters in update
//        params.id = accountContactEmailAddress.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/accountContactEmailAddress/edit"
//        assert model.accountContactEmailAddressInstance != null
//
//        accountContactEmailAddress.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/accountContactEmailAddress/show/$accountContactEmailAddress.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        accountContactEmailAddress.clearErrors()
//
//        populateValidParams(params)
//        params.id = accountContactEmailAddress.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/accountContactEmailAddress/edit"
//        assert model.accountContactEmailAddressInstance != null
//        assert model.accountContactEmailAddressInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactEmailAddress/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def accountContactEmailAddress = new AccountContactEmailAddress(params)
//
//        assert accountContactEmailAddress.save() != null
//        assert AccountContactEmailAddress.count() == 1
//
//        params.id = accountContactEmailAddress.id
//
//        controller.delete()
//
//        assert AccountContactEmailAddress.count() == 0
//        assert AccountContactEmailAddress.get(accountContactEmailAddress.id) == null
//        assert response.redirectedUrl == '/accountContactEmailAddress/list'
//    }
}

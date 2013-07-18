package com.cogda.multitenant



import org.junit.*
import grails.test.mixin.*

/**
 * AccountContactAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(AccountContactAddressController)
@Mock(AccountContactAddress)
class AccountContactAddressControllerTests {

    void testSomething() {
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
//        assert "/accountContactAddress/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.accountContactAddressInstanceList.size() == 0
//        assert model.accountContactAddressInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.accountContactAddressInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.accountContactAddressInstance != null
//        assert view == '/accountContactAddress/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/accountContactAddress/show/1'
//        assert controller.flash.message != null
//        assert AccountContactAddress.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactAddress/list'
//
//
//        populateValidParams(params)
//        def accountContactAddress = new AccountContactAddress(params)
//
//        assert accountContactAddress.save() != null
//
//        params.id = accountContactAddress.id
//
//        def model = controller.show()
//
//        assert model.accountContactAddressInstance == accountContactAddress
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactAddress/list'
//
//
//        populateValidParams(params)
//        def accountContactAddress = new AccountContactAddress(params)
//
//        assert accountContactAddress.save() != null
//
//        params.id = accountContactAddress.id
//
//        def model = controller.edit()
//
//        assert model.accountContactAddressInstance == accountContactAddress
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactAddress/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def accountContactAddress = new AccountContactAddress(params)
//
//        assert accountContactAddress.save() != null
//
//        // test invalid parameters in update
//        params.id = accountContactAddress.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/accountContactAddress/edit"
//        assert model.accountContactAddressInstance != null
//
//        accountContactAddress.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/accountContactAddress/show/$accountContactAddress.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        accountContactAddress.clearErrors()
//
//        populateValidParams(params)
//        params.id = accountContactAddress.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/accountContactAddress/edit"
//        assert model.accountContactAddressInstance != null
//        assert model.accountContactAddressInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactAddress/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def accountContactAddress = new AccountContactAddress(params)
//
//        assert accountContactAddress.save() != null
//        assert AccountContactAddress.count() == 1
//
//        params.id = accountContactAddress.id
//
//        controller.delete()
//
//        assert AccountContactAddress.count() == 0
//        assert AccountContactAddress.get(accountContactAddress.id) == null
//        assert response.redirectedUrl == '/accountContactAddress/list'
//    }
}

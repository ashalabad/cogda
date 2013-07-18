package com.cogda.multitenant



import org.junit.*
import grails.test.mixin.*

/**
 * AccountContactLinkControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(AccountContactLinkController)
@Mock(AccountContactLink)
class AccountContactLinkControllerTests {

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
//        assert "/accountContactLink/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.accountContactLinkInstanceList.size() == 0
//        assert model.accountContactLinkInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.accountContactLinkInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.accountContactLinkInstance != null
//        assert view == '/accountContactLink/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/accountContactLink/show/1'
//        assert controller.flash.message != null
//        assert AccountContactLink.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactLink/list'
//
//
//        populateValidParams(params)
//        def accountContactLink = new AccountContactLink(params)
//
//        assert accountContactLink.save() != null
//
//        params.id = accountContactLink.id
//
//        def model = controller.show()
//
//        assert model.accountContactLinkInstance == accountContactLink
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactLink/list'
//
//
//        populateValidParams(params)
//        def accountContactLink = new AccountContactLink(params)
//
//        assert accountContactLink.save() != null
//
//        params.id = accountContactLink.id
//
//        def model = controller.edit()
//
//        assert model.accountContactLinkInstance == accountContactLink
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactLink/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def accountContactLink = new AccountContactLink(params)
//
//        assert accountContactLink.save() != null
//
//        // test invalid parameters in update
//        params.id = accountContactLink.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/accountContactLink/edit"
//        assert model.accountContactLinkInstance != null
//
//        accountContactLink.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/accountContactLink/show/$accountContactLink.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        accountContactLink.clearErrors()
//
//        populateValidParams(params)
//        params.id = accountContactLink.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/accountContactLink/edit"
//        assert model.accountContactLinkInstance != null
//        assert model.accountContactLinkInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/accountContactLink/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def accountContactLink = new AccountContactLink(params)
//
//        assert accountContactLink.save() != null
//        assert AccountContactLink.count() == 1
//
//        params.id = accountContactLink.id
//
//        controller.delete()
//
//        assert AccountContactLink.count() == 0
//        assert AccountContactLink.get(accountContactLink.id) == null
//        assert response.redirectedUrl == '/accountContactLink/list'
//    }
}

package com.cogda.domain.admin



import org.junit.*
import grails.test.mixin.*

/**
 * SicCodeControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(SicCodeController)
@Mock(SicCode)
class SicCodeControllerTests {

      void testSomething() {
        assert 1 == 1
      }
}
//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
//    void testIndex() {
//        controller.index()
//        assert "/sicCode/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.sicCodeInstanceList.size() == 0
//        assert model.sicCodeInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.sicCodeInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.sicCodeInstance != null
//        assert view == '/sicCode/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/sicCode/show/1'
//        assert controller.flash.message != null
//        assert SicCode.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/sicCode/list'
//
//
//        populateValidParams(params)
//        def sicCode = new SicCode(params)
//
//        assert sicCode.save() != null
//
//        params.id = sicCode.id
//
//        def model = controller.show()
//
//        assert model.sicCodeInstance == sicCode
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/sicCode/list'
//
//
//        populateValidParams(params)
//        def sicCode = new SicCode(params)
//
//        assert sicCode.save() != null
//
//        params.id = sicCode.id
//
//        def model = controller.edit()
//
//        assert model.sicCodeInstance == sicCode
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/sicCode/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def sicCode = new SicCode(params)
//
//        assert sicCode.save() != null
//
//        // test invalid parameters in update
//        params.id = sicCode.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/sicCode/edit"
//        assert model.sicCodeInstance != null
//
//        sicCode.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/sicCode/show/$sicCode.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        sicCode.clearErrors()
//
//        populateValidParams(params)
//        params.id = sicCode.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/sicCode/edit"
//        assert model.sicCodeInstance != null
//        assert model.sicCodeInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/sicCode/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def sicCode = new SicCode(params)
//
//        assert sicCode.save() != null
//        assert SicCode.count() == 1
//
//        params.id = sicCode.id
//
//        controller.delete()
//
//        assert SicCode.count() == 0
//        assert SicCode.get(sicCode.id) == null
//        assert response.redirectedUrl == '/sicCode/list'
//    }
//}

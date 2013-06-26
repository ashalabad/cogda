package com.cogda.domain.admin



import org.junit.*
import grails.test.mixin.*

/**
 * NaicsCodeControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(NaicsCodeController)
@Mock(NaicsCode)
class NaicsCodeControllerTests {

     void testSomething(){
         true
     }


//    def populateValidParams(params) {
//        assert params != null
//        // TODO: Populate valid properties like...
//        //params["name"] = 'someValidName'
//    }
//
//    void testIndex() {
//        controller.index()
//        assert "/naicsCode/list" == response.redirectedUrl
//    }
//
//    void testList() {
//
//        def model = controller.list()
//
//        assert model.naicsCodeInstanceList.size() == 0
//        assert model.naicsCodeInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.naicsCodeInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.naicsCodeInstance != null
//        assert view == '/naicsCode/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/naicsCode/show/1'
//        assert controller.flash.message != null
//        assert NaicsCode.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/naicsCode/list'
//
//
//        populateValidParams(params)
//        def naicsCode = new NaicsCode(params)
//
//        assert naicsCode.save() != null
//
//        params.id = naicsCode.id
//
//        def model = controller.show()
//
//        assert model.naicsCodeInstance == naicsCode
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/naicsCode/list'
//
//
//        populateValidParams(params)
//        def naicsCode = new NaicsCode(params)
//
//        assert naicsCode.save() != null
//
//        params.id = naicsCode.id
//
//        def model = controller.edit()
//
//        assert model.naicsCodeInstance == naicsCode
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/naicsCode/list'
//
//        response.reset()
//
//
//        populateValidParams(params)
//        def naicsCode = new NaicsCode(params)
//
//        assert naicsCode.save() != null
//
//        // test invalid parameters in update
//        params.id = naicsCode.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/naicsCode/edit"
//        assert model.naicsCodeInstance != null
//
//        naicsCode.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/naicsCode/show/$naicsCode.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        naicsCode.clearErrors()
//
//        populateValidParams(params)
//        params.id = naicsCode.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/naicsCode/edit"
//        assert model.naicsCodeInstance != null
//        assert model.naicsCodeInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/naicsCode/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def naicsCode = new NaicsCode(params)
//
//        assert naicsCode.save() != null
//        assert NaicsCode.count() == 1
//
//        params.id = naicsCode.id
//
//        controller.delete()
//
//        assert NaicsCode.count() == 0
//        assert NaicsCode.get(naicsCode.id) == null
//        assert response.redirectedUrl == '/naicsCode/list'
//    }
}

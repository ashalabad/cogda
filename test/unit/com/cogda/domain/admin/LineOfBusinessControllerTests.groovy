package com.cogda.domain.admin



import org.junit.*
import grails.test.mixin.*

/**
 * LineOfBusinessControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LineOfBusinessController)
@Mock(LineOfBusiness)
class LineOfBusinessControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/lineOfBusiness/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.lineOfBusinessInstanceList.size() == 0
        assert model.lineOfBusinessInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.lineOfBusinessInstance != null
    }

    void testSave() {
        controller.save()

        assert model.lineOfBusinessInstance != null
        assert view == '/lineOfBusiness/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/lineOfBusiness/show/1'
        assert controller.flash.message != null
        assert LineOfBusiness.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusiness/list'


        populateValidParams(params)
        def lineOfBusiness = new LineOfBusiness(params)

        assert lineOfBusiness.save() != null

        params.id = lineOfBusiness.id

        def model = controller.show()

        assert model.lineOfBusinessInstance == lineOfBusiness
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusiness/list'


        populateValidParams(params)
        def lineOfBusiness = new LineOfBusiness(params)

        assert lineOfBusiness.save() != null

        params.id = lineOfBusiness.id

        def model = controller.edit()

        assert model.lineOfBusinessInstance == lineOfBusiness
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusiness/list'

        response.reset()


        populateValidParams(params)
        def lineOfBusiness = new LineOfBusiness(params)

        assert lineOfBusiness.save() != null

        // test invalid parameters in update
        params.id = lineOfBusiness.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/lineOfBusiness/edit"
        assert model.lineOfBusinessInstance != null

        lineOfBusiness.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/lineOfBusiness/show/$lineOfBusiness.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        lineOfBusiness.clearErrors()

        populateValidParams(params)
        params.id = lineOfBusiness.id
        params.version = -1
        controller.update()

        assert view == "/lineOfBusiness/edit"
        assert model.lineOfBusinessInstance != null
        assert model.lineOfBusinessInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusiness/list'

        response.reset()

        populateValidParams(params)
        def lineOfBusiness = new LineOfBusiness(params)

        assert lineOfBusiness.save() != null
        assert LineOfBusiness.count() == 1

        params.id = lineOfBusiness.id

        controller.delete()

        assert LineOfBusiness.count() == 0
        assert LineOfBusiness.get(lineOfBusiness.id) == null
        assert response.redirectedUrl == '/lineOfBusiness/list'
    }
}

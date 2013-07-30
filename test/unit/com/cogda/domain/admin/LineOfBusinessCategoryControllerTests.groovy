package com.cogda.domain.admin



import org.junit.*
import grails.test.mixin.*

/**
 * LineOfBusinessCategoryControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(LineOfBusinessCategoryController)
@Mock(LineOfBusinessCategory)
class LineOfBusinessCategoryControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/lineOfBusinessCategory/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.lineOfBusinessCategoryInstanceList.size() == 0
        assert model.lineOfBusinessCategoryInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.lineOfBusinessCategoryInstance != null
    }

    void testSave() {
        controller.save()

        assert model.lineOfBusinessCategoryInstance != null
        assert view == '/lineOfBusinessCategory/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/lineOfBusinessCategory/show/1'
        assert controller.flash.message != null
        assert LineOfBusinessCategory.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusinessCategory/list'


        populateValidParams(params)
        def lineOfBusinessCategory = new LineOfBusinessCategory(params)

        assert lineOfBusinessCategory.save() != null

        params.id = lineOfBusinessCategory.id

        def model = controller.show()

        assert model.lineOfBusinessCategoryInstance == lineOfBusinessCategory
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusinessCategory/list'


        populateValidParams(params)
        def lineOfBusinessCategory = new LineOfBusinessCategory(params)

        assert lineOfBusinessCategory.save() != null

        params.id = lineOfBusinessCategory.id

        def model = controller.edit()

        assert model.lineOfBusinessCategoryInstance == lineOfBusinessCategory
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusinessCategory/list'

        response.reset()


        populateValidParams(params)
        def lineOfBusinessCategory = new LineOfBusinessCategory(params)

        assert lineOfBusinessCategory.save() != null

        // test invalid parameters in update
        params.id = lineOfBusinessCategory.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/lineOfBusinessCategory/edit"
        assert model.lineOfBusinessCategoryInstance != null

        lineOfBusinessCategory.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/lineOfBusinessCategory/show/$lineOfBusinessCategory.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        lineOfBusinessCategory.clearErrors()

        populateValidParams(params)
        params.id = lineOfBusinessCategory.id
        params.version = -1
        controller.update()

        assert view == "/lineOfBusinessCategory/edit"
        assert model.lineOfBusinessCategoryInstance != null
        assert model.lineOfBusinessCategoryInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/lineOfBusinessCategory/list'

        response.reset()

        populateValidParams(params)
        def lineOfBusinessCategory = new LineOfBusinessCategory(params)

        assert lineOfBusinessCategory.save() != null
        assert LineOfBusinessCategory.count() == 1

        params.id = lineOfBusinessCategory.id

        controller.delete()

        assert LineOfBusinessCategory.count() == 0
        assert LineOfBusinessCategory.get(lineOfBusinessCategory.id) == null
        assert response.redirectedUrl == '/lineOfBusinessCategory/list'
    }
}

package com.cogda.domain



import org.junit.*
import grails.test.mixin.*

/**
 * UserProfilePhoneNumberControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(UserProfilePhoneNumberController)
@Mock(UserProfilePhoneNumber)
class UserProfilePhoneNumberControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userProfilePhoneNumber/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userProfilePhoneNumberInstanceList.size() == 0
        assert model.userProfilePhoneNumberInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userProfilePhoneNumberInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userProfilePhoneNumberInstance != null
        assert view == '/userProfilePhoneNumber/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userProfilePhoneNumber/show/1'
        assert controller.flash.message != null
        assert UserProfilePhoneNumber.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfilePhoneNumber/list'


        populateValidParams(params)
        def userProfilePhoneNumber = new UserProfilePhoneNumber(params)

        assert userProfilePhoneNumber.save() != null

        params.id = userProfilePhoneNumber.id

        def model = controller.show()

        assert model.userProfilePhoneNumberInstance == userProfilePhoneNumber
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfilePhoneNumber/list'


        populateValidParams(params)
        def userProfilePhoneNumber = new UserProfilePhoneNumber(params)

        assert userProfilePhoneNumber.save() != null

        params.id = userProfilePhoneNumber.id

        def model = controller.edit()

        assert model.userProfilePhoneNumberInstance == userProfilePhoneNumber
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfilePhoneNumber/list'

        response.reset()


        populateValidParams(params)
        def userProfilePhoneNumber = new UserProfilePhoneNumber(params)

        assert userProfilePhoneNumber.save() != null

        // test invalid parameters in update
        params.id = userProfilePhoneNumber.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userProfilePhoneNumber/edit"
        assert model.userProfilePhoneNumberInstance != null

        userProfilePhoneNumber.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userProfilePhoneNumber/show/$userProfilePhoneNumber.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userProfilePhoneNumber.clearErrors()

        populateValidParams(params)
        params.id = userProfilePhoneNumber.id
        params.version = -1
        controller.update()

        assert view == "/userProfilePhoneNumber/edit"
        assert model.userProfilePhoneNumberInstance != null
        assert model.userProfilePhoneNumberInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userProfilePhoneNumber/list'

        response.reset()

        populateValidParams(params)
        def userProfilePhoneNumber = new UserProfilePhoneNumber(params)

        assert userProfilePhoneNumber.save() != null
        assert UserProfilePhoneNumber.count() == 1

        params.id = userProfilePhoneNumber.id

        controller.delete()

        assert UserProfilePhoneNumber.count() == 0
        assert UserProfilePhoneNumber.get(userProfilePhoneNumber.id) == null
        assert response.redirectedUrl == '/userProfilePhoneNumber/list'
    }
}

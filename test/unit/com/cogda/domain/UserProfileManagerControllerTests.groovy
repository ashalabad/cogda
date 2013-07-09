package com.cogda.domain

import grails.test.mixin.*

/**
 * UserProfileManagerControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(UserProfileManagerController)
@Mock(UserProfile)
class UserProfileManagerControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userProfile/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userProfileInstanceList.size() == 0
        assert model.userProfileInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userProfileInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userProfileInstance != null
        assert view == '/userProfile/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userProfile/show/1'
        assert controller.flash.message != null
        assert UserProfile.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfile/list'


        populateValidParams(params)
        def userProfile = new UserProfile(params)

        assert userProfile.save() != null

        params.id = userProfile.id

        def model = controller.show()

        assert model.userProfileInstance == userProfile
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfile/list'


        populateValidParams(params)
        def userProfile = new UserProfile(params)

        assert userProfile.save() != null

        params.id = userProfile.id

        def model = controller.edit()

        assert model.userProfileInstance == userProfile
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfile/list'

        response.reset()


        populateValidParams(params)
        def userProfile = new UserProfile(params)

        assert userProfile.save() != null

        // test invalid parameters in update
        params.id = userProfile.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userProfile/edit"
        assert model.userProfileInstance != null

        userProfile.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userProfile/show/$userProfile.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userProfile.clearErrors()

        populateValidParams(params)
        params.id = userProfile.id
        params.version = -1
        controller.update()

        assert view == "/userProfile/edit"
        assert model.userProfileInstance != null
        assert model.userProfileInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userProfile/list'

        response.reset()

        populateValidParams(params)
        def userProfile = new UserProfile(params)

        assert userProfile.save() != null
        assert UserProfile.count() == 1

        params.id = userProfile.id

        controller.delete()

        assert UserProfile.count() == 0
        assert UserProfile.get(userProfile.id) == null
        assert response.redirectedUrl == '/userProfile/list'
    }
}

package com.cogda.domain



import org.junit.*
import grails.test.mixin.*

/**
 * UserProfileEmailAddressControllerTests
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(UserProfileEmailAddressController)
@Mock(UserProfileEmailAddress)
class UserProfileEmailAddressControllerTests {


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userProfileEmailAddress/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userProfileEmailAddressInstanceList.size() == 0
        assert model.userProfileEmailAddressInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userProfileEmailAddressInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userProfileEmailAddressInstance != null
        assert view == '/userProfileEmailAddress/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userProfileEmailAddress/show/1'
        assert controller.flash.message != null
        assert UserProfileEmailAddress.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfileEmailAddress/list'


        populateValidParams(params)
        def userProfileEmailAddress = new UserProfileEmailAddress(params)

        assert userProfileEmailAddress.save() != null

        params.id = userProfileEmailAddress.id

        def model = controller.show()

        assert model.userProfileEmailAddressInstance == userProfileEmailAddress
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfileEmailAddress/list'


        populateValidParams(params)
        def userProfileEmailAddress = new UserProfileEmailAddress(params)

        assert userProfileEmailAddress.save() != null

        params.id = userProfileEmailAddress.id

        def model = controller.edit()

        assert model.userProfileEmailAddressInstance == userProfileEmailAddress
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfileEmailAddress/list'

        response.reset()


        populateValidParams(params)
        def userProfileEmailAddress = new UserProfileEmailAddress(params)

        assert userProfileEmailAddress.save() != null

        // test invalid parameters in update
        params.id = userProfileEmailAddress.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userProfileEmailAddress/edit"
        assert model.userProfileEmailAddressInstance != null

        userProfileEmailAddress.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userProfileEmailAddress/show/$userProfileEmailAddress.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userProfileEmailAddress.clearErrors()

        populateValidParams(params)
        params.id = userProfileEmailAddress.id
        params.version = -1
        controller.update()

        assert view == "/userProfileEmailAddress/edit"
        assert model.userProfileEmailAddressInstance != null
        assert model.userProfileEmailAddressInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userProfileEmailAddress/list'

        response.reset()

        populateValidParams(params)
        def userProfileEmailAddress = new UserProfileEmailAddress(params)

        assert userProfileEmailAddress.save() != null
        assert UserProfileEmailAddress.count() == 1

        params.id = userProfileEmailAddress.id

        controller.delete()

        assert UserProfileEmailAddress.count() == 0
        assert UserProfileEmailAddress.get(userProfileEmailAddress.id) == null
        assert response.redirectedUrl == '/userProfileEmailAddress/list'
    }
}

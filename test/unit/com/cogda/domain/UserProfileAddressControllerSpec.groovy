package com.cogda.domain



import org.junit.*
import grails.test.mixin.*
import spock.lang.Specification

/**
 * UserProfileAddressControllerSpec
 * A unit test class is used to test individual methods or blocks of code without considering the surrounding infrastructure
 */
@TestFor(UserProfileAddressController)
@Mock(UserProfileAddress)
class UserProfileAddressControllerSpec extends Specification {

    String testJsonString = /{"userProfile.id":"1","id":"","version":"","address.addressOne":"175 Stafford Drive","address.addressTwo":"","address.addressThree":"","address.zipcode":"30605","address.city":"Athens","address.state":"Georgia","address.county":"GA","address.country":"usa"}/


    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userProfileAddress/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userProfileAddressInstanceList.size() == 0
        assert model.userProfileAddressInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userProfileAddressInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userProfileAddressInstance != null
        assert view == '/userProfileAddress/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userProfileAddress/show/1'
        assert controller.flash.message != null
        assert UserProfileAddress.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfileAddress/list'


        populateValidParams(params)
        def userProfileAddress = new UserProfileAddress(params)

        assert userProfileAddress.save() != null

        params.id = userProfileAddress.id

        def model = controller.show()

        assert model.userProfileAddressInstance == userProfileAddress
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfileAddress/list'


        populateValidParams(params)
        def userProfileAddress = new UserProfileAddress(params)

        assert userProfileAddress.save() != null

        params.id = userProfileAddress.id

        def model = controller.edit()

        assert model.userProfileAddressInstance == userProfileAddress
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userProfileAddress/list'

        response.reset()


        populateValidParams(params)
        def userProfileAddress = new UserProfileAddress(params)

        assert userProfileAddress.save() != null

        // test invalid parameters in update
        params.id = userProfileAddress.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userProfileAddress/edit"
        assert model.userProfileAddressInstance != null

        userProfileAddress.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userProfileAddress/show/$userProfileAddress.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userProfileAddress.clearErrors()

        populateValidParams(params)
        params.id = userProfileAddress.id
        params.version = -1
        controller.update()

        assert view == "/userProfileAddress/edit"
        assert model.userProfileAddressInstance != null
        assert model.userProfileAddressInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userProfileAddress/list'

        response.reset()

        populateValidParams(params)
        def userProfileAddress = new UserProfileAddress(params)

        assert userProfileAddress.save() != null
        assert UserProfileAddress.count() == 1

        params.id = userProfileAddress.id

        controller.delete()

        assert UserProfileAddress.count() == 0
        assert UserProfileAddress.get(userProfileAddress.id) == null
        assert response.redirectedUrl == '/userProfileAddress/list'
    }
}

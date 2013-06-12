package com.cogda.domain

import grails.test.mixin.domain.DomainClassUnitTestMixin

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(UserProfile)
@TestMixin(DomainClassUnitTestMixin)
class UserProfileTests {

    UserProfile userProfile

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    /**
     * class UserProfile {
     * 	...
     * 	firstName(nullable:false, blank:false)
     * 	middleName(nullable:true)
     * 	lastName(nullable:false, blank:false)
     * 	company(nullable:true)
     * 	user(nullable:true)
     * 	published(nullable:true)
     *  ...
     */
    void testNullable() {
        mockDomain(UserProfile)

        userProfile = new UserProfile()

        assertFalse userProfile.validate()

        assert "nullable" == userProfile.errors["firstName"].code
        assert "nullable" == userProfile.errors["lastName"].code

        assert !userProfile.errors["middleName"]
        assert !userProfile.errors["company"]
        assert !userProfile.errors["user"]
        assert !userProfile.errors["published"]
    }

    /**
     * class UserProfile {
     * 	...
     * 	firstName(nullable:false, blank:false)
     * 	middleName(nullable:true)
     * 	lastName(nullable:false, blank:false)
     * 	company(nullable:true)
     * 	user(nullable:true)
     * 	published(nullable:true)
     *  ...
     */
    void testBlank(){
        mockDomain(UserProfile)
        userProfile = new UserProfile()

        userProfile.firstName = "  "
        userProfile.lastName = "  "

        assertFalse userProfile.validate()

        assert "blank" == userProfile.errors["firstName"].code
        assert "blank" == userProfile.errors["lastName"].code

        userProfile.firstName = "firstName"
        userProfile.validate()
        assert !userProfile.errors["firstName"]

        userProfile.lastName = "lastName"
        userProfile.validate()
        assert !userProfile.errors["lastName"]


    }

    /**
     *
     */
    void testGetPrimaryEmailAddress(){
        userProfile = mockDomain(UserProfile)
        userProfile.firstName = "firstName"
        userProfile.lastName = "lastName"
        userProfile.published = false
        assert userProfile.save(), "UserProfile test domain class was not saved successfully"

        String primaryEmailAddress =  "primary@cogda.com"

        mockDomain(UserProfileEmailAddress, [new UserProfileEmailAddress(emailAddress: primaryEmailAddress, primaryEmailAddress: true, published:false, userProfile: userProfile),
                new UserProfileEmailAddress(emailAddress: "mezz@cogda.com", primaryEmailAddress: false, published:false, userProfile: userProfile)])

        String emailAddress = userProfile.primaryEmailAddress
        assert emailAddress.equals(primaryEmailAddress)

    }


}

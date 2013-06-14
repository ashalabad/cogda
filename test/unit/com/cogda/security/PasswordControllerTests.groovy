package com.cogda.security

import com.cogda.domain.UserProfile
import com.cogda.domain.admin.CustomerNotificationService
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.domain.security.PasswordCode
import com.cogda.domain.security.User
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(PasswordController)
@TestMixin(DomainClassUnitTestMixin)
class PasswordControllerTests {

    void testIndex() {

        controller.index()

        assert getView().equals("/password/forgotPassword")
    }

    void testForgotPasswordNullUsername(){
        params.username = null

        controller.forgotPassword()

        assert flash.error.equals("spring.security.forgotPassword.username.missing")
    }

    void testForgotPasswordUserNotFound(){
        params.username = "notfound"

        mockDomain(User)

        controller.forgotPassword()

        assert flash.error.equals("spring.security.forgotPassword.user.notFound")
    }

    void testForgotPasswordExistingPasswordCode(){
        mockDomain(User)
        mockDomain(UserProfile)
        mockDomain(PasswordCode)

        def mockSpringSecurityService = mockFor(SpringSecurityService, false)
        mockSpringSecurityService.demand.encodePassword(0..1) { String username ->
            "encodedpassword"
        }

        // customerNotificationService.prepareResetPasswordMessage(userProfile, resetPasswordUrl)
        def mockCustomerNotificationService = mockFor(CustomerNotificationService, false)
        mockCustomerNotificationService.demand.prepareResetPasswordMessage(0..1) { UserProfile userProfile, String resetPasswordUrl ->
            new EmailConfirmationLog()
        }

        controller.customerNotificationService = mockCustomerNotificationService.createMock()

        User user = new User(username:"username", password:"password", enabled: true, accountExpired:false, accountLocked:false, passwordExpired:false)
        user.springSecurityService = mockSpringSecurityService.createMock()
        assert user.save(), "User save failed with ${user.errors}"

        PasswordCode passwordCode = new PasswordCode()
        passwordCode.username = user.username
        assert passwordCode.save(), "PasswordCode save failed ${passwordCode.errors}"


        UserProfile userProfile = new UserProfile()
        userProfile.firstName = "firstName"
        userProfile.middleName = "middleName"
        userProfile.lastName = "lastName"
        userProfile.company = null
        userProfile.user = user
        userProfile.published = true
        userProfile.aboutDesc = ""
        userProfile.businessSpecialtiesDesc = ""
        userProfile.associationsDesc = ""

        assert userProfile.save(), "UserProfile save failed with ${userProfile.errors}"

        params.username = "username"

        controller.forgotPassword()

        assert flash.error == null
        assert PasswordCode.list().size() == 1, "There should only be one PasswordCode for this User ${user.username}"
        assert flash.message.equals("spring.security.resetPassword.emailSent")

    }

    void testForgotPasswordNoExistingPasswordCode(){
        mockDomain(User)
        mockDomain(UserProfile)
        mockDomain(PasswordCode)

        def mockSpringSecurityService = mockFor(SpringSecurityService, false)
        mockSpringSecurityService.demand.encodePassword(0..1) { String username ->
            "encodedpassword"
        }

        // customerNotificationService.prepareResetPasswordMessage(userProfile, resetPasswordUrl)
        def mockCustomerNotificationService = mockFor(CustomerNotificationService, false)
        mockCustomerNotificationService.demand.prepareResetPasswordMessage(0..1) { UserProfile userProfile, String resetPasswordUrl ->
            new EmailConfirmationLog()
        }

        controller.customerNotificationService = mockCustomerNotificationService.createMock()

        User user = new User(username:"username", password:"password", enabled: true, accountExpired:false, accountLocked:false, passwordExpired:false)
        user.springSecurityService = mockSpringSecurityService.createMock()
        assert user.save(), "User save failed with ${user.errors}"

        UserProfile userProfile = new UserProfile()
        userProfile.firstName = "firstName"
        userProfile.middleName = "middleName"
        userProfile.lastName = "lastName"
        userProfile.company = null
        userProfile.user = user
        userProfile.published = true
        userProfile.aboutDesc = ""
        userProfile.businessSpecialtiesDesc = ""
        userProfile.associationsDesc = ""

        assert userProfile.save(), "UserProfile save failed with ${userProfile.errors}"

        params.username = "username"

        controller.forgotPassword()

        assert flash.error == null
        assert PasswordCode.list().size() == 1, "There should only be one PasswordCode for this User ${user.username}"
        assert flash.message.equals("spring.security.resetPassword.emailSent")

    }

    // TODO: Add tests for resetPassword on PasswordController
}

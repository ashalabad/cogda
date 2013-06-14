package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
import com.cogda.security.UserService
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestMixin(DomainClassUnitTestMixin)
@TestFor(EmailVerificationController)
class EmailVerificationControllerTests {


    void testVerifyAwaitingApproval() {
        mockDomain(CompanyType, [new CompanyType(code:"R", description: "R", intCode:0)])
        mockDomain(Registration)

        Registration registration = createValidRegistration()
        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..5) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
        registration.save()

        assert registration.token, "Registration token is missing."

        params.t = registration.token

        controller.verify()

        assert flash.message.equals("registration.status.awaitingAdminApproval")

    }

    void testVerifyApproved() {
        mockDomain(CompanyType, [new CompanyType(code:"R", description: "R", intCode:0)])
        mockDomain(Registration)

        Registration registration = createValidRegistration()
        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..5) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.registrationStatus = RegistrationStatus.APPROVED
        registration.save()

        assert registration.token, "Registration token is missing."

        params.t = registration.token

        controller.verify()

        assert flash.message.equals("registration.status.approved")

    }

    void testVerifyTokenNotFound() {
        mockDomain(CompanyType, [new CompanyType(code:"R", description: "R", intCode:0)])
        mockDomain(Registration)

        Registration registration = createValidRegistration()
        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..5) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.registrationStatus = RegistrationStatus.APPROVED
        registration.save()

        assert registration.token, "Registration token is missing."

        params.t = registration.token + "1020"

        controller.verify()

        assert flash.error.equals("registration.token.invalid")

    }

    void testVerify(){
        mockDomain(CompanyType, [new CompanyType(code:"R", description: "R", intCode:0)])
        mockDomain(Registration)

        Registration registration = createValidRegistration()
        def mockControl = mockFor(UserService, false)
        mockControl.demand.availableUsername(0..5) { String username ->
            true
        }
        registration.userService = mockControl.createMock()

        registration.registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION
        registration.save()

        // accountActivationService.confirmEmailVerification(registration)
        def mockAccountActivationService = mockFor(AccountActivationService, false)
        mockAccountActivationService.demand.confirmEmailVerification(0..1) { Registration r ->
             new EmailConfirmationLog()
        }
        controller.accountActivationService = mockAccountActivationService.createMock()
        assert registration.token, "Registration token is missing."

        params.t = registration.token

        controller.verify()

        assert flash.message.equals("registration.email.verified")

    }

    private Registration createValidRegistration(){
        Registration reg = new Registration()
        reg.firstName = "Christopher"
        reg.lastName = "Kwiatkowski"
        reg.username = "ctk"
        reg.emailAddress = "chris@cogda.com"
        reg.password = "939020kiddko2"
        reg.companyName = "Cogda Solutions, LLC."
        reg.companyType = CompanyType.list().first()
        reg.existingCompany = null
        reg.companyTypeOther = null
        reg.phoneNumber = "706-255-9087"
        reg.streetAddressOne = "1 Press Place"
        reg.streetAddressTwo = "Suite 200"
        reg.streetAddressThree = "Office #17"
        reg.city = "Athens"
        reg.state = "GA"
        reg.zipcode = "30601"
        reg.county = "CLARKE"
        reg.registrationStatus = RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION
        reg.subDomain = "rais"


        return reg
    }
}

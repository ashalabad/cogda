package com.cogda.domain.onboarding

import com.cogda.BaseRegistrationSpec
import com.cogda.common.RegistrationStatus
import com.cogda.multitenant.CustomerAccountService
import com.cogda.security.UserService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.FieldError

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(RegisterService)
@TestMixin([DomainClassUnitTestMixin])
@Mock([Registration])
class RegisterServiceSpec extends BaseRegistrationSpec {
    CustomerAccountService customerAccountService = Mock(CustomerAccountService)
    def userService = Mock(UserService)

    def setup() {
        userService.availableUsername(_) >> true
        customerAccountService.onboardCustomerAccount(_)
        service.customerAccountService = customerAccountService
    }

    def 'save valid registration'() {
        given:
        def registrationInstance = createValidRegistration('whatever')

        when:
        Registration savedRegistration = service.save(registrationInstance)

        then:
        compareRegistration(savedRegistration, registrationInstance)
        !savedRegistration.hasErrors()
        savedRegistration.errors.errorCount == 0
    }

    def 'save invalid registration'() {
        given:
        def registrationInstance = createValidRegistration('whatever')
        registrationInstance.subDomain = '@@@@@@@@@@'

        when:
        Registration savedRegistration = service.save(registrationInstance)

        then:
        savedRegistration == null
        registrationInstance.hasErrors() || registrationInstance.errors.errorCount > 0
    }

    def 'list params'() {
        given:
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }

        expect:
        service.list(max: max).size() == max

        where:
        max << (1..6)
    }

    def 'successfully approve valid registration'() {
        given:
        def registrationInstance = createValidRegistration('whatever', RegistrationStatus.AWAITING_ADMIN_APPROVAL)
        registrationInstance.subDomain = 'validsubdomain'
        registrationInstance.save()

        when:
        service.approve(registrationInstance)

        then:
        registrationInstance.registrationStatus == RegistrationStatus.APPROVED
        Registration.get(registrationInstance.id).registrationStatus == RegistrationStatus.APPROVED
    }

    def 'fail on approve invalid registration status'() {
        given:
        def registrationInstance = createAndSaveValidRegistration('whatever', registrationStatus)

        when:
        service.approve(registrationInstance)
        FieldError fieldError = registrationInstance.errors.getFieldError('registrationStatus')

        then:
        registrationInstance.registrationStatus == registrationStatus
        Registration.get(registrationInstance.id).registrationStatus == registrationStatus
        registrationInstance.errors.errorCount == 1
        fieldError.field == 'registrationStatus'
        fieldError.code == 'registration.status.incorrectstate'
        fieldError.defaultMessage ==  'Registration status must be Awaiting Admin Approval.'

        where:
        registrationStatus << [RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION, RegistrationStatus.APPROVED]
    }

    def 'fail on approve invalid domain'() {
        given:
        RegistrationStatus registrationStatus = RegistrationStatus.AWAITING_ADMIN_APPROVAL
        def registrationInstance = createAndSaveValidRegistration('whatever', registrationStatus)

        when:
        service.approve(registrationInstance)
        FieldError fieldError = registrationInstance.errors.getFieldError('subDomain')

        then:
        registrationInstance.registrationStatus == registrationStatus
        Registration.get(registrationInstance.id).registrationStatus == registrationStatus
        registrationInstance.errors.errorCount == 1
        fieldError.field == 'subDomain'
        fieldError.code == 'registration.subdomain.approval'
        fieldError.defaultMessage ==  'Registration status must have valid subdomain before approval.'
    }

    def 'succesful findById'() {
        given:
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }
        def registrationToFind = Registration.first()

        when:
        def foundRegistration = service.findById(registrationToFind.id)

        then:
        compareRegistration(foundRegistration, registrationToFind)
    }

    def 'succesful get'() {
        given:
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }
        def registrationToFind = Registration.first()

        when:
        def foundRegistration = service.get(registrationToFind.id)

        then:
        compareRegistration(foundRegistration, registrationToFind)
    }

    def 'unsuccessful findById'() {
        when:
        def foundRegistration = service.findById(-1)

        then:
        foundRegistration == null
    }

    def 'valid count'() {
        given:
        (0..5).each {
            i -> createAndSaveValidRegistration(generator((('A'..'Z') + ('0'..'9')).join(), 9))
        }

        when:
        def count = service.count()

        then:
        count == 6
    }
}
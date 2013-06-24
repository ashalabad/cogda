package com.cogda

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.onboarding.Registration
import spock.lang.Specification

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 6/22/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
class BaseRegistrationSpec extends Specification {
    def saveRegistration(Registration registration) {
        registration.userService = userService
        registration.save()
    }

    def createValidRegistration(String username, RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
        Registration registration = createValidBaseRegistration(registrationStatus)
        registration.username = username
        registration.userService = userService
        return registration
    }

    def createInvalidRegistration() {
        return createValidBaseRegistration()
    }

    Registration createValidBaseRegistration(RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
        Registration registration = new Registration(registrationStatus: registrationStatus)
        registration.metaClass.isDirty = { String fieldName -> return false }
        registration.firstName = "Walnut"
        registration.lastName = "Banks"
        registration.emailAddress = "chris@cogda.com"
        registration.password = "939020kiddko2"
        registration.companyName = "Adgoc Solutions, LLC."
        registration.companyType = mockDomain(CompanyType)
        registration.existingCompany = null
        registration.companyTypeOther = null
        registration.phoneNumber = "706-255-9087"
        registration.streetAddressOne = "1 Press Place"
        registration.streetAddressTwo = "Suite 200"
        registration.streetAddressThree = "Office #17"
        registration.city = "Athens"
        registration.state = "GA"
        registration.zipcode = "30601"
        registration.county = "CLARKE"
        return registration
    }

    Registration createAndSaveValidRegistration(String username, RegistrationStatus registrationStatus = RegistrationStatus.APPROVED) {
        Registration registration = createValidRegistration(username, registrationStatus)
        return saveRegistration(registration)
    }

    def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }

    def createCompanyTypes() {
        CompanyType.withTransaction {
            if (!CompanyType.findByCode("Agency/Retailer")) {
                new CompanyType(code: "Agency/Retailer", intCode: 0, description: "Agency/Retailer").save()
            }
            if (!CompanyType.findByCode("Carrier")) {
                new CompanyType(code: "Carrier", intCode: 1, description: "Carrier").save()
            }
            if (!CompanyType.findByCode("Reinsurer")) {
                new CompanyType(code: "Reinsurer", intCode: 2, description: "Reinsurer").save()
            }
            if (!CompanyType.findByCode("Wholesaler (MGA, Broker)")) {
                new CompanyType(code: "Wholesaler (MGA, Broker)", intCode: 3, description: "Wholesaler (MGA, Broker)").save()
            }
        }
    }

    def compareRegistration(Registration a, Registration b) {
        assert a.city == b.city
        assert a.companyName == b.companyName
        assert a.companyType.id == b.companyType.id
        assert a.companyType.intCode == b.companyType.intCode
        assert a.companyTypeOther == b.companyTypeOther
        assert a.country == b.country
        assert a.county == b.county
        assert a.emailAddress == b.emailAddress
        assert a.firstName == b.firstName
        assert a.lastName == b.lastName
        assert a.phoneNumber == b.phoneNumber
        assert a.streetAddressOne == b.streetAddressOne
        assert a.streetAddressTwo == b.streetAddressTwo
        assert a.streetAddressThree == b.streetAddressThree
        assert a.state == b.state
        assert a.newCompany == b.newCompany
        return true
    }
}

package com.cogda.security

import com.cogda.common.RegistrationStatus
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.User
import com.cogda.multitenant.CustomerAccount

import static org.junit.Assert.*
import org.junit.*

class UserServiceTests {
    UserService userService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
        User.withTransaction {
            Registration.executeUpdate("delete from Registration ")
            CompanyType.executeUpdate("delete from CompanyType")
            User.executeUpdate("delete from User")
            CustomerAccount.executeUpdate("delete from CustomerAccount ")
        }
    }

    @Test
    void testAvailableUsername() {
        CustomerAccount customerAccount = new CustomerAccount(subDomain:"fakeCustomerAccount")
        assert customerAccount.save(), "customerAccount.save failed $customerAccount.errors"

        CompanyType companyType = new CompanyType(code:"R", description: "R", intCode:0)
        assert companyType.save(), "companyType.save failed $companyType.errors"

        customerAccount.withThisTenant {
            User user = new User(username:"taken", enabled: true, password: "password")
            assert user.save(), "user.save failed $user.errors"
        }

        Registration registration = createValidRegistration()
        assert registration.save(), "registration.save failed $registration.errors"

        assert !userService.availableUsername("taken"), "username 'taken' should return false it exists in User table"
        assert !userService.availableUsername("takentoo"), "username 'taken' should return false it exists in Registration table"
        assert userService.availableUsername("iamavailable"), "username 'iamavailable' should return true."
    }

    Registration createValidRegistration(){
        Registration reg = new Registration()
        reg.firstName = "Christopher"
        reg.lastName = "Kwiatkowski"
        reg.username = "takentoo"
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

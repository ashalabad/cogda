package com.cogda.multitenant

import com.cogda.common.RegistrationStatus
import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.CompanyProfilePhoneNumber
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.UserProfilePhoneNumber
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.HtmlFragment
import com.cogda.domain.admin.NaicsCode
import com.cogda.domain.admin.SicCode
import com.cogda.domain.admin.SicCodeDivision
import com.cogda.domain.admin.SupportedCountryCode
import com.cogda.domain.admin.SystemEmailMessageTemplate
import com.cogda.domain.onboarding.Registration
import com.cogda.domain.security.Role
import com.cogda.domain.security.User
import com.cogda.domain.security.UserRole
import com.cogda.security.SecurityService
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.LogFactory

import static org.junit.Assert.*
import org.junit.*

class CustomerAccountServiceTests {

    private static final log = LogFactory.getLog(this)

    SpringSecurityService springSecurityService
    CustomerAccountService customerAccountService
    CompanyService companyService
    Registration registration

    @Before
    void setUp() {
        CompanyType agency = new CompanyType(code:"Agency/Retailer", intCode:0, description: "Agency/Retailer")
        if(!agency.save()){
            agency.errors.each {
                log.debug it
            }
        }
        CompanyType carrier = new CompanyType(code:"Carrier", intCode:1, description: "Carrier")
        if(!carrier.save()){
            carrier.errors.each {
                log.debug it
            }
        }
        CompanyType reinsurer = new CompanyType(code:"Reinsurer", intCode:2, description: "Reinsurer")
        if(!reinsurer.save()){
            reinsurer.errors.each {
                log.debug it
            }
        }
        CompanyType wholesaler = new CompanyType(code:"Wholesaler (MGA, Broker)", intCode:3, description: "Wholesaler (MGA, Broker)")
        if(!wholesaler.save()){
            wholesaler.errors.each {
                log.debug it
            }
        }
    }

    @After
    void tearDown() {
        // Tear down logic here
        Registration.executeUpdate("delete from Registration")

        UserRole.executeUpdate("delete from UserRole")
        Role.executeUpdate("delete from Role")

        UserProfileEmailAddress.executeUpdate("delete from UserProfileEmailAddress")
        UserProfilePhoneNumber.executeUpdate("delete from UserProfilePhoneNumber")
        UserProfile.executeUpdate("delete from UserProfile")
        User.executeUpdate("delete from User")

        CompanyProfileAddress.executeUpdate("delete from CompanyProfileAddress")
        CompanyProfilePhoneNumber.executeUpdate("delete from CompanyProfilePhoneNumber")
        CompanyProfile.executeUpdate("delete from CompanyProfile")
        Company.executeUpdate("delete from Company")

        CustomerAccount.executeUpdate("delete from CustomerAccount")

        CompanyType.executeUpdate("delete from CompanyType")
        HtmlFragment.executeUpdate("delete from HtmlFragment")
        NaicsCode.executeUpdate("delete from NaicsCode")
        SicCode.executeUpdate("delete from SicCode")
        SicCodeDivision.executeUpdate("delete from SicCodeDivision")
        SupportedCountryCode.executeUpdate("delete from SupportedCountryCode")
        SystemEmailMessageTemplate.executeUpdate("delete from SystemEmailMessageTemplate")
    }

    @Test
    void testCreate(){
        CustomerAccount customerAccount = new CustomerAccount(subDomain: "newsubdomain")
        customerAccountService.create(customerAccount)
        assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
    }

    @Test
    void testCreateCustomerAccountThenCompany(){
        registration = createValidRegistration()
        assert !registration.hasErrors(), "Registration has Validation Errors"
        CustomerAccount customerAccount = new CustomerAccount(subDomain: "newsubdomain")
        customerAccountService.create(customerAccount)

        assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."

        customerAccount.withThisTenant {
            companyService.createCompany(registration)
        }


    }

    @Test
    void testOnboardCustomerAccount() {
        registration = createValidRegistration()

        log.debug("Calling customerAccountService.onboardCustomerAccount(registration)")
        customerAccountService.onboardCustomerAccount(registration)

        // verify CustomerAccount setup - check that the CustomerAccount was saved successfully
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)
        assert customerAccount, "CustomerAccount with subDomain ${registration.subDomain} was not created successfully"

        // verify Security setup - check that all Role objects were created successfully
        customerAccount.withThisTenant {

            // verify First User setup - check that the initial administrator of the root Company was created successfully
            [
                    SecurityService.ROLE_HOST_ADMINISTRATOR,
                    SecurityService.ROLE_UNDERWRITER,
                    SecurityService.ROLE_CUSTOMER_SERVICE_REP,
                    SecurityService.ROLE_BRANCH_MANAGER,
                    SecurityService.ROLE_MAREKETING_MANAGER,
                    SecurityService.ROLE_SALES_MANAGER,
                    SecurityService.ROLE_MARKETER,
                    SecurityService.ROLE_PRODUCER,
                    SecurityService.ROLE_USER,
                    SecurityService.ROLE_COMPANY_MANAGER,
                    SecurityService.ROLE_ADMINISTRATOR
            ].each { String role ->
                assert Role.findByAuthority(role), "$role was not created successfully"
            }

            // verify Company setup - Check that the root Company was setup appropriately
            Company company = Company.findByCompanyName(registration.companyName)
            assert company, "Company was not created successfully"
            assert company.companyName.equals(registration.companyName), "Company Name does not match Registration Company Name"
            assert company.doingBusinessAs.equals(registration.companyName), "Doing Business As does not match Registration Company Name"
            assert company.parentCompany == null, "Parent Company should be null"
            assert company.intCode == 0, "Company intCode should be 0"


            // verify First User setup - check that the initial administrator of the root Company was created successfully
            User user = User.findByUsername(registration.username)
            assert user, "User is null"

            List userRoles = UserRole.findAllByUser(user)
            List<String> roles = userRoles.collect { UserRole userRole ->
                userRole.role.authority
            }

            assert roles.size() == 2, "UserRoles associated with User should be 2"
            assert roles.containsAll([SecurityService.ROLE_ADMINISTRATOR, SecurityService.ROLE_USER]), "Roles were not assigned successfully for the first User"

        }

        // Verify the UserProfile is associated with the initial administrator and the properties
        User user = User.findByUsername(registration.username)

        log.debug "User was found ${user?.username}"

        assert user, "User was not found"

        UserProfile userProfile = UserProfile.findByUser(user)
        assert userProfile, "UserProfile was not found"
        assert userProfile.firstName.equals(registration.firstName)
        assert userProfile.lastName.equals(registration.lastName)
        assert userProfile.userProfileEmailAddresses.size() == 1, "UserProfileEmailAddress was not added successfully"

        UserProfileEmailAddress upea = userProfile.userProfileEmailAddresses.first()
        assert upea.primaryEmailAddress, "primaryEmailAddress should be true"
        assert upea.emailAddress.equals(registration.emailAddress), "emailAddress does not match registration email address"

        // Verify the CompanyProfile is associated with the initial Company and its properties

    }

    /**
     * Creates a Valid Registration Domain Class.
     * @return
     */
    protected Registration createValidRegistration(){
        registration = new Registration()

        registration.firstName = "Christopher"
        registration.lastName = "Kwiatkowski"
        registration.username = "ctk"
        registration.emailAddress = "chris@cogda.com"
        registration.password = springSecurityService.encodePassword("password")
        registration.companyName = "Cogda Solutions, LLC."
        registration.companyType = CompanyType.list().first()
        registration.existingCompany = null
        registration.companyTypeOther = null
        registration.phoneNumber = "706-255-9087"
        registration.streetAddressOne = "1 Press Place"
        registration.streetAddressTwo = "Suite 200"
        registration.streetAddressThree = "Office #17"
        registration.city = "Athens"
        registration.state = "GA"
        registration.zipcode = "30601"
        registration.registrationStatus = RegistrationStatus.APPROVED
        registration.subDomain = "rais"

        log.debug("Saving Registration Domain Class")

        assert registration.save(), "Registration save failed: ${registration.errors}"

        return registration
    }
}

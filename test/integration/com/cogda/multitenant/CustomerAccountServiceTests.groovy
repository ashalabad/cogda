package com.cogda.multitenant

import com.amazonaws.services.s3.model.DeleteObjectsRequest
import com.amazonaws.services.s3.model.GetObjectRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.cogda.BaseIntegrationTest
import com.cogda.common.RegistrationStatus
import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.CompanyProfilePhoneNumber
import com.cogda.domain.CompanyProfileService
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.UserProfilePhoneNumber
import com.cogda.domain.UserProfileService
import com.cogda.domain.admin.CompanyType
import com.cogda.domain.admin.EmailConfirmationLog
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
import grails.plugin.awssdk.AmazonWebService
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.hibernate.SessionFactory

import static org.junit.Assert.*
import org.junit.*

class CustomerAccountServiceTests extends BaseIntegrationTest{

    private static final log = LogFactory.getLog(this)

    SpringSecurityService springSecurityService
    CustomerAccountService customerAccountService
    CompanyService companyService
    UserProfileService userProfileService
    CompanyProfileService companyProfileService
    AmazonWebService amazonWebService
    GrailsApplication grailsApplication
    def dataSource

    private void deleteAllObjectsInTestAmazonBucket(){
        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"

        ObjectListing objectListing = amazonWebService.s3.listObjects(defaultBucket, pathPrefix)

        List objectSummaries = objectListing.objectSummaries

        objectSummaries.each { S3ObjectSummary objectSummary ->
            amazonWebService.s3.deleteObject(defaultBucket, objectSummary.key)
        }
    }

    @Before
    void setUp() {
        deleteAllData(dataSource)

        deleteAllObjectsInTestAmazonBucket()

        createCompanyTypes()
    }

    @After
    void tearDown() {
        deleteAllData(dataSource)
//        Registration.withTransaction {
//
//            // Tear down logic here
//            Registration.executeUpdate("delete from Registration")
//
//            UserRole.executeUpdate("delete from UserRole")
//            Role.executeUpdate("delete from Role")
//
//            UserProfileEmailAddress.executeUpdate("delete from UserProfileEmailAddress")
//            UserProfilePhoneNumber.executeUpdate("delete from UserProfilePhoneNumber")
//            UserProfile.executeUpdate("delete from UserProfile")
//            User.executeUpdate("delete from User")
//
//            CompanyProfileAddress.executeUpdate("delete from CompanyProfileAddress")
//            CompanyProfilePhoneNumber.executeUpdate("delete from CompanyProfilePhoneNumber")
//            CompanyProfile.executeUpdate("delete from CompanyProfile")
//            Company.executeUpdate("delete from Company")
//            EmailConfirmationLog.executeUpdate("delete from EmailConfirmationLog")
//
//            CustomerAccount.executeUpdate("delete from CustomerAccount")
//
//            CompanyType.executeUpdate("delete from CompanyType")
//            HtmlFragment.executeUpdate("delete from HtmlFragment")
//            NaicsCode.executeUpdate("delete from NaicsCode")
//            SicCode.executeUpdate("delete from SicCode")
//            SicCodeDivision.executeUpdate("delete from SicCodeDivision")
//            SupportedCountryCode.executeUpdate("delete from SupportedCountryCode")
//            SystemEmailMessageTemplate.executeUpdate("delete from SystemEmailMessageTemplate")
//        }
    }

    @Test
    void testCreate(){
        CustomerAccount customerAccount = new CustomerAccount(subDomain: "newsubdomain")
        customerAccountService.create(customerAccount)
        assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
    }

    @Test
    void testCreateCustomerAccountThenCompany(){
        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"
        assert !registration.hasErrors(), "Registration has Validation Errors"
        CustomerAccount customerAccount = new CustomerAccount(subDomain: "newsubdomain")
        customerAccountService.create(customerAccount)

        assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."
    }

    @Test
    void testProvisionCustomerAccountAmazonFileSystem(){

        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"
        assert !registration.hasErrors(), "Registration has Validation Errors"

        CustomerAccount customerAccount = new CustomerAccount(subDomain: "newsubdomain")
        customerAccountService.create(customerAccount)
        assert !customerAccount.hasErrors(), "CustomerAccount has Validation Errors."

        // Create the First User
        User user = customerAccountService.createFirstUser(customerAccount, registration)

        // create the UserProfile
        UserProfile userProfile = userProfileService.createUserProfile(user, registration)

        // create comp
        Company company

        customerAccount.withThisTenant {
            company = companyService.createCompany(registration)
        }

        // create the CompanyProfile
        CompanyProfile companyProfile = companyProfileService.createCompanyProfile(company, registration)

        assert !companyProfile.hasErrors()

        customerAccountService.provisionCustomerAccountAmazonFileSystem(customerAccount, userProfile, companyProfile)

        final String defaultBucket = grailsApplication.config.grails.plugin.awssdk.default.bucket
        final String pathPrefix = "customerAccounts/"
        final String companiesFolder = "companies"
        final String clientsFolder = "clients"
        final String imagesFolder = "images"
        final String tempFolder = "temp"

        // delete everything under customerAccounts/
        ObjectListing objectListing = amazonWebService.s3.listObjects(defaultBucket, pathPrefix)

        List objectSummaries = objectListing.objectSummaries

        assert objectSummaries.size() == 5

        objectSummaries.each { S3ObjectSummary objectSummary ->
            amazonWebService.s3.deleteObject(defaultBucket, objectSummary.key)
        }

    }


    @Test
    void testOnboardCustomerAccount() {

        Registration registration = createValidRegistration()
        assert registration.save(flush:true), "Registration did not save ${registration.errors}"

        log.debug("Calling customerAccountService.onboardCustomerAccount(registration)")
        customerAccountService.onboardCustomerAccount(registration)

        // verify CustomerAccount setup - check that the CustomerAccount was saved successfully
        CustomerAccount customerAccount = CustomerAccount.findBySubDomain(registration.subDomain)
        assert customerAccount, "CustomerAccount with subDomain ${registration.subDomain} was not created successfully"

        Long companyId = null

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
            companyId = company.id

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
        assert UserProfile.count() == 1, "UserProfile count should be 1"
        UserProfile userProfile = UserProfile.list().first()
        assert userProfile, "UserProfile was not found"
        assert userProfile.firstName.equals(registration.firstName)
        assert userProfile.lastName.equals(registration.lastName)
        assert userProfile.userProfileEmailAddresses.size() == 1, "UserProfileEmailAddress was not added successfully"
        assert userProfile.user.username.equals(registration.username), "Username of the Registration did not match the UserProfile.user.username"

        UserProfileEmailAddress upea = userProfile.userProfileEmailAddresses.first()
        assert upea.primaryEmailAddress, "primaryEmailAddress should be true"
        assert upea.emailAddress.equals(registration.emailAddress), "emailAddress does not match registration email address"

        // Verify the CompanyProfile is associated with the initial Company and its properties
        CompanyProfile companyProfile = CompanyProfile.executeQuery("from CompanyProfile cp where cp.company.id = ? ", [companyId]).first()

        assert companyProfile.company, "CompanyProfile does not have an association to a company"
        assert companyProfile.company.companyName.equals(registration.companyName)
        assert companyProfile.companyType.equals(registration.companyType)
        assert companyProfile.companyProfileAddresses.size() == 1
        CompanyProfileAddress companyProfileAddress = companyProfile.companyProfileAddresses.first()
        assert companyProfileAddress, "CompanyProfileAddress was not found"
        assert companyProfileAddress.address.addressOne.equals(registration.streetAddressOne)
        assert companyProfileAddress.address.addressTwo.equals(registration.streetAddressTwo)
        assert companyProfileAddress.address.city.equals(registration.city)
        assert companyProfileAddress.address.country.equals(registration.country)
        assert companyProfileAddress.address.county.equals(registration.county)
        assert companyProfileAddress.address.state.equals(registration.state)
        assert companyProfileAddress.address.zipcode.equals(registration.zipcode)
        assert companyProfileAddress.primaryAddress == Boolean.TRUE


        assert companyProfile.companyProfilePhoneNumbers.size() == 1
        CompanyProfilePhoneNumber companyProfilePhoneNumber = companyProfile.companyProfilePhoneNumbers.first()
        assert companyProfilePhoneNumber.primaryPhoneNumber == Boolean.TRUE
        assert companyProfilePhoneNumber.phoneNumber.phoneNumber.equals(registration.phoneNumber)

    }


}

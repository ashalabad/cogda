package com.cogda.api.admin

import com.cogda.BaseIntegrationTest
import com.cogda.api.admin.AdminRegisterController
import com.cogda.common.web.AjaxResponseDto
import com.cogda.domain.CompanyProfile
import com.cogda.domain.CompanyProfileAddress
import com.cogda.domain.CompanyProfilePhoneNumber
import com.cogda.domain.UserProfile
import com.cogda.domain.UserProfileEmailAddress
import com.cogda.domain.UserProfilePhoneNumber
import com.cogda.domain.admin.AdminService
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
import com.cogda.multitenant.Company
import com.cogda.multitenant.CustomerAccount
import com.cogda.util.ErrorMessageResolverService
import grails.converters.JSON

import static org.junit.Assert.*
import org.junit.*

class AdminRegisterControllerIntegrationTests extends BaseIntegrationTest {

    AdminService adminService
    ErrorMessageResolverService errorMessageResolverService

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {

    }

    @Test
    void testSuccessfulList() {
        def adminRegisterController = new AdminRegisterController()
        adminRegisterController.adminService = adminService
        adminRegisterController.errorMessageResolverService = errorMessageResolverService
        def expectedRegistrations = Registration.list()
        adminRegisterController.list()
        def results = adminRegisterController.response.contentAsString
        def actualResultsObj = JSON.parse(results)
        assert actualResultsObj
        assert actualResultsObj.success
        expectedRegistrations.each {

        }

    }
}

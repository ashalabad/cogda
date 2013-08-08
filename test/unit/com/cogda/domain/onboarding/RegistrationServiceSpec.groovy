package com.cogda.domain.onboarding

import com.cogda.common.RegistrationStatus
import com.cogda.domain.CompanyProfile
import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import com.cogda.security.UserService
import grails.gorm.PagedResultList
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import org.junit.*
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

@ConfineMetaClassChanges(Registration)
@TestFor(RegistrationService)
@Mock([Registration, CompanyType, Company, CompanyProfile, UserService])
@TestMixin([ServiceUnitTestMixin, DomainClassUnitTestMixin])
class RegistrationServiceSpec extends Specification {

    UserService userService
    def params = [:]

    void setup(){
        createCompanyTypes()

        def looseUserServiceMock = mockFor(UserService, true)
        looseUserServiceMock.demand.availableUsername(1..100) { String username -> true }
        userService = looseUserServiceMock.createMock()

        100.times { Integer index ->
            if (index >= 0 && index < 25){
                buildRegistration(false, RegistrationStatus.APPROVED, index, "subDomain$index")
            }

            if (index >= 25 && index < 50){
                buildRegistration(false, RegistrationStatus.REJECTED, index, null)
            }

            if (index >= 50 && index < 75){
                buildRegistration(false, RegistrationStatus.AWAITING_USER_EMAIL_CONFIRMATION, index, null)
            }
            if(index >= 75){
                buildRegistration(false, RegistrationStatus.AWAITING_COMPANY_APPROVAL, index, null)
            }
        }
    }

    def cleanup(){

    }

    def "reject registration sets the status correctly"(){
        expect:
        fail("implement me")
    }

    def "approve registration sets the status and calls onboardCustomerAccount"(){
        expect:
        fail("implement me")
    }

    def "search with no query parameter returns an unfiltered list of registrations"(){
        given:
        params.q = null
        def registrations

        when:
        registrations = service.search(params)

        then:
        registrations.size() == 10
        registrations instanceof PagedResultList
        registrations.totalCount == 100
    }

    def "search with firstName"(){
        given:
        params.q = "firstName1"
        def registrations

        when:
        registrations = service.search(params)

        then:
        registrations.size() == 10
        registrations instanceof PagedResultList
        registrations.totalCount == 10
    }

    def "search with lastName"(){
        expect:
        fail("implement me")
    }

    def "search with companyType"(){
        expect:
        fail("implement me")
    }

    def "search with params returns expected results"(){
        expect:
        fail("implement me")
    }

    private Registration buildRegistration(Boolean newCompany,
                                           RegistrationStatus registrationStatus,
                                           Integer index,
                                           String subDomain = null){
        Registration registration = new Registration()
        registration.metaClass.isDirty = { String fieldName -> false }
        registration.metaClass.getPersistentValue = { String fieldName -> this."$fieldName" }

        registration.userService = userService

        registration.firstName = "firstName$index"
        registration.lastName  = "lastName$index"
        registration.username  = "username$index"
        registration.emailAddress = "email$index@cogda.com"
        registration.password     = "password"
        registration.companyName  = "companyName$index"

        registration.companyType  = CompanyType.first()

        if(newCompany){

            registration.existingCompany = null
            registration.phoneNumber = "7777777777"
            registration.streetAddressOne = "1 Press Place"
            registration.streetAddressTwo = ""
            registration.streetAddressThree = ""
            registration.zipcode = "30601"
            registration.city = "Athens"
            registration.state = "GA"
            registration.county = "Clarke"
            registration.country = "usa"
        }else{
            registration.existingCompany = createCompany("existingCompany$index", index)
        }

        registration.registrationStatus = registrationStatus
        registration.subDomain = subDomain

        assert registration.save(), "registration save failed with errors: ${registration.errors}"
    }

    private void createCompanyTypes(){

        List codes = ["Agency/Retailer", "Carrier", "Reinsurer", "Wholesaler (MGA, Broker)"]

        codes.eachWithIndex { String code, int i ->
            if(!CompanyType.findByCode(code)){
                CompanyType companyType = new CompanyType(code:code, intCode:i.toInteger(), description: code)
                if(!companyType.save(flush:true)){
                    companyType.errors.each {
                        log.debug it
                    }
                }
            }
        }
    }


    private Company createCompany(String companyName, Integer index){
        Company company = new Company()
        CompanyProfile companyProfile = new CompanyProfile()
        company.companyName = companyName
        company.doingBusinessAs = "A Company $index"
        company.intCode = 0
        company.companyProfile = companyProfile
        company.companyType = CompanyType.first()
        company.save(failOnError:true)
        companyProfile = new CompanyProfile()
        companyProfile.company = company
        companyProfile.companyDescription = "lsllsls"
        companyProfile.published = true
        companyProfile.companyWebsite = "http://www.google.com"
        companyProfile.save(failOnError:true)
        return company
    }



}

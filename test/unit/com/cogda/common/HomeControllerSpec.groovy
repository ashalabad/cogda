package com.cogda.common

import com.cogda.domain.CompanyProfile
import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import com.cogda.multitenant.CompanySettings
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.web.ControllerUnitTestMixin
import org.junit.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HomeController)
@TestMixin([ControllerUnitTestMixin, DomainClassUnitTestMixin])
@Mock([Company, CompanyType, CompanyProfile, CompanySettings])
class HomeControllerSpec extends Specification {

    Company company
    CompanyType companyType

    def setup(){
        companyType = new CompanyType()
        companyType.code = "CARRIER"
        companyType.intCode = 0
        companyType.description = "CARRIER"
        assert companyType.save()


        company = new Company()
        company.parentCompany = null
        company.companyType = companyType
        company.companyName = "Root Company"
        company.doingBusinessAs = "Root Company"
        company.federalIdNumber = "10000slsllsls"
        company.amBestNumber = "llslslslsls"
        company.intCode = 0
        assert company.save()
    }


     def "home index defaultAction"(){
        when:
        controller.index()

        then:
        view == "/home/index"
        model.companyInstance
        model.companyInstance.companyName == company.companyName
     }
}

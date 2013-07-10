package com.cogda.domain

import com.cogda.domain.admin.CompanyType
import com.cogda.multitenant.Company
import spock.lang.Specification

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@Mock([CompanyProfile, Company, CompanyType])
class CompanyProfileSpec extends Specification{

    Company rootCompany
    CompanyProfile companyProfile
    CompanyType companyType

    void setupSpec() {
        // Setup logic here
        def looseCompanyMock = mockFor(Company, true)
        looseCompanyMock.demand.retrieveRootCompany { Company.findByParentCompany(null) }
    }

    void setup(){
        companyType = new CompanyType()
        companyType.intCode = 0
        companyType.code = "MGA"
        companyType.description = "MGA"
        companyType.save(failOnError: true)
    }

    void tearDown() {
        // Tear down logic here
    }

    def "getRootCompanyProfile should return the root company's company profile"() {
        given:
        companyProfile = new CompanyProfile()
        companyProfile.amBestNumber = "111111"
        companyProfile.company = rootCompany
        companyProfile.companyDescription = "lsllsls"
        companyProfile.published = true
        companyProfile.companyWebsite = "http://www.google.com"
        companyProfile.yearFounded = new Date()
        companyProfile.companyType = companyType

        rootCompany = new Company()
        rootCompany.companyName = "Company"
        rootCompany.doingBusinessAs = "A Company"
        rootCompany.intCode = 0
        rootCompany.companyProfile = companyProfile
        rootCompany.save(failOnError:true)
        companyProfile.company = rootCompany
        companyProfile.save(failOnError:true)

        5.times { Integer i ->

            Company c = new Company()
            c.companyName = "$i Company"
            c.doingBusinessAs = "$i Company"
            c.intCode = i
            c.parentCompany = rootCompany


            CompanyProfile cp = new CompanyProfile()
            cp.amBestNumber = "$i"
            cp.company = c
            cp.companyDescription = "$i"
            cp.published = true
            cp.companyWebsite = "http://www.google.com"
            cp.yearFounded = new Date()
            cp.companyType = companyType

            c.companyProfile = cp
            c.save()
            cp.save()

        }


        expect:
            CompanyProfile.rootCompanyProfile == companyProfile
            Company.findAllByParentCompany(rootCompany).size() == 5
    }
}

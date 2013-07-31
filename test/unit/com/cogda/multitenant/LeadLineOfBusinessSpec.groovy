package com.cogda.multitenant

import com.cogda.ConstraintUnitSpec
import com.cogda.common.LeadSubType
import com.cogda.common.LeadType
import com.cogda.domain.CompanyProfile
import com.cogda.domain.admin.BusinessType
import com.cogda.domain.admin.LineOfBusiness
import com.cogda.domain.admin.LineOfBusinessCategory
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.junit.*
import org.springframework.mock.web.MockServletContext

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(LeadLineOfBusiness)
@Mock([Lead, BusinessType, LeadLineOfBusiness, LineOfBusiness, LineOfBusinessCategory, LeadService])
@TestMixin([DomainClassUnitTestMixin, ServiceUnitTestMixin])
class LeadLineOfBusinessSpec extends ConstraintUnitSpec{

    LeadService leadService = mockService(LeadService)

    Lead lead

    BusinessType businessType

    LeadLineOfBusiness leadLineOfBusiness

    LeadLineOfBusiness secondLeadLineOfBusiness

    LineOfBusinessCategory lineOfBusinessCategory

    LineOfBusiness lineOfBusiness

    LineOfBusiness secondLineOfBusiness

    def setup(){
        mockForConstraintsTests(LeadLineOfBusiness)

        def servletContext = new MockServletContext()
        servletContext.setAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT, mainContext)
        ServletContextHolder.setServletContext(servletContext)

        lineOfBusinessCategory = new LineOfBusinessCategory()
        lineOfBusinessCategory.intCode = 0
        lineOfBusinessCategory.code = "Commercial Lines"
        lineOfBusinessCategory.description = "Commercial Lines"
        assert lineOfBusinessCategory.validate()
        assert lineOfBusinessCategory.save()

        lineOfBusiness = new LineOfBusiness()
        lineOfBusiness.code = "Agriculture"
        lineOfBusiness.description = "Agriculture"
        lineOfBusiness.intCode = 0
        lineOfBusiness.lineOfBusinessCategory = lineOfBusinessCategory
        assert lineOfBusiness.validate()
        assert lineOfBusiness.save()

        secondLineOfBusiness = new LineOfBusiness()
        secondLineOfBusiness.code = "Farming"
        secondLineOfBusiness.description = "Farming"
        secondLineOfBusiness.intCode = 0
        secondLineOfBusiness.lineOfBusinessCategory = lineOfBusinessCategory
        assert secondLineOfBusiness.validate()
        assert secondLineOfBusiness.save()

        businessType = new BusinessType()
        businessType.intCode = 0
        businessType.code = "Agriculture, Forestry, Fishing and Hunting"
        businessType.codeFrom = 11
        businessType.codeTo = 11
        assert businessType.validate()
        assert businessType.save()

        lead = new Lead()
        lead.clientId = "123456789"
        lead.clientName = "Lead Business Name"
        lead.businessType = BusinessType.first()
        lead.leadType = LeadType.PROSPECT
        lead.subType = LeadSubType.BUSINESS

        assert lead.validate()
        assert lead.save()

        leadLineOfBusiness = new LeadLineOfBusiness()
        leadLineOfBusiness.leadService = leadService
        leadLineOfBusiness.lead = lead
        leadLineOfBusiness.lineOfBusiness = lineOfBusiness
        leadLineOfBusiness.targetDate = new Date() + 10
        leadLineOfBusiness.expirationDate = new Date() + 30
        leadLineOfBusiness.remarket = true // remarket just means competition for this business in Cogda
        leadLineOfBusiness.renewal = true  // renewal just means that the insurance is a renewal
        leadLineOfBusiness.targetCommission = new BigDecimal(200.00)
        leadLineOfBusiness.targetPremium = new BigDecimal(2000.00)
        assert leadLineOfBusiness.validate()
        assert leadLineOfBusiness.save()

        lead.addToLinesOfBusiness(leadLineOfBusiness)

        secondLeadLineOfBusiness = new LeadLineOfBusiness()
        secondLeadLineOfBusiness.leadService = leadService
        secondLeadLineOfBusiness.lead = lead
        secondLeadLineOfBusiness.lineOfBusiness = secondLineOfBusiness
        secondLeadLineOfBusiness.targetDate = new Date() + 10
        secondLeadLineOfBusiness.expirationDate = new Date() + 30
        secondLeadLineOfBusiness.remarket = true // remarket just means competition for this business in Cogda
        secondLeadLineOfBusiness.renewal = true  // renewal just means that the insurance is a renewal
        secondLeadLineOfBusiness.targetCommission = new BigDecimal(200.00)
        secondLeadLineOfBusiness.targetPremium = new BigDecimal(2000.00)

        secondLeadLineOfBusiness.validate()
        assert secondLeadLineOfBusiness.save()

        lead.addToLinesOfBusiness(secondLeadLineOfBusiness)
    }

    def cleanup(){

    }

    def "test constraints on the LeadLineOfBusiness targetDate"(){
        given:
        leadLineOfBusiness.targetDate = leadLineOfBusiness.expirationDate + 20

        when:
        leadLineOfBusiness.validate()

        then:
        'leadLineOfBusiness.targetDate.max.exceeded' == leadLineOfBusiness.errors['targetDate']
    }

    def "test constraints on the LeadLineOfBusiness expirationDate nullable"(){
        given:
        leadLineOfBusiness.expirationDate = null

        when:
        leadLineOfBusiness.validate()

        then:
        'nullable' == leadLineOfBusiness.errors['expirationDate']
    }

    def "test constraints on the LeadLineOfBusiness expirationDate"(){
        given:
        leadLineOfBusiness.expirationDate = new Date() - 7

        when:
        leadLineOfBusiness.validate()

        then:
        'leadLineOfBusiness.expirationDate.min.exceeded' == leadLineOfBusiness.errors['expirationDate']
    }

    def "test constraints on the targetCommission"(){
        given:
        leadLineOfBusiness.targetPremium = leadLineOfBusiness.targetCommission - 5

        when:
        leadLineOfBusiness.validate()

        then:
        'leadLineOfBusiness.targetCommission.exceeds.targetPremium' == leadLineOfBusiness.errors['targetCommission']
    }

    def "test lineOfBusiness on the LeadLineOfBusiness"(){
        given:
        leadLineOfBusiness.lineOfBusiness = null

        when:
        leadLineOfBusiness.validate()

        then:
        'nullable' == leadLineOfBusiness.errors['lineOfBusiness']
    }


}

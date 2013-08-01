package com.cogda.multitenant

import com.cogda.common.LeadSubType
import com.cogda.common.LeadType
import com.cogda.domain.admin.BusinessType
import com.cogda.domain.admin.LineOfBusiness
import com.cogda.domain.admin.LineOfBusinessCategory
import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.junit.*
import org.springframework.mock.web.MockServletContext
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(LeadService)
@TestMixin(DomainClassUnitTestMixin)
@Mock([Lead, BusinessType, LeadLineOfBusiness, LineOfBusiness, LineOfBusinessCategory])
class LeadServiceSpec extends Specification {



    Lead lead

    BusinessType businessType

    LeadLineOfBusiness leadLineOfBusiness

    LeadLineOfBusiness secondLeadLineOfBusiness

    LineOfBusinessCategory lineOfBusinessCategory

    LineOfBusiness lineOfBusiness

    LineOfBusiness secondLineOfBusiness

    def setup(){
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
        leadLineOfBusiness.leadService = service
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
        secondLeadLineOfBusiness.leadService = service
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

    def "findLeadLineOfBusinessesByLob id and lineOfBusiness finds the right LeadLineOfBusiness"() {
        when:
        def leadLinesOfBusinessWithMatchingLob = service.findLeadLineOfBusinessesByLob(lead.id, lineOfBusiness)

        then:
        leadLinesOfBusinessWithMatchingLob
        leadLinesOfBusinessWithMatchingLob.size() == 1
        leadLinesOfBusinessWithMatchingLob.first().lineOfBusiness == lineOfBusiness
    }

    def "findLeadLineOfBusinessesByLobAndExpirationDate id, lineOfBusiness, expirationDate finds the right LeadLineOfBusiness"() {
        given:
        Date todayPlus180 = new Date() + 180
        def outside180Days = createLeadLineOfBusiness()
        outside180Days.validate()
        println outside180Days.errors
        assert outside180Days.save()
        lead.addToLinesOfBusiness(outside180Days)

        when:
        def leadLinesOfBusinessWithMatchingLob = service.findLeadLineOfBusinessesByLobAndExpirationDate(lead.id, lineOfBusiness, todayPlus180)

        then:
        leadLinesOfBusinessWithMatchingLob
        leadLinesOfBusinessWithMatchingLob.size() == 1
        leadLinesOfBusinessWithMatchingLob.first().lineOfBusiness == lineOfBusiness
    }

    private LeadLineOfBusiness createLeadLineOfBusiness(){
        LeadLineOfBusiness thirdLeadLineOfBusiness = new LeadLineOfBusiness()
        thirdLeadLineOfBusiness = new LeadLineOfBusiness()
        thirdLeadLineOfBusiness.leadService = service
        thirdLeadLineOfBusiness.lead = lead
        thirdLeadLineOfBusiness.lineOfBusiness = lineOfBusiness
        thirdLeadLineOfBusiness.targetDate = new Date() + 10
        thirdLeadLineOfBusiness.expirationDate = (new Date() + 181)
        thirdLeadLineOfBusiness.remarket = true // remarket just means competition for this business in Cogda
        thirdLeadLineOfBusiness.renewal = true  // renewal just means that the insurance is a renewal
        thirdLeadLineOfBusiness.targetCommission = new BigDecimal(200.00)
        thirdLeadLineOfBusiness.targetPremium = new BigDecimal(2000.00)
        return thirdLeadLineOfBusiness
    }

}

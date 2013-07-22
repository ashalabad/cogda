package com.cogda.domain

import com.cogda.common.LeadSubType
import com.cogda.common.LeadType
import com.cogda.domain.Submission
import com.cogda.domain.admin.BusinessType
import com.cogda.domain.security.User
import com.cogda.multitenant.Lead
import grails.plugins.springsecurity.SpringSecurityService
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes
import org.springframework.context.support.GenericApplicationContext
import spock.lang.Specification
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import org.springframework.mock.web.MockServletContext
import org.springframework.context.support.StaticMessageSource
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.codehaus.groovy.grails.support.MockApplicationContext
import org.springframework.context.ApplicationContext

import javax.servlet.ServletContext

@TestFor(Submission)
@TestMixin([DomainClassUnitTestMixin])
@Mock([Lead, User, BusinessType])
class SubmissionSpec extends Specification {
    Submission submission
    User user
    Lead lead
    LeadType leadType
    LeadSubType leadSubType
    BusinessType businessType

    void tearDown() {
        // Tear down logic here
    }


    void setup(){

        def looseSpringSecurityServiceMock = mockFor(SpringSecurityService, true)
        looseSpringSecurityServiceMock.demand.encodePassword { UUID.randomUUID().toString().replaceAll('-', '') }

        def servletContext = new MockServletContext()
        servletContext.setAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT, mainContext)
        ServletContextHolder.setServletContext(servletContext)

        user = new User()
        user.springSecurityService = looseSpringSecurityServiceMock.createMock()
        user.username = UUID.randomUUID().toString().replaceAll('-', '')
        user.enabled = true
        user.accountExpired = false
        user.password = "password"
        assert user.validate()
        assert user.save()

        businessType = new BusinessType()
        businessType.intCode = 0
        businessType.code = "Agriculture, Forestry, Fishing and Hunting"
        businessType.codeFrom = 11
        businessType.codeTo = 11
        assert businessType.validate()
        assert businessType.save()

        leadType = LeadType.PROSPECT

        lead = new Lead()
        lead.clientId = "1234567890"
        lead.clientName = "Testing Client"
        lead.businessType = businessType
        lead.leadType = leadType
        lead.subType = LeadSubType.BUSINESS

        assert lead.validate()
        assert lead.save()

        submission = new Submission()
        submission.lead = lead
        submission.parentSubmission = null
        submission.createdBy = user

        assert submission.validate()

    }

    def "assert the default submission in this test is valid"(){
        when:
        submission.save()

        then:
        assert !submission.hasErrors(), "Fix the Submission that is built in the setup() method of this SubmissionSpec!"
    }

    def "Save the root Submission will fail without an associated Lead"() {
        given:
        submission.lead = null
        submission.parentSubmission = null

        when:
        submission.save()

        then:
        assert submission.hasErrors()
        assertNotNull submission.errors["lead"]
    }

    def "Save the root Submission will fail without an associated createdBy User"() {
        given:
        submission.createdBy = null
        submission.parentSubmission = null

        when:
        submission.save()

        then:
        assert submission.hasErrors()
        assert submission.errors["createdBy"]
    }

    def "isChild correctly identifies Child Submissions"() {
        given:
        Submission childSubmission = new Submission()
        childSubmission.lead = null
        childSubmission.parentSubmission = submission
        childSubmission.createdBy = user

        when:
        childSubmission.save()

        then:
        assert !childSubmission.hasErrors()
        assert childSubmission.lead == null
        assert childSubmission.isChild()
    }

    def "Child Submission does not require a Lead"() {
        given:
        Submission childSubmission = new Submission()
        childSubmission.lead = null
        childSubmission.parentSubmission = submission
        childSubmission.createdBy = user

        when:
        childSubmission.save()

        then:
        assert childSubmission.isChild()
        assert childSubmission.lead == null
        assert !childSubmission.hasErrors()
    }

    def "retrieveLead returns the root Submission Lead from a deeply nested ChildSubmission"(){
        given:
        Submission firstChild = new Submission()
        firstChild.lead = null
        firstChild.parentSubmission = submission
        firstChild.createdBy = user

        Submission secondChild = new Submission()
        secondChild.lead = null
        secondChild.parentSubmission = firstChild
        secondChild.createdBy = user

        Submission thirdChild = new Submission()
        thirdChild.lead = null
        thirdChild.parentSubmission = secondChild
        thirdChild.createdBy = user

        when:
        firstChild.save(failOnError: true)
        secondChild.save(failOnError: true)
        thirdChild.save(failOnError: true)

        then:
        assert !firstChild.hasErrors()
        assert !secondChild.hasErrors()
        assert !thirdChild.hasErrors()

        assert firstChild.isChild()
        assert secondChild.isChild()
        assert thirdChild.isChild()

        assert lead == thirdChild.retrieveLead()
        assert lead == secondChild.retrieveLead()
        assert lead == firstChild.retrieveLead()
    }
}

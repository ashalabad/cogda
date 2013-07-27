package com.cogda.multitenant

import grails.test.GrailsUnitTestCase
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Environment
import grails.util.Metadata
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest
import spock.lang.Specification
import grails.test.mixin.TestFor
import grails.test.mixin.web.ControllerUnitTestMixin

@TestMixin([GrailsUnitTestMixin, DomainClassUnitTestMixin])
@Mock([CustomerAccount])
class DomainTenantResolverSpec extends Specification {

    /**
     * The {@link org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest} object
     */
    GrailsMockHttpServletRequest request

    def setup(){
        ["NOTHER", "OTHER"].each { String subDomain ->
            CustomerAccount customerAccount = new CustomerAccount()
            customerAccount.subDomain = subDomain
            assert customerAccount.validate()
            assert customerAccount.save()
        }
        assert CustomerAccount.list().size() == 2
    }

    def cleanup() {
        System.setProperty(Environment.KEY, "")
        System.setProperty(Environment.RELOAD_ENABLED, "")
        System.setProperty(Environment.RELOAD_LOCATION, "")

        Metadata.getCurrent().clear()
    }

    private setExecutionEnvironment(String environmentName){
        System.setProperty("grails.env", Environment.PRODUCTION.name)
    }

    def "resolve function returns null for the CustomerAccount id when Environment is PRODUCTION and CustomerAccount is not found"(){
        given:
        setExecutionEnvironment(Environment.PRODUCTION.name)
        request = new GrailsMockHttpServletRequest()
        request.serverName = "cogdatwo.com"
        DomainTenantResolver domainTenantResolver = new DomainTenantResolver()
        Integer tenantId = Integer.MAX_VALUE

        when:
        tenantId = domainTenantResolver.resolve(request)

        then:
        assert Environment.current == Environment.PRODUCTION
        assertNull tenantId
        assert request.customerAccount == -1
        assert request.domain == ""
    }

    def "resolve function returns the expected CustomerAccount id when Environment is PRODUCTION and CustomerAccount is found"(){
        expect:
        fail("implement me")
    }

    def "resolve function returns null for the CustomerAccount when Environment is Custom and CustomerAccount is not found"(){
        expect:
        fail("implement me")
    }

    def "resolve function returns the expected CustomerAccount id when Environment is Custom and CustomerAccount is found"(){
        expect:
        fail("implement me")
    }

    def "resolve function returns null for the CustomerAccount when Environment is DEVELOPMENT and CustomerAccount is not found"(){
        expect:
        fail("implement me")
    }

    def "resolve function returns the expected CustomerAccount id when Environment is DEVELOPMENT and CustomerAccount is found"(){
        expect:
        fail("implement me")
    }

    def "resolve function returns null for the CustomerAccount when Environment is TEST and CustomerAccount is not found"(){
        expect:
        fail("implement me")
    }

    def "resolve function returns the expected CustomerAccount id when Environment is TEST and CustomerAccount is found"(){
        expect:
        fail("implement me")
    }

}
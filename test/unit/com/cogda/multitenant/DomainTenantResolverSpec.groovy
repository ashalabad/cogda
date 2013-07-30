package com.cogda.multitenant

import grails.test.GrailsUnitTestCase
import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.util.Environment
import grails.util.Metadata
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest
import spock.lang.Specification
import grails.test.mixin.TestFor
import grails.test.mixin.web.ControllerUnitTestMixin

/**
 * setup:
 * when:
 * then:
 * expect:
 * cleanup:
 * where:
 */
@TestMixin([GrailsUnitTestMixin, DomainClassUnitTestMixin])
@Mock([CustomerAccount])
class DomainTenantResolverSpec extends Specification {

    /**
     * The {@link org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest} object
     */
    GrailsMockHttpServletRequest request = new GrailsMockHttpServletRequest()
    DomainTenantResolver domainTenantResolver = new DomainTenantResolver()
    Integer tenantId = Integer.MAX_VALUE

    CustomerAccount caSoldier = new CustomerAccount(subDomain:"SOLDIER")
    CustomerAccount caBrother = new CustomerAccount(subDomain:"BROTHER")

    String productionDomainURL = "cogdatwo.com"
    String stagingDomainURL = "staging.cogdatwo.com"
    String developDomainURL = "develop.cogdatwo.com"
    String developmentDomainURL = "cogdalocal.com:8080"
    String developmentDomainBase = "cogdalocal.com"
    def setup(){
        domainTenantResolver.grailsApplication = grailsApplication
        assert caSoldier.save()
        assert caBrother.save()
        assert CustomerAccount.list().size() == 2
    }

    def cleanup() {
        System.setProperty(Environment.KEY, "")
        System.setProperty(Environment.RELOAD_ENABLED, "")
        System.setProperty(Environment.RELOAD_LOCATION, "")

        Metadata.getCurrent().clear()
    }

    private setExecutionEnvironment(String environmentName){
        System.setProperty("grails.env", environmentName)
    }

    def "resolve function returns null for the CustomerAccount id when Environment is PRODUCTION and no subdomain is provided"(){
        given: "an execution environment of production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        and: "verify that the execution environment was set to production"
        assert Environment.current == Environment.PRODUCTION

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", productionDomainURL)

        and: "the user issues a request to"
        request.serverName = productionDomainURL

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will not find a tenant"
        tenantId == null
        request.customerAccount == -1
        request.domain == ""
    }


    def "resolve function returns null for the CustomerAccount id when Environment is PRODUCTION and CustomerAccount is not found"(){
        given: "an execution environment of production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        and: "verify that the execution environment was set to production"
        assert Environment.current == Environment.PRODUCTION

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", productionDomainURL)

        and: "the user issues a request to"
        request.serverName = "cogdasolutionsllc." + productionDomainURL   // cogdasolutionsllc is in DISALLOWED_SUBDOMAINS

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will not find a tenant at that subDomain"
        tenantId == null
        request.customerAccount == -1
        request.domain == ""
    }

    def "resolve function returns expected CustomerAccount id regardless of mixedcase subdomain"(){

        given: "an execution environment of production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        and: "verify that the execution environment was set to production"
        assert Environment.current == Environment.PRODUCTION

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", productionDomainURL)

        and: "the user issues a request to a valid subDomain"
        request.serverName = "${caBrother.subDomain.toUpperCase()}.${productionDomainURL}"

        when: "we resolve the request"
        tenantId = domainTenantResolver.resolve(request)

        then: "we receive the correct CustomerAccount.id and request parameters are set"
        tenantId == caBrother.id
        request.customerAccount == caBrother.id
        request.domain == caBrother.subDomain
    }

    def "resolve function returns the expected CustomerAccount id when Environment is PRODUCTION and CustomerAccount is found"(){
        given: "an execution environment of production"
        setExecutionEnvironment(Environment.PRODUCTION.name)

        and: "verify that the execution environment was set to production"
        assert Environment.current == Environment.PRODUCTION

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", productionDomainURL)

        and: "the user issues a request to a valid subDomain"
        request.serverName = "${caBrother.subDomain}.${productionDomainURL}"

        when: "we resolve the request"
        tenantId = domainTenantResolver.resolve(request)

        then: "we receive the correct CustomerAccount.id and request parameters are set"
        tenantId == caBrother.id
        request.customerAccount == caBrother.id
        request.domain == caBrother.subDomain
    }

    def "resolve function returns null for the CustomerAccount when Environment is Custom and no subdomain is provided"(){
        given: "an execution environment of custom"
        setExecutionEnvironment("staging")

        and: "verify that the execution environment was set to staging"
        assert Environment.current == Environment.CUSTOM
        assert Environment.current.name == "staging"

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", stagingDomainURL)

        and: "the user issues a request to"
        request.serverName = stagingDomainURL

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will not find a tenant"
        tenantId == null
        request.customerAccount == -1
        request.domain == ""
    }

    def "resolve function returns the expected CustomerAccount id when Environment is Custom and CustomerAccount is found"(){
        given: "an execution environment of custom"
        setExecutionEnvironment("staging")

        and: "verify that the execution environment was set to staging"
        assert Environment.current == Environment.CUSTOM
        assert Environment.current.name == "staging"

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", stagingDomainURL)

        and: "the user issues a request to"
        request.serverName = "${caBrother.subDomain}.${stagingDomainURL}"

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will find a tenant"
        tenantId == caBrother.id
        request.customerAccount == caBrother.id
        request.domain == caBrother.subDomain
    }

    def "resolve function returns null for the CustomerAccount when Environment is Custom develop and no subdomain is provided"(){
        given: "an execution environment of custom"
        setExecutionEnvironment("develop")

        and: "verify that the execution environment was set to develop"
        assert Environment.current == Environment.CUSTOM
        assert Environment.current.name == "develop"

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", developDomainURL)

        and: "the user issues a request to"
        request.serverName = developDomainURL

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will not find a tenant"
        tenantId == null
        request.customerAccount == -1
        request.domain == ""
    }

    def "resolve function returns the expected CustomerAccount id when Environment is Custom develop and CustomerAccount is found"(){
        given: "an execution environment of custom"
        setExecutionEnvironment("develop")

        and: "verify that the execution environment was set to staging"
        assert Environment.current == Environment.CUSTOM
        assert Environment.current.name == "develop"

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", developDomainURL)

        and: "the user issues a request to"
        request.serverName = "${caBrother.subDomain}.${developDomainURL}"

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will find a tenant"
        tenantId == caBrother.id
        request.customerAccount == caBrother.id
        request.domain == caBrother.subDomain
    }

    def "resolve function returns null for the CustomerAccount when Environment is development and no subdomain is provided"(){
        given: "an execution environment of custom"
        setExecutionEnvironment(Environment.DEVELOPMENT.name)

        and: "verify that the execution environment was set to develop"
        assert Environment.current == Environment.DEVELOPMENT

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", developmentDomainURL)

        and: "the user issues a request to"
        request.serverName = developmentDomainBase

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will not find a tenant"
        tenantId == null
        request.customerAccount == -1
        request.domain == ""
    }

    def "resolve function returns the expected CustomerAccount id when Environment is development and CustomerAccount is found"(){
        given: "an execution environment of custom"
        setExecutionEnvironment(Environment.DEVELOPMENT.name)

        and: "verify that the execution environment was set to develop"
        assert Environment.current == Environment.DEVELOPMENT

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", developmentDomainURL)

        and: "the user issues a request to"
        request.serverName = "${caBrother.subDomain}.${developmentDomainBase}"

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will find a tenant"
        tenantId == caBrother.id
        request.customerAccount == caBrother.id
        request.domain == caBrother.subDomain
    }

    def "resolve function returns null for the CustomerAccount when Environment is TEST and CustomerAccount is not found"(){
        given: "an execution environment of custom"
        setExecutionEnvironment(Environment.TEST.name)

        and: "verify that the execution environment was set to develop"
        assert Environment.current == Environment.TEST

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", developmentDomainURL)

        and: "the user issues a request to"
        request.serverName = developmentDomainBase

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will not find a tenant"
        tenantId == null
        request.customerAccount == -1
        request.domain == ""
    }

    def "resolve function returns the expected CustomerAccount id when Environment is TEST and CustomerAccount is found"(){
        given: "an execution environment of custom"
        setExecutionEnvironment(Environment.TEST.name)

        and: "verify that the execution environment was set to develop"
        assert Environment.current == Environment.TEST

        and: "the environment configuration domainURL is set to"
        grailsApplication.config.grails.put("domainURL", developmentDomainURL)

        and: "the user issues a request to"
        request.serverName = "${caBrother.subDomain}.${developmentDomainURL}"

        when: "we attempt to resolve a tenant"
        tenantId = domainTenantResolver.resolve(request)

        // then blocks are restricted to conditions, exception conditions, interactions, and variable definitions
        then: "we will find a tenant"
        tenantId == caBrother.id
        request.customerAccount == caBrother.id
        request.domain == caBrother.subDomain
    }

}
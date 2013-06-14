package com.cogda.multitenant



import grails.test.mixin.*
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(HomeController)
@TestMixin(DomainClassUnitTestMixin)
class HomeControllerTests {

    void testIndex() {

        mockDomain(Company)
        mockDomain(CustomerAccount, [new CustomerAccount(subDomain: "newAccount", id:1)])
        mockDomain(Company, [new Company(parentCompany: null, companyName:"testcompany", doingBusinessAs:"testcompany", intCode:0, )])

        request.method = "GET"
        request.putAt("customerAccount", 1)

        controller.index()

        assert view == "/home/index"
        assert model.companyInstance
        assert model.customerAccountInstance




    }
}

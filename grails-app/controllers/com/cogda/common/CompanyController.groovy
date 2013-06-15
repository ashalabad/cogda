package com.cogda.common

import com.cogda.multitenant.Company
import com.cogda.multitenant.CompanyService
import grails.converters.JSON

/**
 * CompanyController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class CompanyController {

    CompanyService companyService

    /**
     * Returns a list of map objects
     */
    def typeahead(){
        String q = params.q
        List returnList = []

        if(!q){
            render returnList as JSON
            return
        }

        List companies = companyService.find(q)

        println companies
        List companyInstances = companies.collect { Company company ->
            returnList.add([companyName:company.prettyCompanyString(), companyId:company.id])
        }

        render returnList as JSON
        return
    }
}

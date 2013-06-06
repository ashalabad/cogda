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
        String q = params.query
        Map returnMap = [options:[]]

        if(!q){
            render returnMap as JSON
            return
        }

        List companies = companyService.find(q)
        List companyInstances = companies.collect { Company company ->
            returnMap.options.add(company.prettyCompanyString())
        }

        render returnMap as JSON
        return
    }
}

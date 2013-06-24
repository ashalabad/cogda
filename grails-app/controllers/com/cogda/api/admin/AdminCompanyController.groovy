package com.cogda.api.admin

import com.cogda.domain.admin.AdminCompanyService
import com.cogda.multitenant.Company
import grails.converters.XML
import grails.plugin.gson.converters.GSON

import static grails.plugin.gson.http.HttpConstants.X_PAGINATION_TOTAL
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

/**
 * AdminCompanyController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AdminCompanyController {

    static allowedMethods = [list: 'GET', show: 'GET']

    AdminCompanyService adminCompanyService

    def list() {
        response.addIntHeader X_PAGINATION_TOTAL, adminCompanyService.companyCount()
        def registrations = adminCompanyService.listCompanies(params)
        withFormat {
            json { render registrations as GSON }
            xml { render registrations as XML }
        }
    }

    def get(long id) {
        Company company = adminCompanyService.get(id)
        if (!company){
            respondNotFound(id)
        }
        withFormat {
            json {render company as GSON }
            xml { render company as XML }
        }
    }

    def typeahead(){
        String q = params.q
        List returnList = []

        if(!q){
            withFormat {
                json  {render returnList as GSON}
                xml { render returnList as XML }
            }
            return
        }

        List companies = adminCompanyService.find(q)

        companies.collect { Company company ->
            returnList.add([companyName:company.prettyCompanyString(), companyId:company.id])
        }

        withFormat {
            json { render returnList as GSON }
            xml { render returnList as XML }
        }
        return
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'company.label', default: 'Company'), id])
        response.status = SC_NOT_FOUND
        withFormat {
            json { render responseBody as GSON }
            xml { render responseBody as XML }
        }
    }
}

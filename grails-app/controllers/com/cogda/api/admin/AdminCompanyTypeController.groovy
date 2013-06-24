package com.cogda.api.admin

import com.cogda.domain.admin.CompanyType
import grails.converters.XML
import grails.plugin.gson.converters.GSON

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

/**
 * AdminCompanyTypeController
 * A controller class handles incoming web requests and performs actions such as redirects, rendering views and so on.
 */
class AdminCompanyTypeController {

    def get(long id) {
        CompanyType companyType = CompanyType.get(id)
        if (!companyType) {
            respondNotFound(id)
        }
        withFormat {
            json { render companyType as GSON }
            xml { render companyType as GSON }
        }
    }

    def exists(long id) {
        render CompanyType.exists(id)
    }

    private void respondNotFound(id) {
        def responseBody = [:]
        responseBody.message = message(code: 'default.not.found.message', args: [message(code: 'registration.companyType.label', default: 'CompanyType'), id])
        response.status = SC_NOT_FOUND
        withFormat {
            json { render responseBody as GSON }
            xml { render responseBody as XML }
        }
    }
}

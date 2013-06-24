package com.cogda.domain.admin

import com.cogda.multitenant.Company
import com.cogda.multitenant.CompanyService
import com.cogda.multitenant.CustomerAccount

/**
 * AdminCompanyService
 * A service class encapsulates the core business logic of a Grails application
 */
class AdminCompanyService {
    CompanyService companyService

    List<Company> listCompanies(params) {
        params.fetch = [emailConfirmationLogs: "eager"]
        List<Company> companyList
        CustomerAccount.withoutTenantRestriction {
            companyService.list()
        }
        return companyList
    }

    int companyCount() {
        int companyCount
        CustomerAccount.withoutTenantRestriction {
            companyCount companyService.count()
        }
        return companyCount
    }

    def get(long companyId) {
        Company company
        CustomerAccount.withoutTenantRestriction {
            company = companyService.get(companyId)
        }
        return company
    }

    List<Company> find(String qParam) {
        List<Company> companies
        CustomerAccount.withoutTenantRestriction {
            companies = companyService.find(qParam)
        }
        return companies
    }
}

package com.cogda.domain.admin

/**
 * CompanyTypeService
 * A service class encapsulates the core business logic of a Grails application
 */
class CompanyTypeService {

    static transactional = true

    def get(long id) {
        return CompanyType.get(id)
    }
}

package com.cogda.multitenant

import com.cogda.domain.onboarding.Registration
import org.apache.commons.logging.LogFactory

/**
 * CompanyService
 * A service class encapsulates the core business logic of a Grails application
 */
class CompanyService {

    private static final log = LogFactory.getLog(this)

    /**
     * Lists all Company domain classes in the system.
     */
    def list(Map params) {
        return Company.list(params)
    }

    /**
     * Returns a list of Company domain class objects
     * based on the passed in qParam.
     * The query checks for a matching Company.name or matching
     * Company.doingBusinessAs
     *
     * @param qParam
     * @return List
     */
    List<Company> find(String qParam){
        qParam = "%" + qParam + "%"
        return Company.executeQuery("from Company c where c.companyName like :q or c.doingBusinessAs like :qTwo order by c.companyName",
                [q:qParam, qTwo:qParam])
    }

    Company findById(Serializable id, Map params) {
        Company.findById(id, params)
    }

    /**
     * Creates a company based on a Registration object.
     * @return Company
     */
    Company createCompany(Registration registration){
        Company company = new Company()

        company.companyName = registration.companyName
        company.doingBusinessAs = registration.companyName
        company.parentCompany = null
        company.intCode = 0

        if(!company.validate()){
            company.errors.each {
                log.error it
            }
        }

        company.save()

        return company
    }

    int count() {
        return Company.count
    }

    def get(Serializable id) {
        return Company.get(id)
    }
}

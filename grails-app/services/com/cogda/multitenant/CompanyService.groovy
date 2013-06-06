package com.cogda.multitenant

/**
 * CompanyService
 * A service class encapsulates the core business logic of a Grails application
 */
class CompanyService {

    /**
     * Lists all Company domain classes in the system.
     */
    def list() {
        return Company.list()
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
        return Company.executeQuery("from Company c where c.name like :q or c.doingBusinessAs like :qTwo order by c.name",
                [q:qParam, qTwo:qParam])
    }
}

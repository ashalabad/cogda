package com.cogda.multitenant
import grails.plugin.multitenant.core.Tenant
import groovy.transform.ToString

/**
 * CustomerAccount
 * A domain class describes the data object and it's mapping to the database
 */
@ToString(includeNames=true, includeFields=true)
class CustomerAccount implements Tenant {
    private static List<String> DISALLOWED_SUBDOMAINS = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "cogda", "sombra", "cogdasolutions", "cogdasolutionsllc",
            "test", "testdrive", "demo", "testing", "sales", "marketing", "development", "api", "staging"]

    String subDomain

    String accountId = UUID.randomUUID().toString().replaceAll('-', '')

    static constraints = {
        subDomain(nullable:false, blank:false, unique:true, minSize:2, matches:"[A-Za-z]+", validator: { val, obj ->
            if(CustomerAccount.DISALLOWED_SUBDOMAINS.contains(val)){
                return ['customerAccount.subDomain.invalid']
            }
        })
        accountId(nullable:false, blank:false, unique:true)
    }

    Integer tenantId() {
        return this.id
    }

    /**
     * Verifies if the passed in subDomain is available
     * @param subDomain
     * @return
     */
    static Boolean availableSubDomain(String subDomain){
        def c = CustomerAccount.createCriteria()
        def list = c.list {
            eq("subDomain", subDomain, [ignoreCase:true])
        }
        if(!list.isEmpty()){
            return Boolean.FALSE
        }
        return Boolean.TRUE
    }

    /**
     * Sets the subDomain to lowerCase regardless of the case
     * that was passed into this domain class.
     * @param subDomain
     */
    void setSubDomain(String subDomain){
        this.subDomain = subDomain?.toLowerCase()
    }
}

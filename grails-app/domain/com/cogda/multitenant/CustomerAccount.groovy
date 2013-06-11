package com.cogda.multitenant
import grails.plugin.multitenant.core.Tenant
/**
 * CustomerAccount
 * A domain class describes the data object and it's mapping to the database
 */
class CustomerAccount implements Tenant {
    private static List<String> DISALLOWED_SUBDOMAINS = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "cogda", "sombra", "cogdasolutions", "cogdasolutionsllc", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
            "test", "testdrive", "demo", "testing", "sales", "marketing", "development", "api"]

    String subDomain

    static constraints = {
        subDomain(nullable:false, blank:false, unique:true, minSize:2, validator: { val, obj ->
            if(CustomerAccount.DISALLOWED_SUBDOMAINS.contains(val)){
                return ['customerAccount.subDomain.invalid']
            }
        })
    }

    Integer tenantId() {
        id
    }
}

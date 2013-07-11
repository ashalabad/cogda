package com.cogda.security

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import grails.plugin.multitenant.core.annotation.MultiTenant
import grails.plugin.multitenant.core.ast.MultiTenantAST
/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 7/11/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
class SecuritySerializationExclusionStrategy implements ExclusionStrategy {
    @Override
    boolean shouldSkipField(FieldAttributes f) {
        // remove any MultiTenant classes tenantId field from serialization by default
        if(f.name.equals(MultiTenantAST.TENANT_ID_FIELD_NAME)){
            return true
        }
        if(f.name.equals("accountId")){
            return true
        }

        return false  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    boolean shouldSkipClass(Class<?> clazz) {
        return false  //To change body of implemented methods use File | Settings | File Templates.
    }
}

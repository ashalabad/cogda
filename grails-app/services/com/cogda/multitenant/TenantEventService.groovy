package com.cogda.multitenant

import org.springframework.beans.factory.InitializingBean

/**
 * TenantEventService
 * A service class encapsulates the core business logic of a Grails application
 */
class TenantEventService implements InitializingBean {

    def eventBroker

    void afterPropertiesSet() {
        eventBroker.subscribe("tenant.created") { evt ->
            println "New tenant created: " + evt.payload
        }
    }
}

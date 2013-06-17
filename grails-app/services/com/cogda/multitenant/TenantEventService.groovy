package com.cogda.multitenant

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.InitializingBean

/**
 * TenantEventService
 * A service class encapsulates the core business logic of a Grails application
 */
class TenantEventService implements InitializingBean {
    private static final log = LogFactory.getLog(this)
    def eventBroker

    void afterPropertiesSet() {
        eventBroker.subscribe("tenant.created") { evt ->
            log.info "New tenant created: " + evt.payload
        }
    }
}

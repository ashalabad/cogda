package com.cogda.common

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes

/**
 * Created with IntelliJ IDEA.
 * User: chewy
 * Date: 7/2/13
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public enum LeadSubType {
    BUSINESS(0), INDIVIDUAL(1)
    def ctx = ServletContextHolder.servletContext.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
    def messageSource = ctx.messageSource

    LeadSubType(int value) {
        this.value = value
    }

    private final int value

    public int value() { return value }

    public String getLabel() {
        switch (value) {
            case 0:
                return messageSource.getMessage("lead.leadSubType.business.label", null, 'Business', Locale.default)
                break
            case 1:
                return messageSource.getMessage("lead.leadSubType.individual.label", null, 'Individual', Locale.default)
                break
            default:
                throw new IndexOutOfBoundsException()
        }
    }
}
<%@ page import="com.cogda.multitenant.LeadContactEmailAddress" %>
<div id="${prefix}">
    <div class="control-group fieldcontain ${hasErrors(bean: leadContactEmailAddress, field: 'emailAddress', 'error')} ">
        <label for="${prefix}emailAddress" class="control-label"><g:message code="leadContactEmailAddress.emailAddress.label"
                                                                   default="Email Address"/></label>

        <div class="controls">
            <g:field type="email" name="${prefix}emailAddress" value="${leadContactEmailAddress?.emailAddress}"/>
            <span class="help-inline">${hasErrors(bean: leadContactEmailAddress, field: 'emailAddress', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactEmailAddress, field: 'primaryEmailAddress', 'error')} ">
        <label for="${prefix}primaryEmailAddress" class="control-label"><g:message
                code="leadContactEmailAddress.primaryEmailAddress.label" default="Primary Email Address"/></label>

        <div class="controls">
            <bs:checkBox name="${prefix}primaryEmailAddress" value="${leadContactEmailAddress?.primaryEmailAddress}"/>
            <span class="help-inline">${hasErrors(bean: leadContactEmailAddress, field: 'primaryEmailAddress', 'error')}</span>
        </div>
    </div>
</div>
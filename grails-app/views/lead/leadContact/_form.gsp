<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadContact" %>
<div id="${prefix}">
<fieldset class="embedded"><legend><g:message code="leadContact.label" default="Contact"/></legend>

<div class="control-group fieldcontain ${hasErrors(bean: leadContactInstance, field: 'firstName', 'error')} required">
    <label for="${prefix}firstName" class="control-label"><g:message code="leadContact.firstName.label"
                                                            default="First Name"/><span
            class="required-indicator">*</span></label>

    <div class="controls">
        <g:textField name="${prefix}firstName" required="" value="${leadContactInstance?.firstName}"/>
        <span class="help-inline">${hasErrors(bean: leadContactInstance, field: 'firstName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadContactInstance, field: 'middleName', 'error')} ">
    <label for="${prefix}middleName" class="control-label"><g:message code="leadContact.middleName.label"
                                                             default="Middle Name"/></label>

    <div class="controls">
        <g:textField name="${prefix}middleName" value="${leadContactInstance?.middleName}"/>
        <span class="help-inline">${hasErrors(bean: leadContactInstance, field: 'middleName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadContactInstance, field: 'lastName', 'error')} required">
    <label for="${prefix}lastName" class="control-label"><g:message code="leadContact.lastName.label" default="Last Name"/><span
            class="required-indicator">*</span></label>

    <div class="controls">
        <g:textField name="${prefix}lastName" required="" value="${leadContactInstance?.lastName}"/>
        <span class="help-inline">${hasErrors(bean: leadContactInstance, field: 'lastName', 'error')}</span>
    </div>
</div>

%{--<g:render template="/leadContactAddress/_form" model="[leadContactAddressInstance = leadContactInstance?.leadContactAddresses?.first()]"/>--}%

<g:render template="/lead/leadContactEmailAddress/leadContactEmailAddresses" model="['leadContactInstance': leadContactInstance, 'prefix': prefix]"/>
<g:render template="/lead/leadContactPhoneNumber/leadContactPhoneNumbers" model="['leadContactInstance': leadContactInstance, 'prefix': prefix]"/>
<g:render template="/lead/leadContactAddress/leadContactAddresses" model="['leadContactInstance': leadContactInstance, 'prefix': prefix]"/>
<div class="control-group fieldcontain ${hasErrors(bean: leadContactInstance, field: 'primaryContact', 'error')} ">
    <label for="${prefix}primaryContact" class="control-label"><g:message code="leadContact.primaryContact.label"
                                                                 default="Primary Contact"/></label>

    <div class="controls">
        <bs:checkBox name="${prefix}primaryContact" value="${leadContactInstance?.primaryContact}"/>
        <span class="help-inline">${hasErrors(bean: leadContactInstance, field: 'primaryContact', 'error')}</span>
    </div>
</div>


</fieldset>
</div>
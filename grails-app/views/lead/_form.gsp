<%@ page import="com.cogda.multitenant.Lead" %>

<div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'clientId', 'error')} required">
    <label for="clientId" class="control-label"><g:message code="lead.clientId.label" default="Client Id"/><span
            class="required-indicator">*</span></label>

    <div class="controls">
        <g:textField name="clientId" required="" value="${leadInstance?.clientId}"/>
        <span class="help-inline">${hasErrors(bean: leadInstance, field: 'clientId', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'ownerName', 'error')} required">
    <label for="ownerName" class="control-label"><g:message code="lead.ownerName.label" default="Owner Name"/><span
            class="required-indicator">*</span></label>

    <div class="controls">
        <g:textField name="ownerName" required="" value="${leadInstance?.ownerName}"/>
        <span class="help-inline">${hasErrors(bean: leadInstance, field: 'ownerName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'businessType', 'error')} ">
    <label for="businessType" class="control-label"><g:message code="lead.businessType.label"
                                                               default="Business Type"/></label>

    <div class="controls">
        <g:select id="businessType" name="businessType.id"
                  from="${com.cogda.domain.admin.BusinessType.list().sort { it.description }}"
                  optionValue="${{ it.description }}" optionKey="id" value="${leadInstance?.businessType?.id}"
                  class="many-to-one" noSelection="['null': '']"/>
        <span class="help-inline">${hasErrors(bean: leadInstance, field: 'businessType', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'naicsCode', 'error')} ">
    <label for="naicsCode" class="control-label"><g:message code="lead.naicsCode.label" default="Naics Code"/></label>

    <div class="controls">
        <g:select id="naicsCode" name="naicsCode.id" from="${com.cogda.domain.admin.NaicsCode.list()}" optionKey="id"
                  value="${leadInstance?.naicsCode?.id}" class="many-to-one" noSelection="['null': '']"/>
        <span class="help-inline">${hasErrors(bean: leadInstance, field: 'naicsCode', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'sicCode', 'error')} ">
    <label for="sicCode" class="control-label"><g:message code="lead.sicCode.label" default="Sic Code"/></label>

    <div class="controls">
        <g:select id="sicCode" name="sicCode.id" from="${com.cogda.domain.admin.SicCode.list()}" optionKey="id"
                  value="${leadInstance?.sicCode?.id}" class="many-to-one" noSelection="['null': '']"/>
        <span class="help-inline">${hasErrors(bean: leadInstance, field: 'sicCode', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'clientName', 'error')} required">
    <label for="clientName" class="control-label"><g:message code="lead.clientName.label" default="Client Name"/><span
            class="required-indicator">*</span></label>

    <div class="controls">
        <g:textField name="clientName" required="" value="${leadInstance?.clientName}"/>
        <span class="help-inline">${hasErrors(bean: leadInstance, field: 'clientName', 'error')}</span>
    </div>
</div>

<g:render template="/lead/leadAddress/leadAddresses" model="['leadInstance': leadInstance]"/>

<g:render template="/lead/leadContact/leadContacts" model="['leadInstance': leadInstance]"/>
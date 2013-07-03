<%@ page import="com.cogda.common.LeadSubType; com.cogda.multitenant.Lead" %>
<fieldset class="embedded">
    <g:hiddenField name="id" value="${leadInstance?.id}"/>
    <g:hiddenField name="version" value="${leadInstance?.version}"/>
    <r:require module="sicCodeTree"/>
    <r:require module="naicsCodeTree"/>
    <div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'subType', 'error')} required">
        <label for="subType" class="control-label">
            <g:message code="lead.subType.label" default="Type"/>
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <g:radioGroup values="${LeadSubType.values()}" name="subType"
                          value="${leadInstance?.subType}"
                          labels="${LeadSubType.values().collect { it.getLabel() }}">
                <label class="radio inline"><span class="radioSpan">${it.label}</span>${it.radio}</label>
            </g:radioGroup>

        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'clientName', 'error')} required">
        <label for="clientName" class="control-label"><g:message code="lead.clientName.label" default="Client Name"/>
            <span class="required-indicator">*</span></label>

        <div class="controls">
            <g:textField name="clientName" required="" value="${leadInstance?.clientName}"/>
            <span class="help-inline">${hasErrors(bean: leadInstance, field: 'clientName', 'error')}</span>
        </div>
    </div>

    <g:render template="/lead/leadAddress/leadAddresses" model="[leadInstance: leadInstance]"/>

    <div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'businessType', 'error')} ">
        <label for="businessType" class="control-label">
            <g:message code="lead.businessType.label" default="Business Type"/>
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <g:select id="businessType" name="businessType.id"
                      from="${com.cogda.domain.admin.BusinessType.list().sort { it.description }}"
                      optionValue="${{ it.description }}" optionKey="id" value="${leadInstance?.businessType?.id}"
                      class="many-to-one"/>
            <span class="help-inline">${hasErrors(bean: leadInstance, field: 'businessType', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="lead.naicssiccode.label" default="NAICS/SIC Codes"/>
        </label>

        <div class="controls">
            <g:render template="/_common/modals/naicsCode/naicsCodes" model="[treeHandler: 'leadNaicsChecked();']"/>
            <g:render template="/_common/modals/sicCode/sicCodes" model="[treeHandler: 'leadSicChecked();']"/>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadInstance, field: 'clientId', 'error')} required">
        <label for="clientId" class="control-label"><g:message code="lead.clientId.label" default="Client Id"/>
            <span class="required-indicator">*</span></label>

        <div class="controls">
            <g:textField name="clientId" required="" value="${leadInstance?.clientId}"/>
            <span class="help-inline">${hasErrors(bean: leadInstance, field: 'clientId', 'error')}</span>
        </div>
    </div>
    <g:render template="/lead/leadContact/leadContacts" model="[leadInstance: leadInstance]"/>

    <g:render template="/lead/leadNote/leadNotes" model="[leadInstance: leadInstance]"/>
</fieldset>
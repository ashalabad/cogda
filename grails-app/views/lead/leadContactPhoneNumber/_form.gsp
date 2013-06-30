<%@ page import="com.cogda.multitenant.LeadContactPhoneNumber" %>

<div id="leadContactPhoneNumber${i}" class="phone-div" <g:if test="${hidden}">style="display:none"</g:if>>
    %{--<g:hiddenField name='leadContactPhoneNumbers[${i}].id' value='${leadContactPhoneNumberInstance?.id}'/>--}%
    %{--<g:hiddenField name='leadContactPhoneNumbers[${i}].deleted' value='false'/>--}%
    %{--<g:hiddenField name='leadContactPhoneNumbers[${i}].new' value='false'/>--}%


    <div class="control-group fieldcontain ${hasErrors(bean: leadContactPhoneNumberInstance, field: 'description', 'error')} ">
        <label for="${prefix}description" class="control-label"><g:message
                code="leadContactPhoneNumber.description.label"
                default="Phone Description"/></label>

        <div class="controls">
            <g:textField name="${prefix}description" value="${leadContactPhoneNumberInstance?.description}"/>
            <span class="help-inline">${hasErrors(bean: leadContactPhoneNumberInstance, field: 'description', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactPhoneNumberInstance, field: 'phoneNumber', 'error')} required">
        <label for="${prefix}phoneNumber" class="control-label"><g:message
                code="leadContactPhoneNumber.phoneNumber.label"
                default="Phone Number"/><span
                class="required-indicator">*</span></label>

        <div class="controls">
            <g:textField name="${prefix}phoneNumber" required=""
                         value="${leadContactPhoneNumberInstance?.phoneNumber}"/>
            <span class="help-inline">${hasErrors(bean: leadContactPhoneNumberInstance, field: 'phoneNumber', 'error')}</span>

            <a class="del-phone btn btn-danger add-field" href="#">
                <g:message code="leadContactPhoneNumber.phoneNumber.remove.label" default="Remove Phone"/></a>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactPhoneNumberInstance, field: 'primaryPhoneNumber', 'error')} ">
        <label for="${prefix}primaryPhoneNumber" class="control-label"><g:message
                code="leadContactPhoneNumber.primaryPhoneNumber.label" default="Primary Phone Number"/></label>

        <div class="controls">
            <bs:checkBox name="${prefix}primaryPhoneNumber"
                         value="${leadContactPhoneNumberInstance?.primaryPhoneNumber}"/>
            <span class="help-inline">${hasErrors(bean: leadContactPhoneNumberInstance, field: 'primaryPhoneNumber', 'error')}</span>
        </div>
    </div>
</div>
<%@ page import="com.cogda.domain.UserProfile" %>
<legend>
    <g:message code="userProfile.label"/>
    <span class="pull-right">
        <button class="btn btn-success" id="editUserProfileDetails">
            <i class="icon-edit"></i>
            <g:message code="default.button.edit.label"/>
        </button>
    </span>
</legend>
<g:hiddenField name="id" value="${userProfileInstance?.id}" />
<g:hiddenField name="version" value="${userProfileInstance?.version}" />
<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'published', 'error')} ">
    <label class="control-label"><g:message code="userProfile.published.label" default="Published" /></label>
    <div class="controls">
        <g:formatBoolean boolean="${userProfileInstance?.published}" />
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'published', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'firstName', 'error')} required">
    <label class="control-label"><g:message code="userProfile.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
    <div class="controls">
        ${fieldValue(bean: userProfileInstance, field: "firstName")}
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'firstName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'middleName', 'error')} ">
    <label class="control-label"><g:message code="userProfile.middleName.label" default="Middle Name" /></label>
    <div class="controls">
        ${fieldValue(bean: userProfileInstance, field: "middleName")}
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'middleName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'lastName', 'error')} required">
    <label class="control-label"><g:message code="userProfile.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
    <div class="controls">
        ${fieldValue(bean: userProfileInstance, field: "lastName")}
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'lastName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ">
    <label class="control-label"><g:message code="userProfile.company.label" default="Company" /></label>
    <div class="controls" >
        <strong style="margin-top:3px;">${userProfileInstance?.company?.companyName}</strong>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')} ">
    <label  class="control-label"><g:message code="userProfile.aboutDesc.label" default="About Desc" /></label>
    <div class="controls">
        ${fieldValue(bean: userProfileInstance, field: "aboutDesc")}
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'businessSpecialtiesDesc', 'error')} ">
    <label class="control-label"><g:message code="userProfile.businessSpecialtiesDesc.label" default="Business Specialties Desc" /></label>
    <div class="controls">
        ${fieldValue(bean: userProfileInstance, field: "businessSpecialtiesDesc")}
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'businessSpecialtiesDesc', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'associationsDesc', 'error')} ">
    <label class="control-label"><g:message code="userProfile.associationsDesc.label" default="Associations Desc" /></label>
    <div class="controls">
        ${fieldValue(bean: userProfileInstance, field: "associationsDesc")}
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'associationsDesc', 'error')}</span>
    </div>
</div>
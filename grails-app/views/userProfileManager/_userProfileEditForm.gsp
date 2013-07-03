<%@ page import="com.cogda.domain.UserProfile" %>
<legend>
    <g:message code="userProfile.label"/>
    <span class="pull-right">
        <button class="btn userProfileEditFormCancel" id="cancelEditUserProfileDetails">
            <i class="ui-icon-cancel"></i>
            <g:message code="default.button.cancel.label"/>
        </button>
    </span>
</legend>
<g:hiddenField name="id" value="${userProfileInstance?.id}" />
<g:hiddenField name="version" value="${userProfileInstance?.version}" />
<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'published', 'error')} ">
    <label for="published" class="control-label"><g:message code="userProfile.published.label" default="Published" /></label>
    <div class="controls">
        <g:checkBox name="published" value="${userProfileInstance?.published}"/>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'published', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'firstName', 'error')} required">
    <label for="firstName" class="control-label"><g:message code="userProfile.firstName.label" default="First Name" /><span class="required-indicator">*</span></label>
    <div class="controls">
        <g:textField name="firstName" required="" value="${userProfileInstance?.firstName}"/>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'firstName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'middleName', 'error')} ">
    <label for="middleName" class="control-label"><g:message code="userProfile.middleName.label" default="Middle Name" /></label>
    <div class="controls">
        <g:textField name="middleName" value="${userProfileInstance?.middleName}"/>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'middleName', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'lastName', 'error')} required">
    <label for="lastName" class="control-label"><g:message code="userProfile.lastName.label" default="Last Name" /><span class="required-indicator">*</span></label>
    <div class="controls">
        <g:textField name="lastName" required="" value="${userProfileInstance?.lastName}"/>
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
    <label for="aboutDesc" class="control-label"><g:message code="userProfile.aboutDesc.label" default="About Desc" /></label>
    <div class="controls">
        <g:textArea name="aboutDesc" value="${userProfileInstance?.aboutDesc}" class="bigtextarea"/>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'aboutDesc', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'businessSpecialtiesDesc', 'error')} ">
    <label for="businessSpecialtiesDesc" class="control-label"><g:message code="userProfile.businessSpecialtiesDesc.label" default="Business Specialties Desc" /></label>
    <div class="controls">
        <g:textArea name="businessSpecialtiesDesc" value="${userProfileInstance?.businessSpecialtiesDesc}" class="bigtextarea"/>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'businessSpecialtiesDesc', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: userProfileInstance, field: 'associationsDesc', 'error')} ">
    <label for="associationsDesc" class="control-label"><g:message code="userProfile.associationsDesc.label" default="Associations Desc" /></label>
    <div class="controls">
        <g:textArea name="associationsDesc" value="${userProfileInstance?.associationsDesc}" class="bigtextarea"/>
        <span class="help-inline">${hasErrors(bean: userProfileInstance, field: 'associationsDesc', 'error')}</span>
    </div>
</div>

<div class="form-actions">
    <button type="button" class="btn btn-primary" id="userProfileEditFormSave">
        <g:message code="default.button.update.label"/>
    </button>
    <button type="button" class="btn userProfileEditFormCancel" >
        <g:message code="default.button.cancel.label"/>
    </button>
</div>

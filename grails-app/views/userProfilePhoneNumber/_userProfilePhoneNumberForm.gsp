<%@ page import="com.cogda.domain.UserProfilePhoneNumber" %>

<g:form method="post" class="form-horizontal" name="userProfilePhoneNumberForm">
    <g:hiddenField name="userProfile.id" value="${userProfilePhoneNumberInstance.userProfile?.id}" />
    <g:hiddenField name="id" value="${userProfilePhoneNumberInstance?.id}" />
    <g:hiddenField name="version" value="${userProfilePhoneNumberInstance?.version}" />
    <fieldset class="form">

        <div class="control-group fieldcontain">
            <label for="phoneNumber" class="control-label">
                <g:message code="userProfilePhoneNumber.phoneNumber.label" />
                <span class="required-indicator">*</span>
            </label>
            <div class="controls">
                <g:textField name="phoneNumber" value="${userProfilePhoneNumberInstance?.phoneNumber}" required="required"/>
                <span class="help-inline"></span>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label for="primaryPhoneNumber" class="control-label">
                <g:message code="userProfilePhoneNumber.primaryPhoneNumber.label" />
            </label>
            <div class="controls">
                <g:checkBox name="primaryPhoneNumber" value="${userProfilePhoneNumberInstance.primaryPhoneNumber}"/>
                <span class="help-inline"></span>
            </div>
        </div>

    </fieldset>

    <div class="form-actions">
        <g:if test="${userProfilePhoneNumberInstance?.id}">
            <button type="button" id="userProfilePhoneNumberUpdateButton" class="btn btn-primary"  >
                <i class="icon-plus"></i>
                ${message(code: 'default.button.update.label')}
            </button>

            <button type="button"  id="userProfilePhoneNumberDeleteButton" class="btn btn-danger" >
                <i class="icon-remove"></i>
                ${message(code: 'default.button.delete.label')}
            </button>
        </g:if>
        <g:else>
            <button type="button" id="userProfilePhoneNumberAddButton" class="btn btn-primary"  >
                ${message(code: 'default.button.create.label')}
            </button>
        </g:else>
    </div>
</g:form>
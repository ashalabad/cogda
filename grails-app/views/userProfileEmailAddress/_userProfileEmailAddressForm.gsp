<%@ page import="com.cogda.common.UsState; com.cogda.domain.UserProfileAddress" %>

<g:form method="post" class="form-horizontal" name="userProfileEmailAddressForm">
    <g:hiddenField name="userProfile.id" value="${userProfileEmailAddressInstance.userProfile?.id}" />
    <g:hiddenField name="id" value="${userProfileEmailAddressInstance?.id}" />
    <g:hiddenField name="version" value="${userProfileEmailAddressInstance?.version}" />
    <fieldset class="form">

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileEmailAddressInstance, field: 'address.addressOne', 'error')} ">
            <label for="emailAddress" class="control-label">
                <g:message code="userProfileEmailAddress.emailAddress.label" />
                <span class="required-indicator">*</span>
            </label>
            <div class="controls">
                <g:textField name="emailAddress" value="${userProfileEmailAddressInstance?.emailAddress}" required="required"/>
                <span class="help-inline">${hasErrors(bean: userProfileEmailAddressInstance, field: 'addressOne', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileEmailAddressInstance, field: 'address.addressOne', 'error')} ">
            <label for="primaryEmailAddress" class="control-label">
                <g:message code="userProfileEmailAddress.primaryAddress.label" />

            </label>
            <div class="controls">
                <g:checkBox name="primaryEmailAddress" value="${userProfileEmailAddressInstance.primaryEmailAddress}"/>
                <span class="help-inline">${hasErrors(bean: userProfileEmailAddressInstance, field: 'addressOne', 'error')}</span>
            </div>
        </div>



    </fieldset>

    <div class="form-actions">
        <g:if test="${userProfileEmailAddressInstance?.id}">
            <button type="button" id="userProfileEmailAddressUpdateButton" class="btn btn-primary"  >
                <i class="icon-plus"></i>
                ${message(code: 'default.button.update.label')}
            </button>

            <button type="button"  id="userProfileEmailAddressDeleteButton" class="btn btn-danger" >
                <i class="icon-remove"></i>
                ${message(code: 'default.button.delete.label')}
            </button>
        </g:if>
        <g:else>
            <button type="button" id="userProfileEmailAddressAddButton" class="btn btn-primary"  >
                ${message(code: 'default.button.create.label')}
            </button>
        </g:else>
    </div>
</g:form>
<%@ page import="com.cogda.common.UsState; com.cogda.domain.UserProfileAddress" %>

<g:form method="post" class="form-horizontal" name="userProfileAddressForm">
    <g:hiddenField name="userProfile.id" value="${userProfileAddressInstance.userProfile?.id}" />
    <g:hiddenField name="id" value="${userProfileAddressInstance?.id}" />
    <g:hiddenField name="version" value="${userProfileAddressInstance?.version}" />
    <fieldset class="form">

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.addressOne', 'error')} ">
            <label for="address.addressOne" class="control-label">

                <g:message code="userProfileAddress.address.addressOne.label" default="Address One" />
                <span class="required-indicator">*</span>
            </label>
            <div class="controls">
                <g:textField name="address.addressOne" value="${userProfileAddressInstance?.address?.addressOne}" required="required"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'addressOne', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.addressTwo', 'error')} ">
            <label for="address.addressTwo" class="control-label"><g:message code="userProfileAddress.address.addressTwo.label" default="Address Two" /></label>
            <div class="controls">
                <g:textField name="address.addressTwo" value="${userProfileAddressInstance?.address?.addressTwo}"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'addressTwo', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.addressThree', 'error')} ">
            <label for="address.addressThree" class="control-label"><g:message code="userProfileAddress.address.addressThree.label" default="Address Three" /></label>
            <div class="controls">
                <g:textField name="address.addressThree" value="${userProfileAddressInstance?.address?.addressThree}"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'addressThree', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.zipcode', 'error')} ">
            <label for="address.zipcode" class="control-label"><g:message code="userProfileAddress.address.zipcode.label" default="Zipcode" /></label>
            <div class="controls">
                <g:textField name="address.zipcode" value="${userProfileAddressInstance?.address?.zipcode}"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'zipcode', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.city', 'error')} ">
            <label for="address.city" class="control-label"><g:message code="userProfileAddress.address.city.label" default="City" /></label>
            <div class="controls">
                <g:textField name="address.city" value="${userProfileAddressInstance?.address?.city}"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'city', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.state', 'error')} ">
            <label for="address.state" class="control-label"><g:message code="userProfileAddress.address.state.label" default="State" /></label>
            <div class="controls">
                <g:select name="address.state" from="${UsState.values()}" value="${userProfileAddressInstance?.address?.state}" optionValue="value" optionKey="key"></g:select>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'state', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.county', 'error')} ">
            <label for="address.county" class="control-label"><g:message code="userProfileAddress.address.county.label" default="County" /></label>
            <div class="controls">
                <g:textField name="address.county" value="${userProfileAddressInstance?.address?.county}"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'county', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: userProfileAddressInstance, field: 'address.country', 'error')} ">
            <label for="address.country" class="control-label"><g:message code="userProfileAddress.address.country.label" default="Country" /></label>
            <div class="controls">
                <g:countrySelect name="address.country"
                                 from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"
                                 value="${userProfileAddressInstance?.address?.country}"/>
                <span class="help-inline">${hasErrors(bean: userProfileAddressInstance, field: 'country', 'error')}</span>
            </div>
        </div>
    </fieldset>

    <div class="form-actions">
        <g:if test="${userProfileAddressInstance?.id}">
            <button type="button" id="userProfileAddressUpdateButton" class="btn btn-primary"  >
                <i class="icon-plus"></i>
                ${message(code: 'default.button.update.label')}
            </button>

            <button type="button"  id="userProfileAddressDeleteButton" class="btn btn-danger" >
                <i class="icon-remove"></i>
                ${message(code: 'default.button.delete.label')}
            </button>


        </g:if>
        <g:else>
            <button type="button" id="userProfileAddressAddButton" class="btn btn-primary"  >
                ${message(code: 'default.button.create.label')}
            </button>
        </g:else>
    </div>
</g:form>
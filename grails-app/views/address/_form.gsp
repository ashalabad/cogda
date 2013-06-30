<%@ page import="com.cogda.domain.Address" %>



<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'addressOne', 'error')} ">
    <label for="${prefix}addressOne" class="control-label"><g:message code="address.addressOne.label"
                                                             default="Address One"/></label>

    <div class="controls">
        <g:textField name="${prefix}addressOne" value="${addressInstance?.addressOne}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'addressOne', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'addressTwo', 'error')} ">
    <label for="${prefix}addressTwo" class="control-label"><g:message code="address.addressTwo.label"
                                                             default="Address Two"/></label>

    <div class="controls">
        <g:textField name="${prefix}addressTwo" value="${addressInstance?.addressTwo}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'addressTwo', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'addressThree', 'error')} ">
    <label for="${prefix}addressThree" class="control-label"><g:message code="address.addressThree.label"
                                                               default="Address Three"/></label>

    <div class="controls">
        <g:textField name="${prefix}addressThree" value="${addressInstance?.addressThree}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'addressThree', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'longitude', 'error')} ">
    <label for="${prefix}longitude" class="control-label"><g:message code="address.longitude.label" default="Longitude"/></label>

    <div class="controls">
        <g:field type="number" name="${prefix}longitude" step="any" value="${addressInstance?.longitude}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'longitude', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'latitude', 'error')} ">
    <label for="${prefix}latitude" class="control-label"><g:message code="address.latitude.label" default="Latitude"/></label>

    <div class="controls">
        <g:field type="number" name="${prefix}latitude" step="any" value="${addressInstance?.latitude}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'latitude', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'city', 'error')} ">
    <label for=${prefix}"city" class="control-label"><g:message code="address.city.label" default="City"/></label>

    <div class="controls">
        <g:textField name="${prefix}city" value="${addressInstance?.city}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'city', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'state', 'error')} ">
    <label for="${prefix}state" class="control-label"><g:message code="address.state.label" default="State"/></label>

    <div class="controls">
        <g:textField name="${prefix}state" value="${addressInstance?.state}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'state', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'country', 'error')} required">
    <label for="${prefix}country" class="control-label"><g:message code="address.country.label" default="Country"/>
        <span class="required-indicator">*</span>
    </label>

    <div class="controls">
        <g:countrySelect id="${prefix}country"
                         name="${prefix}address.country"
                         from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"
                         value="${addressInstance?.country}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'country', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'county', 'error')} ">
    <label for="${prefix}county" class="control-label"><g:message code="address.county.label" default="County"/></label>

    <div class="controls">
        <g:textField name="${prefix}county" value="${addressInstance?.county}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'county', 'error')}</span>
    </div>
</div>

<div class="control-group fieldcontain ${hasErrors(bean: addressInstance, field: 'zipcode', 'error')} ">
    <label for="${prefix}zipcode" class="control-label"><g:message code="address.zipcode.label" default="Zipcode"/></label>

    <div class="controls">
        <g:textField name="${prefix}zipcode" value="${addressInstance?.zipcode}"/>
        <span class="help-inline">${hasErrors(bean: addressInstance, field: 'zipcode', 'error')}</span>
    </div>
</div>


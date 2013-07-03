<%@ page import="com.cogda.multitenant.LeadContactAddress" %>


<div id="leadContactAddresses[${i}]"

<fieldset class="embedded"><legend><g:message code="leadContactAddress.address.label" default="Address"/></legend>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'primaryAddress', 'error')} ">
        <label for="primaryAddress" class="control-label"><g:message code="leadContactAddress.primaryAddress.label"
                                                                     default="Primary Address"/></label>

        <div class="controls">
            <bs:checkBox name="leadContactAddressInstance[${i}].primaryAddress"
                         value="${leadContactAddressInstance?.primaryAddress}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'primaryAddress', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.addressOne', 'error')} ">
        <label for="leadContactAddressInstance[${i}].address.addressOne" class="control-label"><g:message
                code="leadContactAddress.address.addressOne.label" default="Address One"/></label>

        <div class="controls">
            <g:textField name="leadContactAddressInstance[${i}].addressOne" value="${addressInstance?.addressOne}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'addressOne', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.addressTwo', 'error')} ">
        <label for="leadContactAddressInstance[${i}].address.addressTwo" class="control-label"><g:message
                code="leadContactAddress.address.addressTwo.label" default="Address Two"/></label>

        <div class="controls">
            <g:textField name="leadContactAddressInstance[${i}].address.addressTwo"
                         value="${addressInstance?.addressTwo}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'addressTwo', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.addressThree', 'error')} ">
        <label for="leadContactAddressInstance[${i}].address.addressThree" class="control-label"><g:message
                code="leadContactAddress.address.addressThree.label" default="Address Three"/></label>

        <div class="controls">
            <g:textField name="leadContactAddressInstance[${i}].address.addressThree"
                         value="${addressInstance?.addressThree}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'addressThree', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.city', 'error')} ">
        <label for="address.city" class="control-label"><g:message code="leadContactAddress.address.city.label"
                                                                   default="City"/></label>

        <div class="controls">
            <g:textField name="city" value="${addressInstance?.city}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'city', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.country', 'error')} ">
        <label for="leadContactAddressInstance[${i}].address.country" class="control-label"><g:message
                code="leadContactAddress.address.country.label"
                default="Country"/></label>

        <div class="controls">
            <g:textField name="leadContactAddressInstance[${i}].address.country" value="${addressInstance?.country}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'country', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.county', 'error')} ">
        <label for="leadContactAddressInstance[${i}].address.county" class="control-label"><g:message
                code="leadContactAddress.address.county.label"
                default="County"/></label>

        <div class="controls">
            <g:textField name="leadContactAddressInstance[${i}].address.county" value="${addressInstance?.county}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'county', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.latitude', 'error')} ">
        <label for="leadContactAddressInstance[${i}].address.latitude" class="control-label"><g:message
                code="leadContactAddress.address.latitude.label"
                default="Latitude"/></label>

        <div class="controls">
            <g:field type="number" name="leadContactAddressInstance[${i}].address.latitude" step="any"
                     value="${addressInstance.latitude}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'latitude', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.longitude', 'error')} ">
        <label for="address.longitude" class="control-label"><g:message
                code="leadContactAddress.address.longitude.label" default="Longitude"/></label>

        <div class="controls">
            <g:field type="number" name="longitude" step="any" value="${addressInstance.longitude}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'longitude', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.state', 'error')} ">
        <label for="address.state" class="control-label"><g:message code="leadContactAddress.address.state.label"
                                                                    default="State"/></label>

        <div class="controls">
            <g:textField name="state" value="${addressInstance?.state}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'state', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.version', 'error')} required">
        <label for="address.version" class="control-label"><g:message code="leadContactAddress.address.version.label"
                                                                      default="Version"/><span
                class="required-indicator">*</span></label>

        <div class="controls">
            <g:field type="number" name="version" required="" value="${addressInstance.version}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'version', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'address.zipcode', 'error')} ">
        <label for="address.zipcode" class="control-label"><g:message code="leadContactAddress.address.zipcode.label"
                                                                      default="Zip Code"/></label>

        <div class="controls">
            <g:textField name="zipcode" value="${addressInstance?.zipcode}"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'zipcode', 'error')}</span>
        </div>
    </div>

    <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'leadContact', 'error')} required">
        <label for="leadContact" class="control-label"><g:message code="leadContactAddress.leadContact.label"
                                                                  default="Lead Contact"/><span
                class="required-indicator">*</span></label>

        <div class="controls">
            <g:select id="leadContact" name="leadContact.id" from="${com.cogda.multitenant.LeadContact.list()}"
                      optionKey="id" required="" value="${leadContactAddressInstance?.leadContact?.id}"
                      class="many-to-one"/>
            <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'leadContact', 'error')}</span>
        </div>
    </div>

</fieldset>
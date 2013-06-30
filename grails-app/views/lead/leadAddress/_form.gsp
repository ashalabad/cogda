<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadAddress" %>

<div id="leadAddresses[${i}]">
    <fieldset class="embedded"><legend><g:message code="leadAddress.address.label" default="Address"/></legend>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.addressOne', 'error')} ">
            <label for="leadAddresses[${i}].address.addressOne" class="control-label"><g:message code="leadAddress.address.addressOne.label"
                                                                             default="Address One"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.addressOne" value="${leadAddressInstance?.address?.addressOne}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'addressOne', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.addressTwo', 'error')} ">
            <label for="leadAddresses[${i}].address.addressTwo" class="control-label"><g:message code="leadAddress.address.addressTwo.label"
                                                                             default="Address Two"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.addressTwo" value="${addressInstance?.addressTwo}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'addressTwo', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.addressThree', 'error')} ">
            <label for="leadAddresses[${i}].address.addressThree" class="control-label"><g:message
                    code="leadAddress.address.addressThree.label"
                    default="Address Three"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.addressThree" value="${addressInstance?.addressThree}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'addressThree', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.city', 'error')} ">
            <label for="leadAddresses[${i}].address.city" class="control-label"><g:message code="leadAddress.address.city.label"
                                                                       default="City"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.city" value="${addressInstance?.city}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'city', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: suspectInstance, field: 'country', 'error')} required">
            <label for="country" class="control-label"><g:message code="address.country.label" default="Country"/>
                <span class="required-indicator">*</span>
            </label>

            <div class="controls">
                <g:countrySelect id="country"
                                 name="leadAddresses[${i}].address.country"
                                 from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"
                                 value="${leadAddressInstance?.address?.country}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance.address, field: 'country', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.county', 'error')} ">
            <label for="leadAddresses[${i}].address.county" class="control-label"><g:message code="leadAddress.address.county.label"
                                                                         default="County"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.county" value="${addressInstance?.county}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'county', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.latitude', 'error')} ">
            <label for="leadAddresses[${i}].address.latitude" class="control-label"><g:message code="leadAddress.address.latitude.label"
                                                                           default="Latitude"/></label>

            <div class="controls">
                <g:field type="number" name="leadAddresses[${i}].address.latitude" step="any" value="${addressInstance?.latitude}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'latitude', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.longitude', 'error')} ">
            <label for="address.longitude" class="control-label"><g:message code="leadAddress.address.longitude.label"
                                                                            default="Longitude"/></label>

            <div class="controls">
                <g:field type="number" name="leadAddresses[${i}].address.longitude" step="any" value="${addressInstance?.longitude}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'longitude', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.state', 'error')} ">
            <label for="leadAddresses[${i}].address.state" class="control-label"><g:message code="leadAddress.address.state.label"
                                                                        default="State"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.state" value="${addressInstance?.state}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'state', 'error')}</span>
            </div>
        </div>

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'address.zipcode', 'error')} ">
            <label for="leadAddresses[${i}].address.zipcode" class="control-label"><g:message code="leadAddress.address.zipcode.label"
                                                                          default="Zip Code"/></label>

            <div class="controls">
                <g:textField name="leadAddresses[${i}].address.zipcode" value="${leadAddressInstance?.address?.zipcode}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance.address, field: 'zipcode', 'error')}</span>
            </div>
        </div>

        %{--<div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'leadAddressType', 'error')} required">--}%
        %{--<label for="leadAddressType" class="control-label"><g:message code="leadAddress.leadAddressType.label"--}%
        %{--default="Lead Address Type"/><span--}%
        %{--class="required-indicator">*</span></label>--}%

        %{--<div class="controls">--}%
        %{--<g:select id="leadAddressType" name="leadAddressType.id"--}%
        %{--from="${com.cogda.multitenant.LeadAddressType.list()}"--}%
        %{--optionKey="id" required="" value="${leadAddressInstance?.leadAddressType?.id}"--}%
        %{--class="many-to-one"/>--}%
        %{--<span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'leadAddressType', 'error')}</span>--}%
        %{--</div>--}%
        %{--</div>--}%

        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'primaryAddress', 'error')} ">
            <label for="primaryAddress" class="control-label"><g:message code="leadAddress.primaryAddress.label"
                                                                         default="Primary Address"/></label>

            <div class="controls">
                <bs:checkBox name="primaryAddress" value="${leadAddressInstance?.primaryAddress}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'primaryAddress', 'error')}</span>
            </div>
        </div>
    </fieldset>

</div>
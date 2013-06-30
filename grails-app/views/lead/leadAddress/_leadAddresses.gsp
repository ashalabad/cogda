<div id="childLeadAddresses" class="control-group fieldcontain">
    <g:each var="leadAddressInstance" in="${leadInstance?.leadAddresses}" status="i">
        <g:render template='/address/form'
                  model="[addressInstance: leadAddressInstance.address, i: i, hidden: false, prefix: 'leadAddresses[' + i + '].address.']"/>
        <div class="control-group fieldcontain ${hasErrors(bean: leadAddressInstance, field: 'primaryAddress', 'error')} ">
            <label for="primaryAddress" class="control-label"><g:message code="leadAddress.primaryAddress.label"
                                                                         default="Primary Address"/></label>
            <div class="controls">
                <bs:checkBox name="primaryAddress" value="${leadAddressInstance?.primaryAddress}"/>
                <span class="help-inline">${hasErrors(bean: leadAddressInstance, field: 'primaryAddress', 'error')}</span>
            </div>
        </div>
    </g:each>
</div>
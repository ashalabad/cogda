<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadContact" %>

<div id="childList">
    <g:each var="leadContactAddressInstance" in="${leadContactInstance.leadContactAddresses}" status="i">

        <g:render template='/address/form'
                  model="['addressInstance': leadContactAddressInstance.address, 'i': i, 'hidden': false, prefix: prefix + 'leadContactAddresses[' + i + '].address.']"/>
        <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'primaryAddress', 'error')} ">
            <label for="${prefix}leadContactAddressInstance.primaryAddress" class="control-label"><g:message code="leadContactAddress.primaryAddress.label"
                                                                         default="Primary Address"/></label>

            <div class="controls">
                <bs:checkBox name="${prefix}leadContactAddressInstance.primaryAddress" value="${leadContactAddressInstance?.primaryAddress}"/>
                <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'primaryAddress', 'error')}</span>
            </div>
        </div>
    </g:each>
</div>
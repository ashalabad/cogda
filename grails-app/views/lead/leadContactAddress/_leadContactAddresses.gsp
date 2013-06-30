<%@ page import="com.cogda.multitenant.Lead;com.cogda.multitenant.LeadContact" %>

<div id="childLeadContactAddresses" class="control-group fieldcontain">
    <g:each var="leadContactAddressInstance" in="${leadContactInstance.leadContactAddresses}" status="i">
        <g:set var="subPrefix" value="${prefix + 'leadContactAddresses[' + i + ']'}"/>
        <g:render template='/address/form'
                  model="[addressInstance: leadContactAddressInstance.address, i: i, hidden: false, prefix: subPrefix + '.address.']"/>
        <div class="control-group fieldcontain ${hasErrors(bean: leadContactAddressInstance, field: 'primaryAddress', 'error')} ">
            <label for="${subPrefix}primaryAddress" class="control-label"><g:message code="leadContactAddress.primaryAddress.label"
                                                                         default="Primary Address"/></label>

            <div class="controls">
                <bs:checkBox name="${subPrefix}primaryAddress" value="${leadContactAddressInstance?.primaryAddress}"/>
                <span class="help-inline">${hasErrors(bean: leadContactAddressInstance, field: 'primaryAddress', 'error')}</span>
            </div>
        </div>
    </g:each>
</div>
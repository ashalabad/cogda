<g:each var="leadAddressInstance" in="${leadInstance?.leadAddresses}" status="i">
    <div class="tab-pane" id="leadAddressInstance${i}">
        <table class="table">
            <tbody>
            <tr class="prop">
                <g:render template='/_common/modals/address/show'
                          model="[addressInstance: leadAddressInstance.address, i: i]"/>
                <td valign="top" class="name"><g:message code="leadAddress.primaryAddress.label"
                                                         default="Primary Address"/></td>
                <td valign="top" class="value"><g:formatBoolean
                        boolean="${leadAddressInstance?.primaryAddress}"/></td>
            </tbody>
        </table>
    </div>
</g:each>

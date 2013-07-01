<g:each var="leadContactAddressInstance" in="${leadContactInstance?.leadContactAddresses}" status="i">
    <div class="tab-pane" id="leadContactAddressInstance${i}">
        <table class="table">
            <tbody>
            <tr class="prop">
                <g:render template='/_common/modals/address/show'
                          model="[addressInstance: leadContactAddressInstance.address, i: i]"/>
                <td valign="top" class="name"><g:message code="leadAddress.primaryAddress.label"
                                                         default="Primary Address"/></td>
                <td valign="top" class="value"><g:formatBoolean
                        boolean="${leadContactAddressInstance?.primaryAddress}"/></td>
            </tbody>
        </table>
    </div>
</g:each>

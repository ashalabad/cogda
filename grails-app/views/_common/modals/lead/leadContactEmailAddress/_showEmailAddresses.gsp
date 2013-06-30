<g:each var="leadContactEmailAddressInstance" in="${leadContactInstance?.leadContactEmailAddresses}" status="i">
    <tr class="prop">
        <td valign="top" class="name"><g:message code="leadContactEmailAddress.primaryEmailAddress.label"
                                                 default="Primary Email Address"/></td>

        <td valign="top" class="value"><g:formatBoolean
                boolean="${leadContactEmailAddressInstance?.primaryEmailAddress}"/></td>

    </tr>

    <tr class="prop">
        <td valign="top" class="name"><g:message code="leadContactEmailAddress.emailAddress.label"
                                                 default="Email Address"/></td>

        <td valign="top" class="value"><a
                href="mailto:#">${fieldValue(bean: leadContactEmailAddressInstance, field: "emailAddress")}</a></td>

    </tr>
</g:each>

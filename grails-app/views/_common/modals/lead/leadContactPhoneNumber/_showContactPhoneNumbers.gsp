<g:each var="leadContactPhoneNumberInstance" in="${leadContactInstance.leadContactPhoneNumbers}" status="i">
    <tr class="prop">
        <td valign="top" class="name"><g:message code="leadContactEmailAddress.primaryEmailAddress.label"
                                                 default="Primary Phone Number"/></td>

        <td valign="top" class="value"><g:formatBoolean
                boolean="${leadContactPhoneNumberInstance?.primaryPhoneNumber}"/></td>
    </tr>
    <tr class="prop">
        <td valign="top" class="name"><g:message code="leadContactEmailAddress.emailAddress.label"
                                                 default="Phone Number"/></td>
        <td valign="top" class="value">${fieldValue(bean: leadContactPhoneNumberInstance, field: "phoneNumber")}</td>
    </tr>
</g:each>
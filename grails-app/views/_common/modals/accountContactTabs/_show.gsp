<div class="tab-pane" id="showContactsContent">
    <table class="table">
        <tbody>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.firstName.label" /></td>

            <td valign="top" class="value">${fieldValue(bean: accountContactInstance, field: "firstName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.middleName.label" /></td>

            <td valign="top" class="value">${fieldValue(bean: accountContactInstance, field: "middleName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.lastName.label" /></td>

            <td valign="top" class="value">${fieldValue(bean: accountContactInstance, field: "lastName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.accountContactAddresses.label" /></td>

            <td valign="top" style="text-align: left;" class="value">
                <ul class="unstyled">
                    <g:each in="${accountContactInstance.accountContactAddresses}" var="a">
                        <li>${a.address}</li>
                    </g:each>
                </ul>
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.accountContactEmailAddresses.label" /></td>

            <td valign="top" style="text-align: left;" class="value">
                <ul class="unstyled">
                    <g:each in="${accountContactInstance.accountContactEmailAddresses}" var="a">
                        <li>${a.emailAddress}</li>
                    </g:each>
                </ul>
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.accountContactPhoneNumbers.label" default="Account Contact Phone Numbers" /></td>

            <td valign="top" style="text-align: left;" class="value">
                <ul class="unstyled">
                    <g:each in="${accountContactInstance.accountContactPhoneNumbers}" var="a">
                        <li>${a.phoneNumber}</li>
                    </g:each>
                </ul>
            </td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.dateCreated.label" /></td>

            <td valign="top" class="value"><g:formatDate date="${accountContactInstance?.dateCreated}" /></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountContact.lastUpdated.label" /></td>

            <td valign="top" class="value"><g:formatDate date="${accountContactInstance?.lastUpdated}" /></td>

        </tr>

        </tbody>
    </table>

    <div class="edit">
        <div class="btn btn-danger show-field" id="closeShowContactTab"><i class="icon-remove-circle"></i> Close</div>
    </div>
</div>
<div>
    <ul id="leadContactDetails" class="nav nav-tabs">
        <li class="active"><a href="#leadDetailTab" data-toggle="tab">Overall</a></li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Addresses<b class="caret"></b></a>
            <ul class="dropdown-menu">
                <g:each var="leadContactAddressInstance" in="${leadContactInstance?.leadContactAddresses}" status="i">
                    <li><a href="#leadContactAddressInstance${i}" data-toggle="tab">Address #${i + 1}</a></li>
                </g:each>
            </ul>
        </li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="leadDetailTab">
            <table class="table">
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name"><g:message code="leadContact.firstName.label" default="First Name"/></td>

                    <td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "firstName")}</td>

                </tr>

                <tr class="prop">
                    <td valign="top" class="name"><g:message code="leadContact.middleName.label" default="Middle Name"/></td>

                    <td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "middleName")}</td>

                </tr>

                <tr class="prop">
                    <td valign="top" class="name"><g:message code="leadContact.lastName.label" default="Last Name"/></td>

                    <td valign="top" class="value">${fieldValue(bean: leadContactInstance, field: "lastName")}</td>

                </tr>

                <tr class="prop">
                    <td valign="top" class="name"><g:message code="leadContact.dateCreated.label" default="Date Created"/></td>

                    <td valign="top" class="value"><g:formatDate date="${leadContactInstance?.dateCreated}"/></td>

                </tr>

                <tr class="prop">
                    <td valign="top" class="name"><g:message code="leadContact.lastUpdated.label" default="Last Updated"/></td>

                    <td valign="top" class="value"><g:formatDate date="${leadContactInstance?.lastUpdated}"/></td>

                </tr>

                <tr class="prop">
                    <td valign="top" class="name"><g:message code="leadContact.primaryContact.label"
                                                             default="Primary Contact"/></td>

                    <td valign="top" class="value"><g:formatBoolean boolean="${leadContactInstance?.primaryContact}"/></td>

                </tr>
                <g:render template="/_common/modals/lead/leadContactEmailAddress/showEmailAddresses" model="[leadContactInstance: leadContactInstance]"/>
                <g:render template="/_common/modals/lead/leadContactPhoneNumber/showContactPhoneNumbers" model="[leadContactInstance: leadContactInstance]"/>
                </tbody>
            </table>
        </div>
        <g:render template="/_common/modals/lead/leadContactAddress/showAddresses"
                  model="[leadContactInstance: leadContactInstance]"/>
    </div>
</div>



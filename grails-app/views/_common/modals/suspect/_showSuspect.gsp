<section id="show-suspect" class="first">
    <g:set var="entityName" value="${message(code: 'suspect.label', default: 'Suspect')}"/>
    <h4><g:message code="default.show.label" args="[entityName]"/></h4>

    <div>
        <ul id="suspectDetails" class="nav nav-tabs">
            <li class="active"><a href="#suspectDetailTab" data-toggle="tab">Overall</a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Addresses<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <g:each var="leadAddressInstance" in="${suspectInstance?.leadAddresses}" status="i">
                        <li><a href="#leadAddressInstance${i}" data-toggle="tab">Address #${i + 1}</a></li>
                    </g:each>
                </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Contacts<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <g:each var="leadContactInstance" in="${suspectInstance?.leadContacts}" status="i">
                        <li><a href="#leadContactInstance${i}" data-toggle="tab">Contact #${i + 1}</a></li>
                    </g:each>
                </ul>
            </li>
        </ul>

        <div class="tab-content">
            <div class="tab-pane active" id="suspectDetailTab">
                <table class="table">
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.clientId.label" default="Client Id"/></td>

                        <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "clientId")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.businessType.label"
                                                                 default="Business Type"/></td>

                        <td valign="top" class="value"><g:link controller="businessType" action="show"
                                                               id="${suspectInstance?.businessType?.id}">${suspectInstance?.businessType?.description.encodeAsHTML()}</g:link></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.leadType.label" default="Lead Type"/></td>

                        <td valign="top" class="value">${suspectInstance?.leadType?.encodeAsHTML()}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.clientName.label"
                                                                 default="Client Name"/></td>

                        <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "clientName")}</td>

                    </tr>


                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactFirstName.label" default="Primary Contact First Name"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: suspectInstance?.primaryLeadContact, field: "firstName")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactLastName.label" default="Primary Contact Last Name"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: suspectInstance?.primaryLeadContact, field: "lastName")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactEmailAddress.label"
                                                                 default="Primary Contact Email Address"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: suspectInstance?.primaryLeadEmailAddress, field: "emailAddress")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactPhoneNumber.label"
                                                                 default="Primary Contact Phone Number"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: suspectInstance?.primaryLeadContactPhoneNumber, field: "phoneNumber")}</td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.dateCreated.label"
                                                                 default="Date Created"/></td>

                        <td valign="top" class="value"><g:formatDate date="${suspectInstance?.dateCreated}"/></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.lastUpdated.label"
                                                                 default="Last Updated"/></td>

                        <td valign="top" class="value"><g:formatDate date="${suspectInstance?.lastUpdated}"/></td>

                    </tr>

                    </tbody>
                </table>
            </div>
            <g:render template="/_common/modals/lead/leadAddress/showAddresses"
                          model="[leadInstance: suspectInstance]"/>
            <g:render template="/_common/modals/lead/leadContact/showContacts"
                      model="[leadInstance: suspectInstance]"/>
        </div>
    </div>
</section>
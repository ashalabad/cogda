<section id="show-prospect" class="first">
    <g:set var="entityName" value="${message(code: 'prospect.label', default: 'Prospect')}"/>
    <h4><g:message code="default.show.label" args="[entityName]"/></h4>

    <div>
        <ul id="prospectDetails" class="nav nav-tabs">
            <li class="active"><a href="#prospectDetailTab" data-toggle="tab">Overall</a></li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Addresses<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <g:each var="leadAddressInstance" in="${prospectInstance?.leadAddresses}" status="i">
                        <li><a href="#leadAddressInstance${i}" data-toggle="tab">Address #${i + 1}</a></li>
                    </g:each>
                </ul>
            </li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Contacts<b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <g:each var="leadContactInstance" in="${prospectInstance?.leadContacts}" status="i">
                        <li><a href="#leadContactInstance${i}" data-toggle="tab">Contact #${i + 1}</a></li>
                    </g:each>
                </ul>
            </li>
        </ul>

        <div class="tab-content">
            <div class="tab-pane active" id="prospectDetailTab">
                <table class="table">
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.clientId.label" default="Client Id"/></td>

                        <td valign="top" class="value">${fieldValue(bean: prospectInstance, field: "clientId")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.ownerName.label" default="Owner Name"/></td>

                        <td valign="top" class="value">${fieldValue(bean: prospectInstance, field: "ownerName")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.businessType.label"
                                                                 default="Business Type"/></td>

                        <td valign="top" class="value"><g:link controller="businessType" action="show"
                                                               id="${prospectInstance?.businessType?.id}">${prospectInstance?.businessType?.description.encodeAsHTML()}</g:link></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.naicsCode.label" default="Naics Code"/></td>

                        <td valign="top" class="value"><g:link controller="naicsCode" action="show"
                                                               id="${prospectInstance?.naicsCode?.id}">${prospectInstance?.naicsCode?.encodeAsHTML()}</g:link></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.sicCode.label" default="Sic Code"/></td>

                        <td valign="top" class="value"><g:link controller="sicCode" action="show"
                                                               id="${prospectInstance?.sicCode?.id}">${prospectInstance?.sicCode?.encodeAsHTML()}</g:link></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.leadType.label" default="Lead Type"/></td>

                        <td valign="top" class="value">${prospectInstance?.leadType?.encodeAsHTML()}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.clientName.label"
                                                                 default="Client Name"/></td>

                        <td valign="top" class="value">${fieldValue(bean: prospectInstance, field: "clientName")}</td>

                    </tr>


                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactFirstName.label" default="Primary Contact First Name"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: prospectInstance?.primaryLeadContact, field: "firstName")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactLastName.label" default="Primary Contact Last Name"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: prospectInstance?.primaryLeadContact, field: "lastName")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactEmailAddress.label"
                                                                 default="Primary Contact Email Address"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: prospectInstance?.primaryLeadEmailAddress, field: "emailAddress")}</td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.primaryContactPhoneNumber.label"
                                                                 default="Primary Contact Phone Number"/></td>

                        <td valign="top"
                            class="value">${fieldValue(bean: prospectInstance?.primaryLeadContactPhoneNumber, field: "phoneNumber")}</td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.dateCreated.label"
                                                                 default="Date Created"/></td>

                        <td valign="top" class="value"><g:formatDate date="${prospectInstance?.dateCreated}"/></td>

                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name"><g:message code="lead.lastUpdated.label"
                                                                 default="Last Updated"/></td>

                        <td valign="top" class="value"><g:formatDate date="${prospectInstance?.lastUpdated}"/></td>

                    </tr>

                    </tbody>
                </table>
            </div>
            <g:render template="/_common/modals/lead/leadAddress/showAddresses"
                          model="[leadInstance: prospectInstance]"/>
            <g:render template="/_common/modals/lead/leadContact/showContacts"
                      model="[leadInstance: prospectInstance]"/>
        </div>
    </div>
</section>
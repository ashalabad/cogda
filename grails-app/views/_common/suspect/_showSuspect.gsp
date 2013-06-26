<section id="show-suspect" class="first">
    <g:set var="entityName" value="${message(code: 'suspect.label', default: 'Suspect')}" />
    <h4><g:message code="default.show.label" args="[entityName]" /></h4>
    <table class="table">
        <tbody>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.clientId.label" default="Client Id" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "clientId")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.ownerName.label" default="Owner Name" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "ownerName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.account.label" default="Account" /></td>

            <td valign="top" class="value"><g:link controller="account" action="show" id="${suspectInstance?.account?.id}">${suspectInstance?.account?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.businessType.label" default="Business Type" /></td>

            <td valign="top" class="value"><g:link controller="businessType" action="show" id="${suspectInstance?.businessType?.id}">${suspectInstance?.businessType?.description.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.naicsCode.label" default="Naics Code" /></td>

            <td valign="top" class="value"><g:link controller="naicsCode" action="show" id="${suspectInstance?.naicsCode?.id}">${suspectInstance?.naicsCode?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.sicCode.label" default="Sic Code" /></td>

            <td valign="top" class="value"><g:link controller="sicCode" action="show" id="${suspectInstance?.sicCode?.id}">${suspectInstance?.sicCode?.encodeAsHTML()}</g:link></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.leadType.label" default="Lead Type" /></td>

            <td valign="top" class="value">${suspectInstance?.leadType?.encodeAsHTML()}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.clientName.label" default="Client Name" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "clientName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.address1.label" default="Address1" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "address1")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.address2.label" default="Address2" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "address2")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.city.label" default="City" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "city")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.state.label" default="State" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "state")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.zipCode.label" default="Zip Code" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "zipCode")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.county.label" default="County" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "county")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.country.label" default="Country" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "country")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.firstName.label" default="First Name" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "firstName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.lastName.label" default="Last Name" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "lastName")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.emailAddress.label" default="Email Address" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "emailAddress")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.phoneNumber.label" default="Phone Number" /></td>

            <td valign="top" class="value">${fieldValue(bean: suspectInstance, field: "phoneNumber")}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.dateCreated.label" default="Date Created" /></td>

            <td valign="top" class="value"><g:formatDate date="${suspectInstance?.dateCreated}" /></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="lead.lastUpdated.label" default="Last Updated" /></td>

            <td valign="top" class="value"><g:formatDate date="${suspectInstance?.lastUpdated}" /></td>

        </tr>

        </tbody>
    </table>
</section>
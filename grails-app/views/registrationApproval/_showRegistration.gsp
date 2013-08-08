<table class="table table-bordered">
<tbody>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.registrationStatus.label" default="Registration Status" /></td>

    <td valign="top" class="value">

        {{ registration.registrationStatus }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name">
        <g:message code="registration.firstName.label" default="First Name" />
    </td>

    <td valign="top" class="value">

        {{ registration.firstName }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.lastName.label" default="Last Name" /></td>

    <td valign="top" class="value">

        {{ registration.lastName }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.username.label" default="Username" /></td>

    <td valign="top" class="value">

        {{ registration.username }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.emailAddress.label" default="Email Address" /></td>

    <td valign="top" class="value">

        {{ registration.emailAddress }}


    </td>

</tr>



<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.companyName.label" default="Company Name" /></td>

    <td valign="top" class="value">

        {{ registration.companyName }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.newCompany.label" default="New Company" /></td>

    <td valign="top" class="value">
        {{ registration.newCompany ? 'Yes' : 'No' }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.companyType.label" default="Company Type" /></td>

    <td valign="top" class="value">
        {{ registration.companyType.code }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.existingCompany.label" default="Existing Company" /></td>

    <td valign="top" class="value">

        {{ registration.existingCompany.companyName }} -
        {{ registration.existingCompany.companyType.code }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.phoneNumber.label" default="Phone Number" /></td>

    <td valign="top" class="value">

        {{ registration.phoneNumber }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.streetAddressOne.label" default="Street Address One" /></td>

    <td valign="top" class="value">

        {{ registration.streetAddressOne }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.streetAddressTwo.label" default="Street Address Two" /></td>

    <td valign="top" class="value">
        {{ registration.streetAddressTwo }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.streetAddressThree.label" default="Street Address Three" /></td>

    <td valign="top" class="value">
        {{ registration.streetAddressThree }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.zipcode.label" default="Zipcode" /></td>

    <td valign="top" class="value">

        {{ registration.zipcode }}
    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.city.label" default="City" /></td>

    <td valign="top" class="value">

        {{ registration.city }}
    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.state.label" default="State" /></td>

    <td valign="top" class="value">
        {{ registration.state }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.county.label" default="County" /></td>

    <td valign="top" class="value">

        {{ registration.county }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="address.country.label" default="Country" /></td>

    <td valign="top" class="value">

        {{ registration.country }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.subDomain.label" default="Sub Domain" /></td>

    <td valign="top" class="value">

        {{ registration.subDomain }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.token.label" default="Token" /></td>

    <td valign="top" class="value">

        {{ registration.token }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.dateCreated.label" default="Date Created" /></td>

    <td valign="top" class="value">


        {{ registration.dateCreated | date }}

    </td>

</tr>

<tr class="prop">
    <td valign="top" class="name"><g:message code="registration.lastUpdated.label" default="Last Updated" /></td>

    <td valign="top" class="value">

        {{ registration.lastUpdated | date }}
    </td>

</tr>

</tbody>
</table>
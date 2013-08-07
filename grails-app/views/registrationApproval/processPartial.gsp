<ul class="breadcrumb">
    <li><a href="#/list">Registration Approval List</a></li>
</ul>

<div class="row">

    <div class="span4">

        <h3><g:message code="registrationApproval.label"/></h3>

            <form name="processRegistrationForm" method="post" class="form-horizontal" data-ng-show="!isProcessingApproval()">
                <fieldset class="form">
                    <div class="control-group fieldcontain">
                        <label for="subDomain" class="control-label">
                            <g:message code="registration.subDomain.label" default="Sub Domain" />
                        </label>
                        <div class="controls">
                            <input type="text" name="subDomain" name="subDomain"
                                   id="subDomain" data-ng-required="true" data-ng-model="subDomain"
                                   data-ng-pattern="/[A-Za-z]/" data-ng-class="{error:!isApproveButtonClickable()}"
                                   data-ng-Minlength="2">
                            <span class="help-inline">{{ subDomain }}.${grailsApplication.config.grails.domainURL}</span>
                        </div>
                    </div>
                </fieldset>
                <div class="form-actions">

                    <button type="button"  value="Approve" class="btn btn-primary"
                            data-ng-click="approveRegistration(processRegistrationForm)"
                            data-ng-disabled="!isApproveButtonClickable()">
                        <i class="icon-check"></i>&nbsp;
                        <g:message code="registrationApproval.approve.button"/>
                    </button>
                </div>
            </form>

            <div data-ng-show="isProcessingApproval()" class="center">
                <div class="alert alert-info">
                    <g:message code="registrationApproval.processingApproval.message"/>
                </div>
                <img src="${resource(dir:'images', file:'spinner.gif')}" title="Processing... Please wait."/>
            </div>
    </div>

    <div class="offset1 span7">

        <h3><g:message code="registrationApproval.currentRegistration.label"/>

        <span class="pull-right">
            <button class="btn btn-danger" data-ng-click="rejectRegistration(processRegistrationForm)"
                    data-ng-show="!isProcessingApproval()">
                <i class="icon-remove"></i>&nbsp;
                <g:message code="registrationApproval.reject.button"/>
            </button>
        </span>
        </h3>

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

    </div>
</div>
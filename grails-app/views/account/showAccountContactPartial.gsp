<div ng-cloak>

    <div class="row">
        <div class="span8">
            <button class="btn" data-ng-click="showAccount()"><i class="icon-arrow-left"></i> <g:message code="default.backTo.label" args="[message(code:'account.label')]"/></button><br>
            <br>
            <div data-ng-show="showAccountContact">
                <h1 class="center lead">
                    <span data-ng-show="account.accountContacts[contactIndex].primaryContact"> <span class="label label-success"><i class="icon-asterisk"></i> <g:message code="default.primary.label" /></span></span>
                    <span data-ng-bind='account.accountContacts[contactIndex].lastName'></span>,
                    <span data-ng-bind='account.accountContacts[contactIndex].firstName'></span>
                    <span data-ng-bind='account.accountContacts[contactIndex].middleName'></span>
                    <button type="button" class="btn btn-mini" data-ng-click="editAccountContact()"><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                </h1>
            </div>

            <div data-ng-hide="showAccountContact">
                <form name="accountContactEditForm" class="form-horizontal">
                    <fieldset>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.primaryContact.label')}
                            </label>
                            <div class="controls">
                                <div class="btn-group" data-toggle="buttons-radio">
                                    <button type="button" class="btn" data-ng-class="{'btn-primary':account.accountContacts[contactIndex].primaryContact==true}" data-ng-click="account.accountContacts[contactIndex].primaryContact=true"><g:message code="default.yes.label"/></button>
                                    <button type="button" class="btn" data-ng-class="{'btn-primary':account.accountContacts[contactIndex].primaryContact==false}" data-ng-click="account.accountContacts[contactIndex].primaryContact=false"><g:message code="default.no.label"/></button>
                                </div>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.firstName.label')}
                            </label>
                            <div class="controls">
                                <input type="text" name="accountContact.firstName" data-ng-model="account.accountContacts[contactIndex].firstName"/>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.middleName.label')}
                            </label>
                            <div class="controls">
                                <input type="text" name="accountContact.middleName" data-ng-model="account.accountContacts[contactIndex].middleName"/>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.lastName.label')}
                            </label>
                            <div class="controls">
                                <input type="text" name="accountContact.lastName" data-ng-model="account.accountContacts[contactIndex].lastName"/>
                            </div>
                        </div>
                    </fieldset>
                    <div class="span6">
                        <button type="button" class="btn btn-mini btn-primary pull-right" data-ng-click="updateAccount()"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
                        <button type="button" class="btn btn-mini btn-danger pull-right" data-ng-click="cancelAccountContactEdit()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                    </div>

                </form>
            </div>

        </div>
        <div class="span4"></div>
    </div>
    <br><br>
    <div class="row">
        <div class="span12">
            <tabset>
                <tab heading="Email Addresses ">
                    <button type="button" class="btn btn-mini pull-right" data-ng-click="addAccountContactEmailAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.accountContactEmailAddress.emailAddress.label')]" /></button>
                    <ul class="inline">
                        <li data-ng-repeat="emailAddress in account.accountContacts[contactIndex].accountContactEmailAddresses" class="span12 lead">
                            <div class="row">
                                <div class="span3">
                                    <button type="button" class="btn btn-mini " data-ng-click="editAccountContactEmailAddress(emailAddress)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                                    <button type="button" class="btn btn-mini btn-danger " data-ng-click="deleteAccountContactEmailAddress(emailAddress)" ><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                                    <span data-ng-show="emailAddress.primaryEmailAddress"> <span class="label label-success"><i class="icon-asterisk"></i> <g:message code="default.primary.label" /></span></span>
                                </div>
                                <div class="span9">
                                    {{emailAddress.emailAddress}}
                                </div>
                            </div>
                        </li>
                    </ul>
                </tab>
                <tab heading="Phone Numbers">
                    <button type="button" class="btn btn-mini pull-right" data-ng-click="addAccountContactPhoneNumber()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')]" /></button>
                    <ul class="inline">
                        <li data-ng-repeat="phoneNumber in account.accountContacts[contactIndex].accountContactPhoneNumbers" class="span12 lead">
                            <div class="row">
                                <div class="span3">
                                    <button type="button" class="btn btn-mini " data-ng-click="editAccountContactPhoneNumber(phoneNumber)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                                    <button type="button" class="btn btn-mini btn-danger " data-ng-click="deleteAccountContactPhoneNumber(phoneNumber)" ><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                                    <span data-ng-show="phoneNumber.primaryPhoneNumber"> <span class="label label-success"><i class="icon-asterisk"></i> <g:message code="default.primary.label" /></span></span>
                                </div>
                                <div class="span9">
                                    {{phoneNumber.phoneNumber}}
                                </div>
                            </div>
                        </li>
                    </ul>
                </tab>
                <tab heading="Addresses ">
                    <button type="button" class="btn btn-mini pull-right" data-ng-click="addAccountContactAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.accountContactAddress.address.label')]" /></button>
                    <ul class="inline">
                        <li data-ng-repeat="address in account.accountContacts[contactIndex].accountContactAddresses" class="span5 well fixedAddressWellHeight">
                            <button type="button" class="btn btn-mini pull-right btn-danger " data-ng-click="deleteAccountContactAddress(address)" ><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                            <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountContactAddress(address)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                            <div data-ng-bind-html-unsafe="formatAddress(address)"></div>
                            <span class="pull-right" data-ng-show="address.primaryAddress"> <span class="label label-success"><i class="icon-asterisk"></i> <g:message code="default.primary.label" /></span></span>
                        </li>
                    </ul>
                </tab>
            </tabset>
        </div>
    </div>
</div>
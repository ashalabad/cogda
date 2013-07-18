<div ng-cloak>

    <div class="row">

        <div class="span8">
            %{--<button class="btn" data-ng-click="showAccount()"><i class="icon-arrow-left"></i> <g:message code="default.backTo.label" args="[message(code:'account.label')]"/></button><br>--}%
            %{--<br>--}%
            <div class="well well-small" data-ng-show="showAccountContact">
                <h3>
                    <span data-ng-bind='accountContact.lastName'></span>,
                    <span data-ng-bind='accountContact.firstName'></span>
                    <span data-ng-bind='accountContact.middleName'></span>
                    <span class="gridIcon" data-ng-show="accountContact.favorite==true"><span class="label label-warning"><i class="icon-star"></i></span></span>
                    <span class="gridIcon" data-ng-show="accountContact.displayAsMarketOnBuilder==true"><span class="label label-info"><i class="icon-columns"></i></span></span>
                    <button type="button" class="btn btn-danger pull-right" data-ng-click="deleteAccountContact()"><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                    <button type="button" class="btn pull-right" data-ng-click="editAccountContact()"><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                </h3>
            </div>

            <div class="well well-small" data-ng-hide="showAccountContact" >
                <h3><g:message code="default.edit.label" args="[message(code:'accountContact.label')]"/></h3>
                <form name="accountContactEditForm" class="form-horizontal">
                    <fieldset>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.firstName.label')}
                            </label>
                            <div class="controls">
                                <input type="text" name="accountContact.firstName" data-ng-model="accountContact.firstName"/>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.middleName.label')}
                            </label>
                            <div class="controls">
                                <input type="text" name="accountContact.middleName" data-ng-model="accountContact.middleName"/>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                ${message(code:'accountContact.lastName.label')}
                            </label>
                            <div class="controls">
                                <input type="text" name="accountContact.lastName" data-ng-model="accountContact.lastName"/>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                <span class="gridIcon"><span class="label label-warning"><i class="icon-star"></i></span></span>
                                ${message(code:'accountContact.favorite.label')}
                            </label>
                            <div class="controls">
                                <div class="btn-group" data-toggle="buttons-radio">
                                    <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.favorite==true}" data-ng-click="accountContact.favorite=true"><g:message code="default.yes.label"/></button>
                                    <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.favorite==false}" data-ng-click="accountContact.favorite=false"><g:message code="default.no.label"/></button>
                                </div>
                            </div>
                        </div>
                        <div class="control-group fieldcontain">
                            <label class="control-label">
                                <span class="gridIcon"><span class="label label-info"><i class="icon-columns"></i></span></span>
                                ${message(code:'accountContact.displayAsMarketOnBuilder.label')}
                            </label>
                            <div class="controls">
                                <div class="btn-group" data-toggle="buttons-radio">
                                    <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.displayAsMarketOnBuilder==true}" data-ng-click="accountContact.displayAsMarketOnBuilder=true"><g:message code="default.yes.label"/></button>
                                    <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.displayAsMarketOnBuilder==false}" data-ng-click="accountContact.displayAsMarketOnBuilder=false"><g:message code="default.no.label"/></button>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <div class="span6">
                        <button type="button" class="btn btn-mini btn-primary pull-right" data-ng-click="updateAccountContact()"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
                        <button type="button" class="btn btn-mini btn-danger pull-right" data-ng-click="cancelAccountContactEdit()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                    </div>
                    <br>
                </form>
            </div>
        </div>
        <div class="span4">

        </div>
    </div>
    <br>
    <div class="row">
        <div class="span12">
            <tabset>
                <tab heading="Email Addresses ">
                    <div class="row">
                        <div class="span12">
                            <button type="button" class="btn pull-left" data-ng-click="addAccountContactEmailAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.accountContactEmailAddress.emailAddress.label')]" /></button>
                        </div>
                    </div>
                    <br>
                    <ul class="inline">
                        <li data-ng-repeat="emailAddress in accountContact.accountContactEmailAddresses" class="span5 well well-small">
                            {{emailAddress.emailAddress}}
                            <span data-ng-show="emailAddress.primaryEmailAddress"> <span class="label label-success"><i class="icon-asterisk"></i></span></span>
                            <button type="button" class="btn btn-mini btn-danger pull-right" data-ng-click="deleteAccountContactEmailAddress(emailAddress)" ><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                            <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountContactEmailAddress(emailAddress)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                        </li>
                    </ul>
                </tab>
                <tab heading="Phone Numbers">
                    <div class="row">
                        <div class="span12">
                            <button type="button" class="btn pull-left" data-ng-click="addAccountContactPhoneNumber()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')]" /></button>
                        </div>
                    </div>
                    <br>
                    <ul class="inline">
                        <li data-ng-repeat="phoneNumber in accountContact.accountContactPhoneNumbers" class="span5 well well-small">
                            {{phoneNumber.phoneNumber}}
                            <span data-ng-show="phoneNumber.primaryPhoneNumber"> <span class="label label-success"><i class="icon-asterisk"></i></span></span>
                            <button type="button" class="btn btn-mini btn-danger pull-right" data-ng-click="deleteAccountContactPhoneNumber(phoneNumber)" ><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                            <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountContactPhoneNumber(phoneNumber)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                        </li>
                    </ul>
                </tab>
                <tab heading="Addresses ">
                    <div class="row">
                        <div class="span12">
                            <button type="button" class="btn pull-left" data-ng-click="addAccountContactAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.accountContactAddress.address.label')]" /></button>
                        </div>
                    </div>
                    <br>
                    <ul class="inline">
                        <li data-ng-repeat="address in accountContact.accountContactAddresses" class="span5 well fixedAddressWellHeight">
                            <button type="button" class="btn btn-mini pull-right btn-danger " data-ng-click="deleteAccountContactAddress(address)" ><i class="icon-remove"></i> <g:message code="default.button.delete.label" /></button>
                            <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountContactAddress(address)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                            <div data-ng-bind-html-unsafe="formatAddress(address)"></div>
                            <span class="pull-right" data-ng-show="address.primaryAddress"> <span class="label label-success"><i class="icon-asterisk"></i></span></span>
                        </li>
                    </ul>
                </tab>
            </tabset>
        </div>
    </div>
</div>
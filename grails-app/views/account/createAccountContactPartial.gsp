<div class="modal">
<div class="modal-header">
    <h3>{{title}}</h3>
</div>
<div class="modal-body" >
<form name="accountContactAddForm" class="form-horizontal">
<fieldset>
<div class="control-group fieldcontain">
    <label class="control-label">
        ${message(code:'accountContact.primaryContact.label')}
    </label>
    <div class="controls">
        <div class="btn-group" data-toggle="buttons-radio">
            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContactLink.primaryContact==true}" data-ng-click="accountContactLink.primaryContact=true"><g:message code="default.yes.label"/></button>
            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContactLink.primaryContact==false}" data-ng-click="accountContactLink.primaryContact=false"><g:message code="default.no.label"/></button>
        </div>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContactLink.accountContact.firstName'].$invalid, success: accountAddForm['accountContactLink.accountContact.firstName'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.firstName.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContactLink.accountContact.firstName"
               data-ng-model="accountContactLink.accountContact.firstName"
               placeholder="${message(code:'accountContact.firstName.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.firstName ">{{ errors.accountContactLink.accountContact.firstName }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.firstName'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.firstName'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContactLink.accountContact.middleName'].$invalid, success: accountAddForm['accountContactLink.accountContact.middleName'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.middleName.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContactLink.accountContact.middleName"
               data-ng-model="accountContactLink.accountContact.middleName"
               placeholder="${message(code:'accountContact.middleName.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.middleName ">{{ errors.accountContactLink.accountContact.middleName }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.middleName'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.middleName'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContactLink.accountContact.lastName'].$invalid, success: accountAddForm['accountContactLink.accountContact.lastName'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.lastName.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContactLink.accountContact.lastName"
               data-ng-model="accountContactLink.accountContact.lastName"
               placeholder="${message(code:'accountContact.lastName.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.lastName ">{{ errors.accountContactLink.accountContact.lastName }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.lastName'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.lastName'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContactEmailAddress.emailAddress'].$invalid, success: accountAddForm['accountContactEmailAddress.emailAddress'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContactEmailAddress.emailAddress"
               data-ng-model="accountContactEmailAddress.emailAddress"
               placeholder="${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContactEmailAddress.emailAddress ">{{ errors.accountContactEmailAddress.emailAddress }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContactEmailAddress.emailAddress'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContactEmailAddress.emailAddress'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContactPhoneNumber.phoneNumber'].$invalid, success: accountAddForm['accountContactPhoneNumber.phoneNumber'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContactPhoneNumber.phoneNumber"
               data-ng-model="accountContactPhoneNumber.phoneNumber"
               placeholder="${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContactPhoneNumber.phoneNumber ">{{ errors.accountContactLink.accountContact.accountContactPhoneNumber.phoneNumber }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContactPhoneNumber.phoneNumber'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContactPhoneNumber.phoneNumber'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressOne'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressOne'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.addressOne.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.addressOne"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.addressOne"--}%
               %{--placeholder="${message(code:'accountAddress.address.addressOne.label')}" />--}%
        %{--<span class="help-inline error" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.addressOne "> {{ errors.accountContact.accountContactAddress.address.addressOne }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressOne'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressOne'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressTwo'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressTwo'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.addressTwo.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.addressTwo"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.addressTwo"--}%
               %{--placeholder="${message(code:'accountAddress.address.addressTwo.label')}"/>--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.addressTwo ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.addressTwo }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressTwo'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressTwo'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressThree'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressThree'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.addressThree.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.addressThree"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.addressThree"--}%
               %{--placeholder="${message(code:'accountAddress.address.addressThree.label')}"/>--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.addressThree ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.addressThree }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressThree'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.addressThree'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.city'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.city'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.city.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.city"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.city"--}%
               %{--placeholder="${message(code:'accountAddress.address.city.label')}"/>--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.city ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.city }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.city'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.city'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.state'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.state'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.state.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<g:select class="input-medium" name="accountContactLink.accountContact.accountContactAddress.address.state" data-ng-model="accountContactAddress.address.state" from="${com.cogda.common.UsState.values()}" optionKey="key"  noSelection="['': 'State']" />--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.state ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.state }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.state'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.state'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.zipcode'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.zipcode'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.zipcode.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.zipcode"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.zipcode"--}%
               %{--placeholder="${message(code:'accountAddress.address.zipcode.label')}"/>--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.zipcode ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.zipcode }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.zipcode'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.zipcode'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.county'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.county'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.county.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.county"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.county"--}%
               %{--placeholder="${message(code:'accountAddress.address.county.label')}"/>--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.county ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.county }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.county'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.county'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%
%{--<div class="control-group fieldcontain"--}%
     %{--data-ng-class="{error: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.country'].$invalid, success: accountAddForm['accountContactLink.accountContact.accountContactAddress.address.country'].$valid}">--}%
    %{--<label class="control-label">--}%
        %{--${message(code:'accountAddress.address.country.label')}--}%
    %{--</label>--}%
    %{--<div class="controls">--}%
        %{--<input type="text"--}%
               %{--name="accountContactLink.accountContact.accountContactAddress.address.country"--}%
               %{--data-ng-model="accountContactLink.accountContact.accountContactAddress.address.country"--}%
               %{--placeholder="${message(code:'accountAddress.address.country.label')}"/>--}%
        %{--<span class="help-inline" data-ng-show="errors.accountContactLink.accountContact.accountContactAddress.address.country ">{{ errors.accountContactLink.accountContact.accountContactAddress.address.country }}</span>--}%
        %{--<span class="label label-important" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.country'].$invalid"> ${message(code:'default.invalid.message')} </span>--}%
        %{--<span class="label label-success" ng-show="accountAddForm['accountContactLink.accountContact.accountContactAddress.address.country'].$valid"> ${message(code:'default.valid.message')} </span>--}%
    %{--</div>--}%
%{--</div>--}%


            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="saveAccountContact()"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
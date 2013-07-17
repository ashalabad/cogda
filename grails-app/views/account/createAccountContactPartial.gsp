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
            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.primaryContact==true}" data-ng-click="accountContact.primaryContact=true"><g:message code="default.yes.label"/></button>
            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.primaryContact==false}" data-ng-click="accountContact.primaryContact=false"><g:message code="default.no.label"/></button>
        </div>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.firstName'].$invalid, success: accountAddForm['accountContact.firstName'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.firstName.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.firstName"
               data-ng-model="accountContact.firstName"
               placeholder="${message(code:'accountContact.firstName.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.firstName ">{{ errors.accountContact.firstName }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.firstName'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.firstName'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.middleName'].$invalid, success: accountAddForm['accountContact.middleName'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.middleName.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.middleName"
               data-ng-model="accountContact.middleName"
               placeholder="${message(code:'accountContact.middleName.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.middleName ">{{ errors.accountContact.middleName }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.middleName'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.middleName'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.lastName'].$invalid, success: accountAddForm['accountContact.lastName'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.lastName.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.lastName"
               data-ng-model="accountContact.lastName"
               placeholder="${message(code:'accountContact.lastName.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.lastName ">{{ errors.accountContact.lastName }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.lastName'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.lastName'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactEmailAddress.emailAddress'].$invalid, success: accountAddForm['accountContact.accountContactEmailAddress.emailAddress'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactEmailAddress.emailAddress"
               data-ng-model="accountContactEmailAddress.emailAddress"
               placeholder="${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactEmailAddress.emailAddress ">{{ errors.accountContact.accountContactEmailAddress.emailAddress }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactEmailAddress.emailAddress'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactEmailAddress.emailAddress'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactPhoneNumber.phoneNumber'].$invalid, success: accountAddForm['accountContact.accountContactPhoneNumber.phoneNumber'].$valid}">
    <label class="control-label">
        ${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactPhoneNumber.phoneNumber"
               data-ng-model="accountContactPhoneNumber.phoneNumber"
               placeholder="${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactPhoneNumber.phoneNumber ">{{ errors.accountContact.accountContactPhoneNumber.phoneNumber }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactPhoneNumber.phoneNumber'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactPhoneNumber.phoneNumber'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.addressOne'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.addressOne'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.addressOne.label')}
    </label>
    <div class="controls">
        <input type="text"
               data-ng-model="accountContactAddress.address.addressOne"
               name="accountContact.accountContactAddress.address.addressOne"
               placeholder="${message(code:'accountAddress.address.addressOne.label')}" />
        <span class="help-inline error" data-ng-show="errors.accountContact.accountContactAddress.address.addressOne "> {{ errors.accountContact.accountContactAddress.address.addressOne }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressOne'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressOne'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.addressTwo'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.addressTwo'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.addressTwo.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactAddress.address.addressTwo"
               data-ng-model="accountContactAddress.address.addressTwo"
               placeholder="${message(code:'accountAddress.address.addressTwo.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.addressTwo ">{{ errors.accountContact.accountContactAddress.address.addressTwo }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressTwo'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressTwo'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.addressThree'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.addressThree'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.addressThree.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactAddress.address.addressThree"
               data-ng-model="accountContactAddress.address.addressThree"
               placeholder="${message(code:'accountAddress.address.addressThree.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.addressThree ">{{ errors.accountContact.accountContactAddress.address.addressThree }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressThree'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.addressThree'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.city'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.city'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.city.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactAddress.address.city"
               data-ng-model="accountContactAddress.address.city"
               placeholder="${message(code:'accountAddress.address.city.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.city ">{{ errors.accountContact.accountContactAddress.address.city }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.city'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.city'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.state'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.state'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.state.label')}
    </label>
    <div class="controls">
        <g:select class="input-small" name="accountContact.accountContactAddress.address.state" data-ng-model="accountContactAddress.address.state" from="${com.cogda.common.UsState.values()}" optionKey="key"  noSelection="['': 'State']" />
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.state ">{{ errors.accountContact.accountContactAddress.address.state }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.state'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.state'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.zipcode'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.zipcode'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.zipcode.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactAddress.address.zipcode"
               data-ng-model="accountContactAddress.address.zipcode"
               placeholder="${message(code:'accountAddress.address.zipcode.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.zipcode ">{{ errors.accountContact.accountContactAddress.address.zipcode }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.zipcode'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.zipcode'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.county'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.county'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.county.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactAddress.address.county"
               data-ng-model="accountContactAddress.address.county"
               placeholder="${message(code:'accountAddress.address.county.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.county ">{{ errors.accountContact.accountContactAddress.address.county }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.county'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.county'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
<div class="control-group fieldcontain"
     data-ng-class="{error: accountAddForm['accountContact.accountContactAddress.address.country'].$invalid, success: accountAddForm['accountContact.accountContactAddress.address.country'].$valid}">
    <label class="control-label">
        ${message(code:'accountAddress.address.country.label')}
    </label>
    <div class="controls">
        <input type="text"
               name="accountContact.accountContactAddress.address.country"
               data-ng-model="accountContactAddress.address.country"
               placeholder="${message(code:'accountAddress.address.country.label')}"/>
        <span class="help-inline" data-ng-show="errors.accountContact.accountContactAddress.address.country ">{{ errors.accountContact.accountContactAddress.address.country }}</span>
        <span class="label label-important" ng-show="accountAddForm['accountContact.accountContactAddress.address.country'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="accountAddForm['accountContact.accountContactAddress.address.country'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>
</fieldset>

<div class="form-actions">
    <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    <button type="button" class="btn btn-primary" data-ng-click="saveAccountContact(accountContact,accountContactAddress,accountContactEmailAddress,accountContactPhoneNumber)"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
</div>
</form>
</div>
</div>

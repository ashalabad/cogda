<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactEmailAddressAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactEmailAddressAddForm['accountContactEmailAddress.emailAddress'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactEmailAddress.emailAddress"
                               data-ng-model="accountContactEmailAddress.emailAddress"
                               placeholder="${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountContactEmailAddressAddForm['accountContactEmailAddress.primaryEmailAddress'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddresses.primary.label')}
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContactEmailAddress.primaryEmailAddress==true}" data-ng-click="accountContactEmailAddress.primaryEmailAddress=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContactEmailAddress.primaryEmailAddress==false}" data-ng-click="accountContactEmailAddress.primaryEmailAddress=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="saveAccountContactEmailAddress(accountContactEmailAddress)"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
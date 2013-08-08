<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactPhoneNumberEditForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContact.accountContactPhoneNumber.phoneNumber"
                               data-ng-model="accountContact.accountContactPhoneNumbers[accountContactPhoneNumberIndex].phoneNumber"
                               placeholder="${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactPhoneNumbers.primary.label')}
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.accountContactPhoneNumbers[accountContactPhoneNumberIndex].primaryPhoneNumber==true}" data-ng-click="accountContact.accountContactPhoneNumbers[accountContactPhoneNumberIndex].primaryPhoneNumber=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.accountContactPhoneNumbers[accountContactPhoneNumberIndex].primaryPhoneNumber==false}" data-ng-click="accountContact.accountContactPhoneNumbers[accountContactPhoneNumberIndex].primaryPhoneNumber=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="updateAccount()"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
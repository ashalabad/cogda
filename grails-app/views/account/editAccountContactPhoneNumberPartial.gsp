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
                               name="account.accountContacts[contactIndex].accountContactPhoneNumber.phoneNumber"
                               data-ng-model="account.accountContacts[contactIndex].accountContactPhoneNumbers[accountContactPhoneNumberIndex].phoneNumber"
                               placeholder="${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactPhoneNumbers.primary.label')}
                    </label>
                    <div class="controls">
                        <input type="checkbox"
                               name="account.accountContacts[contactIndex].accountContactPhoneNumber.primaryPhoneNumber"
                               data-ng-model="account.accountContacts[contactIndex].accountContactPhoneNumbers[accountContactPhoneNumberIndex].primaryPhoneNumber"
                        />
                    </div>
                </div>


                <div class="form-actions">
                    <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                    <button type="button" class="btn btn-primary" data-ng-click="updateAccount()"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
                </div>
            </fieldset>
        </form>
    </div>
</div>

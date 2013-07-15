<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactPhoneNumberAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactPhoneNumberAddForm['accountContactPhoneNumber.phoneNumber'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactPhoneNumber.phoneNumber"
                               data-ng-model="accountContactPhoneNumber.phoneNumber"
                               placeholder="${message(code:'accountContact.accountContactPhoneNumber.phoneNumber.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountContactPhoneNumberAddForm['accountContactPhoneNumber.primaryPhoneNumber'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactPhoneNumbers.primary.label')}
                    </label>
                    <div class="controls">
                        <input type="checkbox"
                               name="accountContactPhoneNumber.primaryPhoneNumber"
                               data-ng-model="accountContactPhoneNumber.primaryPhoneNumber"
                        />
                    </div>
                </div>


                <div class="form-actions">
                    <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                    <button type="button" class="btn btn-primary" data-ng-click="saveAccountContactPhoneNumber(accountContactPhoneNumber)"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
                </div>
            </fieldset>
        </form>
    </div>
</div>

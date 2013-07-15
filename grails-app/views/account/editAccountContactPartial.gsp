<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactEditForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain"
                     data-ng-class="{error: accountContactEditForm['accountContacts[contactIndex].firstName'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.firstName.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContacts[contactIndex].firstName"
                               data-ng-model="account.accountContacts[contactIndex].firstName"
                               placeholder="${message(code:'accountContact.firstName.label')}"/>
                        <span class="help-inline" data-ng-show="errors.accountContacts[contactIndex].firstName">{{ errors.accountContacts[contactIndex].firstName }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain"
                     data-ng-class="{error: accountContactEditForm['accountContact.middleName'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.middleName.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="middleName"
                               data-ng-model="account.accountContacts[contactIndex].middleName"
                               placeholder="${message(code:'accountContact.middleName.label')}"/>
                        <span class="help-inline" data-ng-show="errors.middleName ">{{ errors.middleName }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain"
                     data-ng-class="{error: accountContactEditForm['lastName'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.lastName.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="lastName"
                               data-ng-model="account.accountContacts[contactIndex].lastName"
                               placeholder="${message(code:'accountContact.lastName.label')}"/>
                        <span class="help-inline" data-ng-show="errors.lastName ">{{ errors.lastName }}</span>
                    </div>
                </div>
            </fieldset>

            <div class="form-actions">
                <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                <button type="button" class="btn btn-primary" data-ng-click="updateAccount()"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
            </div>
        </form>
    </div>
</div>

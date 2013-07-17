<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactEmailAddressEditForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="account.accountContacts[contactIndex].accountContactEmailAddress.emailAddress"
                               data-ng-model="account.accountContacts[contactIndex].accountContactEmailAddresses[accountContactEmailAddressIndex].emailAddress"
                               placeholder="${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddresses.primary.label')}
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.accountContacts[contactIndex].accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress==true}" data-ng-click="account.accountContacts[contactIndex].accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.accountContacts[contactIndex].accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress==false}" data-ng-click="account.accountContacts[contactIndex].accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress=false"><g:message code="default.no.label"/></button>
                        </div>
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

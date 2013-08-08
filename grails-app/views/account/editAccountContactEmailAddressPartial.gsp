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
                               name="accountContact.accountContactEmailAddress.emailAddress"
                               data-ng-model="accountContact.accountContactEmailAddresses[accountContactEmailAddressIndex].emailAddress"
                               placeholder="${message(code:'accountContact.accountContactEmailAddress.emailAddress.label')}"/>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountContact.accountContactEmailAddresses.primary.label')}
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress==true}" data-ng-click="accountContact.accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress==false}" data-ng-click="accountContact.accountContactEmailAddresses[accountContactEmailAddressIndex].primaryEmailAddress=false"><g:message code="default.no.label"/></button>
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

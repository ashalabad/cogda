<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactAddressEditForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.primaryAddress'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.primaryAddress.label')}
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.accountContactAddresses[accountContactAddressIndex].primaryAddress==true}" data-ng-click="accountContact.accountContactAddresses[accountContactAddressIndex].primaryAddress=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContact.accountContactAddresses[accountContactAddressIndex].primaryAddress==false}" data-ng-click="accountContact.accountContactAddresses[accountContactAddressIndex].primaryAddress=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.addressOne'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.addressOne.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.addressOne"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.addressOne"
                               placeholder="${message(code:'accountContact.address.addressOne.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.addressTwo'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.addressTwo.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.addressTwo"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.addressTwo"
                               placeholder="${message(code:'accountContact.address.addressTwo.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.addressThree'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.addressThree.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.addressThree"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.addressThree"
                               placeholder="${message(code:'accountContact.address.addressThree.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.city'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.city.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.city"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.city"
                               placeholder="${message(code:'accountContact.address.city.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.state'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.state.label')}
                    </label>
                    <div class="controls">
                        <g:select class="input-medium" name="accountContactAddress.address.state" data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.state" from="${com.cogda.common.UsState.values()}" optionKey="key"  noSelection="['': 'State']" />
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.zipcode'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.zipcode.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.zipcode"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.zipcode"
                               placeholder="${message(code:'accountContact.address.zipcode.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.county'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.county.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.county"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.county"
                               placeholder="${message(code:'accountContact.address.county.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressEditForm['accountContactAddress.address.country'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.country.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.country"
                               data-ng-model="accountContact.accountContactAddresses[accountContactAddressIndex].address.country"
                               placeholder="${message(code:'accountContact.address.country.label')}"/>
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

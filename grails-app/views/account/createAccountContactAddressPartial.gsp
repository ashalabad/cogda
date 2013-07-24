<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactAddressAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.primaryAddress'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.primaryAddress.label')}
                    </label>
                        <div class="controls">
                            <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContactAddress.primaryAddress==true}" data-ng-click="accountContactAddress.primaryAddress=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':accountContactAddress.primaryAddress==false}" data-ng-click="accountContactAddress.primaryAddress=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.addressOne'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.addressOne.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.addressOne"
                               data-ng-model="accountContactAddress.address.addressOne"
                               placeholder="${message(code:'accountContact.address.addressOne.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.addressTwo'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.addressTwo.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.addressTwo"
                               data-ng-model="accountContactAddress.address.addressTwo"
                               placeholder="${message(code:'accountContact.address.addressTwo.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.addressThree'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.addressThree.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.addressThree"
                               data-ng-model="accountContactAddress.address.addressThree"
                               placeholder="${message(code:'accountContact.address.addressThree.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.city'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.city.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.city"
                               data-ng-model="accountContactAddress.address.city"
                               placeholder="${message(code:'accountContact.address.city.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.state'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.state.label')}
                    </label>
                    <div class="controls">
                        <g:select class="input-small" name="accountContactAddress.address.state" data-ng-model="accountContactAddress.address.state" from="${com.cogda.common.UsState.values()}" optionKey="key"  noSelection="['': 'State']" />
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.zipcode'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.zipcode.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.zipcode"
                               data-ng-model="accountContactAddress.address.zipcode"
                               placeholder="${message(code:'accountContact.address.zipcode.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.county'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.county.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.county"
                               data-ng-model="accountContactAddress.address.county"
                               placeholder="${message(code:'accountContact.address.county.label')}"/>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactAddressAddForm['accountContactAddress.address.country'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.address.country.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="accountContactAddress.address.country"
                               data-ng-model="accountContactAddress.address.country"
                               placeholder="${message(code:'accountContact.address.country.label')}"/>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="saveAccountContactAddress(accountContactAddress)"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form class="form-horizontal" name="accountAddressAddForm">
            <fieldset>
                <div class="control-group fieldcontain">
                    <label class="control-label">
                        ${message(code:'accountAddress.primaryAddress.label')}
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.accountAddresses[addressIndex].primaryAddress==true}" data-ng-click="account.accountAddresses[addressIndex].primaryAddress=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.accountAddresses[addressIndex].primaryAddress==false}" data-ng-click="account.accountAddresses[addressIndex].primaryAddress=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['accountAddressType'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.accountAddressType.label')}
                    </label>
                    <div class="controls">
                        <g:select class="input-small" name="accountAddressType" data-ng-model="account.accountAddresses[addressIndex].accountAddressType.id" from="${com.cogda.multitenant.AccountAddressType.listOrderByCode()}" optionKey="id"  />
                        <span class="help-inline" data-ng-show="errors.accountAddressType ">{{ errors.accountAddressType }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['addressOne'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.addressOne.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               data-ng-model="account.accountAddresses[addressIndex].address.addressOne"
                               name="addressOne"
                               placeholder="${message(code:'accountAddress.address.addressOne.label')}"
                               />
                        <span class="help-inline error" data-ng-show="errors.address.addressOne "> {{ errors.addressOne }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['addressTwo'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.addressTwo.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="addressTwo"
                               data-ng-model="account.accountAddresses[addressIndex].address.addressTwo"
                               placeholder="${message(code:'accountAddress.address.addressTwo.label')}"/>
                        <span class="help-inline" data-ng-show="errors.address.addressTwo ">{{ errors.addressTwo }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['addressThree'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.addressThree.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="addressThree"
                               data-ng-model="account.accountAddresses[addressIndex].address.addressThree"
                               placeholder="${message(code:'accountAddress.address.addressThree.label')}"/>
                        <span class="help-inline" data-ng-show="errors.address.addressThree ">{{ errors.addressThree }}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['city'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.city.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="city"
                               data-ng-model="account.accountAddresses[addressIndex].address.city"
                               placeholder="${message(code:'accountAddress.address.city.label')}"/>
                        <span class="help-inline" data-ng-show="errors.address.city ">{{ errors.city }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['state'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.state.label')}
                    </label>
                    <div class="controls">
                        <g:select class="input-small" name="state" data-ng-model="account.accountAddresses[addressIndex].address.state" from="${com.cogda.common.UsState.values()}" optionKey="key"  noSelection="['': 'State']" />
                        <span class="help-inline" data-ng-show="errors.address.state ">{{ errors.state }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['zipcode'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.zipcode.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="zipcode"
                               data-ng-model="account.accountAddresses[addressIndex].address.zipcode"
                               placeholder="${message(code:'accountAddress.address.zipcode.label')}"/>
                        <span class="help-inline" data-ng-show="errors.address.zipcode ">{{ errors.zipcode }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddressAddForm['country'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountAddress.address.country.label')}
                    </label>
                    <div class="controls">
                        <input type="text"
                               name="country"
                               data-ng-model="account.accountAddresses[addressIndex].address.country"
                               placeholder="${message(code:'accountAddress.address.country.label')}"/>
                        <span class="help-inline" data-ng-show="errors.address.country ">{{ errors.country }}</span>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="updateAccount()" ><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>

    </div>
</div>
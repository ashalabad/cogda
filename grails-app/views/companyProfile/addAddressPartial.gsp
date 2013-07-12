<form class="form-horizontal"
      data-ng-controller="addCompanyProfileAddressController"
      name="companyProfileAddressAddForm">

    <fieldset>

        <legend>
            <g:message code="companyProfile.companyProfileAddresses.label" />
        </legend>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['published'].$invalid, success: companyProfileAddressAddForm['published'].$valid}">
            <label class="control-label">
                Published?
            </label>
            <div class="controls">
                <input type="checkbox" data-ng-model="companyProfileAddress.published" name="published"/>

                <span class="help-inline" data-ng-show="errors.published ">  {{ errors.published }}</span>
                <span class="error" ng-show="companyProfileAddressAddForm['published'].$invalid"> </span>
                <span class="success" ng-show="companyProfileAddressAddForm['published'].$valid"> </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['primaryAddress'].$invalid, success: companyProfileAddressAddForm['primaryAddress'].$valid}">
            <label class="control-label">
                Primary Address?
            </label>
            <div class="controls">
                <input type="checkbox" data-ng-model="companyProfileAddress.primaryAddress" name="primaryAddress" />

                <span class="help-inline" data-ng-show="errors.primaryAddress ">  {{ errors.primaryAddress }}</span>
                <span class="error" ng-show="companyProfileAddressAddForm['primaryAddress'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="success" ng-show="companyProfileAddressAddForm['primaryAddress'].$valid"> ${message(code:'default.valid.message')}  </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.addressOne'].$invalid, success: companyProfileAddressAddForm['address.addressOne'].$valid}">
            <label class="control-label" data-ng-click="">
                ${message(code:'companyProfileAddress.address.addressOne')}
                <span class="required-indicator">*</span>
            </label>
            <div class="controls">
                <input type="text"
                       data-ng-model="companyProfileAddress.address.addressOne"
                       name="address.addressOne"
                       required
                       placeholder="${message(code:'companyProfileAddress.address.addressOne')}"
                       autofocus />
                <span class="help-inline error" data-ng-show="errors.address.addressOne "> {{ errors.address.addressOne }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.addressOne'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.addressOne'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.addressTwo'].$invalid, success: companyProfileAddressAddForm['address.addressTwo'].$valid}">
            <label class="control-label">
                ${message(code:'companyProfileAddress.address.addressTwo')}
            </label>
            <div class="controls">
                <input type="text"
                       name="address.addressTwo"
                       data-ng-model="companyProfileAddress.address.addressTwo"
                       placeholder="${message(code:'companyProfileAddress.address.addressTwo')}"/>
                <span class="help-inline" data-ng-show="errors.address.addressTwo ">{{ errors.address.addressTwo }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.addressTwo'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.addressTwo'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.addressThree'].$invalid, success: companyProfileAddressAddForm['address.addressThree'].$valid}">
            <label class="control-label">
                ${message(code:'companyProfileAddress.address.addressThree')}
            </label>
            <div class="controls">
                <input type="text"
                       name="address.addressThree"
                       data-ng-model="companyProfileAddress.address.addressThree"
                       placeholder="${message(code:'companyProfileAddress.address.addressThree')}"/>
                <span class="help-inline" data-ng-show="errors.address.addressThree ">{{ errors.address.addressThree }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.addressThree'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.addressThree'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.zipcode'].$invalid, success: companyProfileAddressAddForm['address.zipcode'].$valid}">
            <label class="control-label">
                ${message(code:'companyProfileAddress.address.zipcode')}
            </label>
            <div class="controls">
                <input type="text"
                       name="address.zipcode"
                       data-ng-model="companyProfileAddress.address.zipcode"
                       placeholder="${message(code:'companyProfileAddress.address.zipcode')}"/>
                <span class="help-inline" data-ng-show="errors.address.zipcode ">{{ errors.address.zipcode }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.zipcode'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.zipcode'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.city'].$invalid, success: companyProfileAddressAddForm['address.city'].$valid}">
            <label class="control-label">
                ${message(code:'companyProfileAddress.address.city')}
            </label>
            <div class="controls">
                <input type="text"
                       name="address.city"
                       data-ng-model="companyProfileAddress.address.city"
                       placeholder="${message(code:'companyProfileAddress.address.city')}"/>
                <span class="help-inline" data-ng-show="errors.address.city ">{{ errors.address.city }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.city'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.city'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.state'].$invalid, success: companyProfileAddressAddForm['address.state'].$valid}">
            <label class="control-label">
                ${message(code:'companyProfileAddress.address.state')}
            </label>
            <div class="controls">
                <input type="text"
                       name="address.state"
                       data-ng-model="companyProfileAddress.address.state"
                       placeholder="${message(code:'companyProfileAddress.address.state')}"/>
                <span class="help-inline" data-ng-show="errors.address.state ">{{ errors.address.state }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.state'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.state'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: companyProfileAddressAddForm['address.country'].$invalid, success: companyProfileAddressAddForm['address.country'].$valid}">
            <label class="control-label">
                ${message(code:'companyProfileAddress.address.country')}
            </label>
            <div class="controls">
                <input type="text"
                       name="address.country"
                       data-ng-model="companyProfileAddress.address.country"
                       placeholder="${message(code:'companyProfileAddress.address.country')}"/>
                <span class="help-inline" data-ng-show="errors.address.country ">{{ errors.address.country }}</span>
                <span class="label label-important" ng-show="companyProfileAddressAddForm['address.country'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" ng-show="companyProfileAddressAddForm['address.country'].$valid"> ${message(code:'default.valid.message')} </span>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-success "
                    data-ng-click="addCompanyProfileAddress(companyProfileAddress)"
                    data-ng-model="addButton"
                    data-ng-disabled="!canSave(companyProfileAddress)">
                Update
            </button>
            <button class="btn btn-danger " data-ng-click="deleteCompanyProfileAddress(companyProfileAddress.address)" data-ng-model="deleteButton">
                <i class="icon-remove"></i> Delete
            </button>
            <button type="button" class="btn " data-ng-click="editingCompanyProfileAddress = false" data-ng-model="cancelButton">
                Cancel
            </button>
        </div>
</fieldset>
</form>
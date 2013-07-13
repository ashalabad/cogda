<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['published'].$invalid, success: companyProfileAddressForm['published'].$valid}">
    <label class="control-label">
        Published?
    </label>
    <div class="controls">
        <input type="checkbox" data-ng-model="companyProfileAddress.published" name="published"/>

        <span class="help-inline" data-ng-show="errors.published ">  {{ errors.published }}</span>
        <span class="error" ng-show="companyProfileAddressForm['published'].$invalid"> </span>
        <span class="success" ng-show="companyProfileAddressForm['published'].$valid"> </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['primaryAddress'].$invalid, success: companyProfileAddressForm['primaryAddress'].$valid}">
    <label class="control-label">
        Primary Address?
    </label>
    <div class="controls">
        <input type="checkbox" data-ng-model="companyProfileAddress.primaryAddress" name="primaryAddress" />

        <span class="help-inline" data-ng-show="errors.primaryAddress ">  {{ errors.primaryAddress }}</span>
        <span class="error" ng-show="companyProfileAddressForm['primaryAddress'].$invalid">  </span>
        <span class="success" ng-show="companyProfileAddressForm['primaryAddress'].$valid">  </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.addressOne'].$invalid, success: companyProfileAddressForm['address.addressOne'].$valid}">
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
        <span class="label label-important" ng-show="companyProfileAddressForm['address.addressOne'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.addressOne'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.addressTwo'].$invalid, success: companyProfileAddressForm['address.addressTwo'].$valid}">
    <label class="control-label">
        ${message(code:'companyProfileAddress.address.addressTwo')}
    </label>
    <div class="controls">
        <input type="text"
               name="address.addressTwo"
               data-ng-model="companyProfileAddress.address.addressTwo"
               placeholder="${message(code:'companyProfileAddress.address.addressTwo')}"/>
        <span class="help-inline" data-ng-show="errors.address.addressTwo ">{{ errors.address.addressTwo }}</span>
        <span class="label label-important" ng-show="companyProfileAddressForm['address.addressTwo'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.addressTwo'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.addressThree'].$invalid, success: companyProfileAddressForm['address.addressThree'].$valid}">
    <label class="control-label">
        ${message(code:'companyProfileAddress.address.addressThree')}
    </label>
    <div class="controls">
        <input type="text"
               name="address.addressThree"
               data-ng-model="companyProfileAddress.address.addressThree"
               placeholder="${message(code:'companyProfileAddress.address.addressThree')}"/>
        <span class="help-inline" data-ng-show="errors.address.addressThree ">{{ errors.address.addressThree }}</span>
        <span class="label label-important" ng-show="companyProfileAddressForm['address.addressThree'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.addressThree'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.zipcode'].$invalid, success: companyProfileAddressForm['address.zipcode'].$valid}">
    <label class="control-label">
        ${message(code:'companyProfileAddress.address.zipcode')}
    </label>
    <div class="controls">
        <input type="text"
               name="address.zipcode"
               data-ng-model="companyProfileAddress.address.zipcode"
               placeholder="${message(code:'companyProfileAddress.address.zipcode')}"/>
        <span class="help-inline" data-ng-show="errors.address.zipcode ">{{ errors.address.zipcode }}</span>
        <span class="label label-important" ng-show="companyProfileAddressForm['address.zipcode'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.zipcode'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.city'].$invalid, success: companyProfileAddressForm['address.city'].$valid}">
    <label class="control-label">
        ${message(code:'companyProfileAddress.address.city')}
    </label>
    <div class="controls">
        <input type="text"
               name="address.city"
               data-ng-model="companyProfileAddress.address.city"
               placeholder="${message(code:'companyProfileAddress.address.city')}"/>
        <span class="help-inline" data-ng-show="errors.address.city ">{{ errors.address.city }}</span>
        <span class="label label-important" ng-show="companyProfileAddressForm['address.city'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.city'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.state'].$invalid, success: companyProfileAddressForm['address.state'].$valid}">
    <label class="control-label">
        ${message(code:'companyProfileAddress.address.state')}
    </label>
    <div class="controls">

        <select data-ng-model = "companyProfileAddress.address.state"
                required
                data-ng-options="abbr as desc for (abbr, desc) in unitedStates"
                name="address.state">
            <option value="">-- choose --</option>
        </select>
        <span class="help-inline" data-ng-show="errors.address.state ">{{ errors.address.state }}</span>
        <span class="label label-important" ng-show="companyProfileAddressForm['address.state'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.state'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

<div class="control-group fieldcontain"
     data-ng-class="{error: companyProfileAddressForm['address.country'].$invalid, success: companyProfileAddressForm['address.country'].$valid}">
    <label class="control-label">
        ${message(code:'companyProfileAddress.address.country')}
    </label>
    <div class="controls">
        <g:countrySelect name="address.country" data-ng-model="companyProfileAddress.address.country" from="${com.cogda.domain.admin.SupportedCountryCode.retrieveSupportedCountryCodes()}"/>
        <span class="help-inline" data-ng-show="errors.address.country ">{{ errors.address.country }}</span>
        <span class="label label-important" ng-show="companyProfileAddressForm['address.country'].$invalid"> ${message(code:'default.invalid.message')} </span>
        <span class="label label-success" ng-show="companyProfileAddressForm['address.country'].$valid"> ${message(code:'default.valid.message')} </span>
    </div>
</div>

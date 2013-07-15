<fieldset class="embedded" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"><legend><g:message
        code="address.label" default="Address"/></legend>

    <div class="control-group fieldcontain">
        <label for="address.primaryAddress" class="control-label"><g:message
                code="leadAddress.primaryAddress.label"
                default="Primary Address"/></label>

        <div class="controls">
            <input type="checkbox" data-ng-model="address.primaryAddress" name="address.primaryAddress"
                   id="address.primaryAddress"/>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.addressOne'].$invalid, success: addressForm['address.address.addressOne'].$valid && addressForm['address.address.addressOne'].$dirty}">
        <label for="address.address.addressOne" class="control-label"><g:message
                code="address.streetAddressOne.label"
                default="Address One"/></label>

        <div class="controls">
            <input type="text" id='address.address.addressOne'
                   name='address.address.addressOne'
                   data-ng-model="address.address.addressOne"/>
            <span class="help-inline"
                  data-ng-show="errors['address.address.addressOne'] ">{{ errors['address.address.addressOne'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.addressOne'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.addressOne'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.addressTwo'].$invalid && addressForm['address.address.addressTwo'].$dirty, success: addressForm['address.address.addressTwo'].$valid && addressForm['address.address.addressTwo'].$dirty}">
        <label for="address.address.addressTwo" class="control-label"><g:message
                code="address.streetAddressTwo.label"
                default="Address Two"/></label>

        <div class="controls">
            <input type="text" id="address.address.addressTwo"
                   name="address.address.addressTwo"
                   data-ng-model="address.address.addressTwo"/>
            <span class="help-inline"
                  data-ng-show="errors['address.address.addressTwo'] ">{{ errors['address.address.addressTwo'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.addressTwo'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.addressTwo'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.addressThree'].$invalid && addressForm['address.address.addressThree'].$dirty, success: addressForm['address.address.addressThree'].$valid && addressForm['address.address.addressThree'].$dirty}">
        <label for="address.address.addressThree" class="control-label"><g:message
                code="address.streetAddressThree.label"
                default="Address Three"/></label>

        <div class="controls">
            <input type="text"
                   id="address.address.addressThree"
                   name="address.address.addressThree"
                   data-ng-model="address.address.addressThree"/>
            <span class="help-inline"
                  data-ng-show="errors['address.address.addressThree'] ">{{ errors['address.address.addressThree'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.addressThree'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.addressThree'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.city'].$invalid && addressForm['address.address.city'].$dirty, success: addressForm['address.address.city'].$valid && addressForm['address.address.city'].$dirty}">
        <label for="address.address.city" class="control-label"><g:message
                code="address.city.label"
                default="City"/></label>

        <div class="controls">
            <input type="text" name="address.address.city"
                   id="address.address.city"
                   data-ng-model="address.address.city"/>
            <span class="help-inline"
                  data-ng-show="errors['address.address.city'] ">{{ errors['address.address.city'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.city'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.city'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.state'].$invalid && addressForm['address.address.state'].$dirty, success: addressForm['address.address.state'].$valid && addressForm['address.address.state'].$dirty}">
        <label for="address.address.state" class="control-label"><g:message
                code="address.state.label"
                default="State"/></label>

        <div class="controls">
            <select id="address.address.state"
                    name="address.address.state"
                    data-ng-model='address.address.state'
                    data-ng-options='state.name as state.shortName for state in states'></select>
            <span class="help-inline"
                  data-ng-show="errors['address.address.state'] ">{{ errors['address.address.state'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.state'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.state'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.country'].$invalid && addressForm['address.address.country'].$dirty, success: addressForm['address.address.country'].$valid && addressForm['address.address.country'].$dirty}">
        <label for="address.address.country" class="control-label"><g:message
                code="address.country.label"
                default="Country"/></label>

        <div class="controls">
            <select name="address.address.country"
                    id="address.address.country"
                    data-ng-model='address.address.country'
                    data-ng-options='country.countryCode as country.countryDescription for country in countryCodes'></select>
            <span class="help-inline"
                  data-ng-show="errors['address.address.country'] ">{{ errors['address.address.country'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.country'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.country'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: addressForm['address.address.zipcode'].$invalid && addressForm['address.address.zipcode'].$dirty, success: addressForm['address.address.zipcode'].$valid && addressForm['address.address.zipcode'].$dirty}">
        <label for="address.address.zipcode" class="control-label"><g:message
                code="address.zipcode.label"
                default="Zip Code"/></label>

        <div class="controls">
            <input type="text" id="address.address.zipcode"
                   name="address.address.zipcode"
                   data-ng-model="address.address.zipcode"/>
            <span class="help-inline"
                  data-ng-show="errors['address.address.zipcode'] ">{{ errors['address.address.zipcode'] }}</span>
            <span class="error" data-ng-show="addressForm['address.address.zipcode'].$invalid"></span>
            <span class="success" data-ng-show="addressForm['address.address.zipcode'].$valid"></span>
        </div>
    </div>
</fieldset>

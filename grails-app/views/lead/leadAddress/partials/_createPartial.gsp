<fieldset class="embedded" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"><legend><g:message
        code="address.label" default="Address"/></legend>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.addressOne'].$invalid, success: leadForm['leadAddress.address.addressOne'].$valid && leadForm['leadAddress.address.addressOne'].$dirty}">
        <label for="leadAddress.address.addressOne" class="control-label"><g:message
                code="address.streetAddressOne.label"
                default="Address One"/></label>

        <div class="controls">
            <input type="text" id='leadAddress.address.addressOne'
                   name='leadAddress.address.addressOne'
                   data-ng-model="leadAddress.address.addressOne"/>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.addressOne'] ">{{ errors['leadAddress.address.addressOne'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.addressOne'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.addressOne'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.addressTwo'].$invalid && leadForm['leadAddress.address.addressTwo'].$dirty, success: leadForm['leadAddress.address.addressTwo'].$valid && leadForm['leadAddress.address.addressTwo'].$dirty}">
        <label for="leadAddress.address.addressTwo" class="control-label"><g:message
                code="address.streetAddressTwo.label"
                default="Address Two"/></label>

        <div class="controls">
            <input type="text" id="leadAddress.address.addressTwo"
                   name="leadAddress.address.addressTwo"
                   data-ng-model="leadAddress.address.addressTwo"/>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.addressTwo'] ">{{ errors['leadAddress.address.addressTwo'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.addressTwo'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.addressTwo'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.addressThree'].$invalid && leadForm['leadAddress.address.addressThree'].$dirty, success: leadForm['leadAddress.address.addressThree'].$valid && leadForm['leadAddress.address.addressThree'].$dirty}">
        <label for="leadAddress.address.addressThree" class="control-label"><g:message
                code="address.streetAddressThree.label"
                default="Address Three"/></label>

        <div class="controls">
            <input type="text"
                   id="leadAddress.address.addressThree"
                   name="leadAddress.address.addressThree"
                   data-ng-model="leadAddress.address.addressThree"/>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.addressThree'] ">{{ errors['leadAddress.address.addressThree'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.addressThree'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.addressThree'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.city'].$invalid && leadForm['leadAddress.address.city'].$dirty, success: leadForm['leadAddress.address.city'].$valid && leadForm['leadAddress.address.city'].$dirty}">
        <label for="leadAddress.address.city" class="control-label"><g:message
                code="address.city.label"
                default="City"/></label>

        <div class="controls">
            <input type="text" name="leadAddress.address.city"
                   id="leadAddress.address.city"
                   data-ng-model="leadAddress.address.city"/>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.city'] ">{{ errors['leadAddress.address.city'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.city'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.city'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.state'].$invalid && leadForm['leadAddress.address.state'].$dirty, success: leadForm['leadAddress.address.state'].$valid && leadForm['leadAddress.address.state'].$dirty}">
        <label for="leadAddress.address.state" class="control-label"><g:message
                code="address.state.label"
                default="State"/></label>

        <div class="controls">
            <select id="leadAddress.address.state"
                    name="leadAddress.address.state"
                    data-ng-model='leadAddress.address.state'
                    data-ng-options='abbr as desc for (abbr, desc) in states'>
                <option value="">-- choose --</option>
            </select>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.state'] ">{{ errors['leadAddress.address.state'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.state'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.state'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.country'].$invalid && leadForm['leadAddress.address.country'].$dirty, success: leadForm['leadAddress.address.country'].$valid && leadForm['leadAddress.address.country'].$dirty}">
        <label for="leadAddress.address.country" class="control-label"><g:message
                code="address.country.label"
                default="Country"/></label>

        <div class="controls">
            <select name="leadAddress.address.country"
                    id="leadAddress.address.country"
                    data-ng-model='leadAddress.address.country'
                    data-ng-options='country.countryCode as country.countryDescription for country in countryCodes'>
                <option value="">-- choose --</option>
            </select>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.country'] ">{{ errors['leadAddress.address.country'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.country'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.country'].$valid"></span>
        </div>
    </div>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadAddress.address.zipcode'].$invalid && leadForm['leadAddress.address.zipcode'].$dirty, success: leadForm['leadAddress.address.zipcode'].$valid && leadForm['leadAddress.address.zipcode'].$dirty}">
        <label for="leadAddress.address.zipcode" class="control-label"><g:message
                code="address.zipcode.label"
                default="Zip Code"/></label>

        <div class="controls">
            <input type="text" id="leadAddress.address.zipcode"
                   name="leadAddress.address.zipcode"
                   data-ng-model="leadAddress.address.zipcode"/>
            <span class="help-inline"
                  data-ng-show="errors['leadAddress.address.zipcode'] ">{{ errors['leadAddress.address.zipcode'] }}</span>
            <span class="error" data-ng-show="leadForm['leadAddress.address.zipcode'].$invalid"></span>
            <span class="success" data-ng-show="leadForm['leadAddress.address.zipcode'].$valid"></span>
        </div>
    </div>
/r</fieldset>

<fieldset class="embedded" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html"><legend><g:message
        code="address.label" default="Address"/></legend>

    <div data-ng-repeat="address in lead.leadAddresses">
        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.addressOne'].$invalid, success: leadForm['leadAddresses[0].address.addressOne'].$valid && leadForm['leadAddresses[0].address.addressOne'].$dirty}">
            <label for="leadAddresses[0].address.addressOne" class="control-label"><g:message
                    code="address.streetAddressOne.label"
                    default="Address One"/></label>

            <div class="controls">
                <input type="text" id='leadAddresses[0].address.addressOne'
                       name='leadAddresses[0].address.addressOne'
                       data-ng-model="lead.leadAddresses[$index].address.addressOne"/>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.addressOne'] ">{{ errors['leadAddresses[0].address.addressOne'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.addressOne'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.addressOne'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.addressTwo'].$invalid && leadForm['leadAddresses[0].address.addressTwo'].$dirty, success: leadForm['leadAddresses[0].address.addressTwo'].$valid && leadForm['leadAddresses[0].address.addressTwo'].$dirty}">
            <label for="leadAddresses[0].address.addressTwo" class="control-label"><g:message
                    code="address.streetAddressTwo.label"
                    default="Address Two"/></label>

            <div class="controls">
                <input type="text" id="leadAddresses[0].address.addressTwo"
                       name="leadAddresses[0].address.addressTwo"
                       data-ng-model="lead.leadAddresses[$index].address.addressTwo"/>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.addressTwo'] ">{{ errors['leadAddresses[0].address.addressTwo'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.addressTwo'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.addressTwo'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.addressThree'].$invalid && leadForm['leadAddresses[0].address.addressThree'].$dirty, success: leadForm['leadAddresses[0].address.addressThree'].$valid && leadForm['leadAddresses[0].address.addressThree'].$dirty}">
            <label for="leadAddresses[0].address.addressThree" class="control-label"><g:message
                    code="address.streetAddressThree.label"
                    default="Address Three"/></label>

            <div class="controls">
                <input type="text"
                       id="leadAddresses[0].address.addressThree"
                       name="leadAddresses[0].address.addressThree"
                       data-ng-model="lead.leadAddresses[$index].address.addressThree"/>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.addressThree'] ">{{ errors['leadAddresses[0].address.addressThree'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.addressThree'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.addressThree'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.city'].$invalid && leadForm['leadAddresses[0].address.city'].$dirty, success: leadForm['leadAddresses[0].address.city'].$valid && leadForm['leadAddresses[0].address.city'].$dirty}">
            <label for="leadAddresses[0].address.city" class="control-label"><g:message
                    code="address.city.label"
                    default="City"/></label>

            <div class="controls">
                <input type="text" name="leadAddresses[0].address.city"
                       id="leadAddresses[0].address.city"
                       data-ng-model="lead.leadAddresses[$index].address.city"/>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.city'] ">{{ errors['leadAddresses[0].address.city'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.city'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.city'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.state'].$invalid && leadForm['leadAddresses[0].address.state'].$dirty, success: leadForm['leadAddresses[0].address.state'].$valid && leadForm['leadAddresses[0].address.state'].$dirty}">
            <label for="leadAddresses[0].address.state" class="control-label"><g:message
                    code="address.state.label"
                    default="State"/></label>

            <div class="controls">
                <select id="leadAddresses[0].address.state"
                        name="leadAddresses[0].address.state"
                        data-ng-model='lead.leadAddresses[$index].address.state'
                        data-ng-options='state.name as state.shortName for state in states'></select>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.state'] ">{{ errors['leadAddresses[0].address.state'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.state'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.state'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.country'].$invalid && leadForm['leadAddresses[0].address.country'].$dirty, success: leadForm['leadAddresses[0].address.country'].$valid && leadForm['leadAddresses[0].address.country'].$dirty}">
            <label for="leadAddresses[0].address.country" class="control-label"><g:message
                    code="address.country.label"
                    default="Country"/></label>

            <div class="controls">
                <select name="leadAddresses[0].address.country"
                        id="leadAddresses[0].address.country"
                        data-ng-model='lead.leadAddresses[$index].address.country'
                        data-ng-options='country.countryCode as country.countryDescription for country in countryCodes'></select>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.country'] ">{{ errors['leadAddresses[0].address.country'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.country'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.country'].$valid"></span>
            </div>
        </div>

        <div class="control-group fieldcontain"
             data-ng-class="{error: leadForm['leadAddresses[0].address.zipcode'].$invalid && leadForm['leadAddresses[0].address.zipcode'].$dirty, success: leadForm['leadAddresses[0].address.zipcode'].$valid && leadForm['leadAddresses[0].address.zipcode'].$dirty}">
            <label for="leadAddresses[0].address.zipcode" class="control-label"><g:message
                    code="address.zipcode.label"
                    default="Zip Code"/></label>

            <div class="controls">
                <input type="text" id="leadAddresses[0].address.zipcode"
                       name="leadAddresses[0].address.zipcode"
                       data-ng-model="lead.leadAddresses[$index].address.zipcode"/>
                <span class="help-inline"
                      data-ng-show="errors['leadAddresses[0].address.zipcode'] ">{{ errors['leadAddresses[0].address.zipcode'] }}</span>
                <span class="error" data-ng-show="leadForm['leadAddresses[0].address.zipcode'].$invalid"></span>
                <span class="success" data-ng-show="leadForm['leadAddresses[0].address.zipcode'].$valid"></span>
            </div>
        </div>
    </div>
</fieldset>

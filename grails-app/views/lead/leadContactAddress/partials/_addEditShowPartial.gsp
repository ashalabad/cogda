<fieldset class="embedded">
    <div data-ng-controller="AddLeadContactAddressController">
        <div class="well" data-ng-show="addingContactAddress">
            <legend><g:message code="default.add.label" args="[message(code: 'contactAddress.label')]"/></legend>
            <div data-ng-form="contactAddressForm" class="form-horizontal">
                <div data-ng-include="" src="'/leadContactAddress/editPartial'"></div>
                %{--<g:render template="/lead/leadContactAddress/partials/editPartial"/>--}%
                <div class="form-actions">
                    <button type="submit"
                            class="btn btn-primary"
                            data-ng-click="saveContactAddress(address)">
                        <i class="icon-plus icon-white"></i>
                        <g:message code="default.add.label"
                                   args="[message(code: 'contactAddress.label')]"/>
                    </button>
                    <button type="button"
                            class="btn"
                            data-ng-click="cancelAddContactAddress()">
                        <i class="icon-ban-circle"></i>
                        <g:message code="default.button.cancel.label"/></button>
                </div>
            </div>
        </div>
        <br/>
        <button type="button"
                class="btn"
                data-ng-click="addContactAddress()"
                data-ng-hide="addingContactAddress">
            <i class="icon-plus"></i>
            <g:message code="default.add.label" args="[message(code: 'contactAddress.label')]"/>
        </button>
    </div>
    <br/>
    <div data-ng-repeat="address in contact.leadContactAddresses | orderBy:'primaryAddress':'reverse' | filter:searchString">
        <div data-ng-include="" src="'/leadContactAddress/indexPartial'"></div>
        %{--<g:render template="/lead/leadContactAddress/partials/indexPartial"/>--}%
    </div>
</fieldset>
<fieldset class="embedded">
    <div data-ng-repeat="address in lead.leadAddresses | orderBy:'primaryAddress':'reverse' | filter:searchString">
        <div data-ng-controller="EditAddressController">
            <div class="form-horizontal">
                <div class="well" data-ng-hide="editingAddress">
                    <div data-ng-include="" src="'/leadAddress/showPartial'"></div>
                    %{--<g:render template="/lead/leadAddress/partials/showPartial"/>--}%
                </div>

                <div class="well" data-ng-show="editingAddress" data-ng-form="addressForm">
                    <div data-ng-include="" src="'/leadAddress/editPartial'"></div>
                    %{--<g:render template="/lead/leadAddress/partials/editPartial"/>--}%
                    <div class="form-actions">
                        <button type="submit"
                                class="btn btn-primary"
                                data-ng-click="updateAddress(address)">
                            <i class="icon-pencil icon-white"></i>
                            <g:message code="default.button.update.label"/> <g:message code="address.label"
                                                                                       default="Address"/>
                        </button>
                        <button class="btn btn-danger"
                                type="button"
                                data-ng-click="deleteAddress(address)">
                            <i class="icon-remove icon-white"></i>
                            <g:message code="default.button.delete.label"/> <g:message code="address.label"
                                                                                       default="Address"/>
                        </button>
                        <button type="button"
                                class="btn"
                                data-ng-click="cancelEditAddress()">
                            <i class="icon-ban-circle"></i>
                            <g:message code="default.button.cancel.label"/></button>
                    </div>
                </div>

            </div>
        </div>
    </div>


    <div data-ng-controller="AddAddressController">
        <div class="well" data-ng-show="addingAddress">
            <div data-ng-form="addressForm" class="form-horizontal">
                <fieldset class="embedded">
                    <legend>
                        <g:message code="default.add.label" args="[message(code: 'address.label')]"/>
                    </legend>
                    <div data-ng-include="" src="'/leadAddress/editPartial'"></div>
                    %{--<g:render template="/lead/leadAddress/partials/editPartial"/>--}%

                    <div class="form-actions">
                        <button type="submit"
                                class="btn btn-primary"
                                data-ng-click="saveAddress(address)">
                            <i class="icon-plus icon-white"></i>
                            <g:message code="default.add.label" args="[message(code: 'address.label')]"/>
                        </button>
                        <button type="button"
                                class="btn"
                                data-ng-click="cancelAddAddress()">
                            <i class="icon-ban-circle"></i>
                            <g:message code="default.button.cancel.label"/></button>
                    </div>
                </fieldset>
            </div>
        </div>

        <button type="button"
                class="btn"
                data-ng-click="addAddress()"
                data-ng-hide="addingAddress">
            <i class="icon-plus"></i>
            <g:message code="default.add.label" args="[message(code: 'address.label')]"/>
        </button>
    </div>
</fieldset>
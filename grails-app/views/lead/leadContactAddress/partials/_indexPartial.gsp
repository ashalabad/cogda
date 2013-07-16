<div data-ng-controller="EditLeadContactAddressController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactAddress">
            <g:render template="/lead/leadContactAddress/partials/showPartial"/>
        </div>

        <div class="well" data-ng-show="editingContactAddress" data-ng-form="contactAddressForm">
            <g:render template="/lead/leadContactAddress/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactAddress(contactAddress)">
                    <i class="icon-pencil icon-white"></i>
                    Update Contact Address
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactAddress(contactAddress, $index)">
                    <i class="icon-remove icon-white"></i>
                    Delete Contact Address
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContactAddress()">
                    <i class="icon-ban-circle"></i>
                    Cancel</button>
            </div>
        </div>

    </div>
</div>



<div data-ng-controller="EditLeadContactEmailAddressController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactEmailAddress">
            <g:render template="/lead/leadContactEmailAddress/partials/showPartial"/>
        </div>

        <div class="well" data-ng-show="editingContactEmailAddress" data-ng-form="contactEmailAddressForm">
            <g:render template="/lead/leadContactEmailAddress/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactEmailAddress(contactEmailAddress)">
                    <i class="icon-pencil icon-white"></i>
                    Update Contact Email Address
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactEmailAddress(contactEmailAddress, $index)">
                    <i class="icon-remove icon-white"></i>
                    Delete Contact Email Address
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContactEmailAddress()">
                    <i class="icon-ban-circle"></i>
                    Cancel</button>
            </div>
        </div>

    </div>
</div>



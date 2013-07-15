<div data-ng-controller="EditLeadContactEmailAddressController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactEmailAddress">
            <g:render template="/lead/leadContactEmailAddress/partials/showPartial"/>
        </div>

        <div class="well" data-ng-show="editingContactEmailAddress" data-ng-form="ContactEmailAddressForm">
            <g:render template="/lead/leadContactEmailAddress/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactEmailAddress(ContactEmailAddress)">
                    <i class="icon-pencil icon-white"></i>
                    Update Contact Email Address
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactEmailAddress(ContactEmailAddress, $index)">
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
        <button class="btn btn-info"
                type="button"
                data-ng-click="editContactEmailAddress()"
                data-ng-hide="editContactEmailAddress">
            <i class="icon-edit icon-white"></i>
            Edit Contact Email Address
        </button>
        <button class="btn btn-danger"
                type="button"
                data-ng-click="deleteContactEmailAddress(ContactEmailAddress)"
                data-ng-hide="editingContactEmailAddress">
            <i class="icon-remove icon-white"></i>
            Delete Contact Email Address
        </button>
    </div>
</div>


<div data-ng-controller="AddContactEmailAddressController">
    <div class="well" data-ng-show="addingContactEmailAddress">
        <div data-ng-form="ContactEmailAddressForm" class="form-horizontal">
            <fieldset class="embedded">
                <legend>
                    Add Contact Email Address
                </legend>
                <g:render template="/lead/leadContactEmailAddress/partials/editPartial"/>

                <div class="form-actions">
                    <button type="submit"
                            class="btn btn-primary"
                            data-ng-click="saveContactEmailAddress(ContactEmailAddress)">
                        <i class="icon-plus icon-white"></i>
                        Add Contact Email Address</button>
                    <button type="button"
                            class="btn"
                            data-ng-click="cancelAddContactEmailAddress()">
                        <i class="icon-ban-circle"></i>
                        Cancel</button>
                </div>
            </fieldset>
        </div>
    </div>

    <br>

    <button type="button"
            class="btn btn-large"
            data-ng-click="addContactEmailAddress()"
            data-ng-hide="addingContactEmailAddress">
        <i class="icon-plus"></i>
        Add Contact Email Address
    </button>
</div>
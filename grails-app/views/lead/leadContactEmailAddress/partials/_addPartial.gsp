<div data-ng-controller="AddContactEmailAddressController">
    <div class="well" data-ng-show="addingContactEmailAddress">
        <div data-ng-form="contactEmailAddressForm" class="form-horizontal">
            <fieldset class="embedded">
                <legend>
                    Add Contact Email Address
                </legend>
                <g:render template="/lead/leadContactEmailAddress/partials/editPartial"/>

                <div class="form-actions">
                    <button type="submit"
                            class="btn btn-primary"
                            data-ng-click="saveContactEmailAddress(contactEmailAddress)">
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
            class="btn"
            data-ng-click="addContactEmailAddress()"
            data-ng-hide="addingContactEmailAddress">
        <i class="icon-plus"></i>
        Add Contact Email Address
    </button>
</div>
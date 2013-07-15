<div data-ng-controller="EditLeadContactPhoneNumberController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactPhoneNumber">
            <g:render template="/lead/leadContactPhoneNumber/partials/showPartial"/>
        </div>

        <div class="well" data-ng-show="editingContactPhoneNumber" data-ng-form="ContactPhoneNumberForm">
            <g:render template="/lead/leadContactPhoneNumber/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactPhoneNumber(ContactPhoneNumber)">
                    <i class="icon-pencil icon-white"></i>
                    Update Contact Phone Number
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactPhoneNumber(ContactPhoneNumber, $index)">
                    <i class="icon-remove icon-white"></i>
                    Delete Contact Phone Number
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContactPhoneNumber()">
                    <i class="icon-ban-circle"></i>
                    Cancel</button>
            </div>
        </div>
        <button class="btn btn-info"
                type="button"
                data-ng-click="editContactPhoneNumber()"
                data-ng-hide="editContactPhoneNumber">
            <i class="icon-edit icon-white"></i>
            Edit Contact Phone Number
        </button>
        <button class="btn btn-danger"
                type="button"
                data-ng-click="deleteContactPhoneNumber(contactPhoneNumber)"
                data-ng-hide="editingContactPhoneNumber">
            <i class="icon-remove icon-white"></i>
            Delete Contact Phone Number
        </button>
    </div>
</div>


<div data-ng-controller="AddContactPhoneNumberController">
    <div class="well" data-ng-show="addingContactPhoneNumber">
        <div data-ng-form="ContactPhoneNumberForm" class="form-horizontal">
            <fieldset class="embedded">
                <legend>
                    Add Contact Phone Number
                </legend>
                <g:render template="/lead/leadContactPhoneNumber/partials/editPartial"/>

                <div class="form-actions">
                    <button type="submit"
                            class="btn btn-primary"
                            data-ng-click="saveContactPhoneNumber(contactPhoneNumber)">
                        <i class="icon-plus icon-white"></i>
                        Add Contact Phone Number</button>
                    <button type="button"
                            class="btn"
                            data-ng-click="cancelAddContactPhoneNumber()">
                        <i class="icon-ban-circle"></i>
                        Cancel</button>
                </div>
            </fieldset>
        </div>
    </div>

    <br>

    <button type="button"
            class="btn btn-large"
            data-ng-click="addContactPhoneNumber()"
            data-ng-hide="addingContactPhoneNumber">
        <i class="icon-plus"></i>
        Add Contact Phone Number
    </button>
</div>
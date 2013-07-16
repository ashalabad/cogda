<div data-ng-controller="EditLeadContactPhoneNumberController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContactPhoneNumber">
            <g:render template="/lead/leadContactPhoneNumber/partials/showPartial"/>
        </div>

        <div class="well" data-ng-show="editingContactPhoneNumber" data-ng-form="contactPhoneNumberForm">
            <g:render template="/lead/leadContactPhoneNumber/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContactPhoneNumber(contactPhoneNumber)">
                    <i class="icon-pencil icon-white"></i>
                    Update Contact Phone Number
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContactPhoneNumber(contactPhoneNumber, $index)">
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
    </div>
</div>



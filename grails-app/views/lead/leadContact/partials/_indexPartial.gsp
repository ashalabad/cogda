<div data-ng-controller="EditContactController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContact">
            <g:render template="/lead/leadContact/partials/showPartial"/>
        </div>

        <div class="well" data-ng-show="editingContact" data-ng-form="contactForm">
            <g:render template="/lead/leadContact/partials/editPartial"/>
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContact(contact)">
                    <i class="icon-pencil icon-white"></i>
                    Update Contact
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContact(contact, $index)">
                    <i class="icon-remove icon-white"></i>
                    Delete Contact
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContact()">
                    <i class="icon-ban-circle"></i>
                    Cancel</button>
            </div>
        </div>
        <button class="btn btn-info"
                type="button"
                data-ng-click="editContact()"
                data-ng-hide="editContact">
            <i class="icon-edit icon-white"></i>
            Edit Contact
        </button>
        <button class="btn btn-danger"
                type="button"
                data-ng-click="deleteContact(contact)"
                data-ng-hide="editingContact">
            <i class="icon-remove icon-white"></i>
            Delete Contact
        </button>
    </div>
</div>



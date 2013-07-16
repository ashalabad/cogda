<div data-ng-controller="EditContactController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContact">
            <g:render template="/lead/leadContact/partials/showPartial"/>
            <tabset>
                <tab heading="Contact Address">
                    <fieldset class="embedded">
                        <div data-ng-repeat="address in contact.leadContactAddresses">
                            <g:render template="/lead/leadContactAddress/partials/indexPartial"/>
                        </div>

                        <div data-ng-controller="AddLeadContactAddressController">
                            <div class="well" data-ng-show="addingContactAddress">
                                <div data-ng-form="contactAddressForm" class="form-horizontal">
                                    <fieldset class="embedded">
                                        <legend>
                                            Add Contact Address
                                        </legend>
                                        <g:render template="/lead/leadContactAddress/partials/editPartial"/>

                                        <div class="form-actions">
                                            <button type="submit"
                                                    class="btn btn-primary"
                                                    data-ng-click="saveContactAddress(address)">
                                                <i class="icon-plus icon-white"></i>
                                                Add Contact Address</button>
                                            <button type="button"
                                                    class="btn"
                                                    data-ng-click="cancelAddContactAddress()">
                                                <i class="icon-ban-circle"></i>
                                                Cancel</button>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>

                            <br>

                            <button type="button"
                                    class="btn"
                                    data-ng-click="addContactAddress()"
                                    data-ng-hide="addingContactAddress">
                                <i class="icon-plus"></i>
                                Add Contact Address
                            </button>
                        </div>
                    </fieldset>
                </tab>
                <tab heading="Contact Phone Numbers">
                    <fieldset class="embedded">
                        <legend></legend>

                        <div data-ng-repeat="contactPhoneNumber in contact.leadContactPhoneNumbers">
                            <g:render template="/lead/leadContactPhoneNumber/partials/indexPartial"/>
                        </div>
                        <g:render template="/lead/leadContactPhoneNumber/partials/addPartial"/>
                    </fieldset>
                </tab>
                <tab heading="Contact Email Addresses">
                    <fieldset class="embedded">
                        <legend>Contact Email Addresses</legend>

                        <div data-ng-repeat="contactEmailAddress in contact.leadContactEmailAddresses">
                            <g:render template="/lead/leadContactEmailAddress/partials/indexPartial"/>
                        </div>
                        <g:render template="/lead/leadContactEmailAddress/partials/addPartial"/>
                    </fieldset>
                </tab>
            </tabset>
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



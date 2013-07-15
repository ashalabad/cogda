<form class="form-horizontal" name="leadForm">
<fieldset>
<legend>
    <g:message code="suspect.label"/>
</legend>

<div data-ng-hide="editingLead">
    <g:render template="/lead/partials/showPartial"/>

    <button type="button"
            class="btn btn-info"
            data-ng-click="editLead()"
            data-ng-hide="editingLead">
        <i class="icon-edit icon-white"></i>
        Edit Suspect
    </button>
</div>
<hr>

<div data-ng-show="editingLead">
    <g:render template="/lead/partials/editPartial"/>
    <div class="form-actions">
        <button type="submit"
                class="btn btn-primary"
                data-ng-click="updateLead(lead)">
            <i class="icon-pencil icon-white"></i>
            Update Suspect
        </button>
        <button type="button"
                class="btn"
                data-ng-click="cancelEditLead()">
            <i class="icon-ban-circle"></i>
            Cancel
        </button>
    </div>
</div>

<fieldset class="embedded">
    <legend>Addresses</legend>

    <div data-ng-repeat="address in lead.leadAddresses">
        <div data-ng-controller="EditAddressController">
            <div class="form-horizontal">
                <div class="well" data-ng-hide="editingAddress">
                    <g:render template="/lead/leadAddress/partials/showPartial"/>
                </div>

                <div class="well" data-ng-show="editingAddress" data-ng-form="addressForm">
                    <g:render template="/lead/leadAddress/partials/editPartial"/>
                    <div class="form-actions">
                        <button type="submit"
                                class="btn btn-primary"
                                data-ng-click="updateAddress(address)">
                            <i class="icon-pencil icon-white"></i>
                            Update Address
                        </button>
                        <button class="btn btn-danger"
                                type="button"
                                data-ng-click="deleteAddress(address, $index)">
                            <i class="icon-remove icon-white"></i>
                            Delete Address
                        </button>
                        <button type="button"
                                class="btn"
                                data-ng-click="cancelEditAddress()">
                            <i class="icon-ban-circle"></i>
                            Cancel</button>
                    </div>
                </div>
                <button class="btn btn-info"
                        type="button"
                        data-ng-click="editAddress()"
                        data-ng-hide="editingAddress">
                    <i class="icon-edit icon-white"></i>
                    Edit Address
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteAddress(address)"
                        data-ng-hide="editingAddress">
                    <i class="icon-remove icon-white"></i>
                    Delete Address
                </button>
            </div>
        </div>
    </div>


    <div data-ng-controller="AddAddressController">
        <div class="well" data-ng-show="addingAddress">
            <div data-ng-form="addressForm" class="form-horizontal">
                <fieldset class="embedded">
                    <legend>
                        Add Address
                    </legend>
                    <g:render template="/lead/leadAddress/partials/editPartial"/>

                    <div class="form-actions">
                        <button type="submit"
                                class="btn btn-primary"
                                data-ng-click="saveAddress(address)">
                            <i class="icon-plus icon-white"></i>
                            Add Address</button>
                        <button type="button"
                                class="btn"
                                data-ng-click="cancelAddAddress()">
                            <i class="icon-ban-circle"></i>
                            Cancel</button>
                    </div>
                </fieldset>
            </div>
        </div>

        <br>

        <button type="button"
                class="btn btn-large"
                data-ng-click="addAddress()"
                data-ng-hide="addingAddress">
            <i class="icon-plus"></i>
            Add Address
        </button>
    </div>
</fieldset>
<hr>
<fieldset class="embedded">
    <legend>Contacts</legend>

    <div data-ng-repeat="contact in lead.leadContacts">
        <g:render template="/lead/leadContact/partials/indexPartial"/>

        <fieldset class="embedded">
            <legend>Contact Addresses</legend>

            <div data-ng-repeat="address in contact.leadContactAddresses">
                <g:render template="/lead/leadContactAddress/partials/indexPartial"/>
            </div>

            <div data-ng-controller="AddContactAddressController">
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
                                        data-ng-click="saveContactAddress(contactAddress)">
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
                        class="btn btn-large"
                        data-ng-click="addContactAddress()"
                        data-ng-hide="addingContactAddress">
                    <i class="icon-plus"></i>
                    Add Contact Address
                </button>
            </div>
        </fieldset>
        <fieldset class="embedded">
            <legend>Contact Phone Numbers</legend>

            <div data-ng-repeat="leadContactPhoneNumber in contact.leadContactPhoneNumbers">
                <g:render template="/lead/leadContactPhoneNumber/partials/indexPartial"/>
            </div>
        </fieldset>
        <fieldset class="embedded">
            <legend>Contact Email Addresses</legend>

            <div data-ng-repeat="leadContactEmailAddress in contact.leadContactEmailAddresses">
                <g:render template="/lead/leadContactEmailAddress/partials/indexPartial"/>
            </div>
        </fieldset>
    </div>

    <div data-ng-controller="AddContactController">
        <div class="well" data-ng-show="addingContact">
            <div data-ng-form="contactForm" class="form-horizontal">
                <fieldset class="embedded">
                    <legend>
                        Add Contact
                    </legend>
                    <g:render template="/lead/leadContact/partials/editPartial"/>

                    <div class="form-actions">
                        <button type="submit"
                                class="btn btn-primary"
                                data-ng-click="saveContact(address)">
                            <i class="icon-plus icon-white"></i>
                            Add Contact</button>
                        <button type="button"
                                class="btn"
                                data-ng-click="cancelAddContact()">
                            <i class="icon-ban-circle"></i>
                            Cancel</button>
                    </div>
                </fieldset>
            </div>
        </div>

        <br>

        <button type="button"
                class="btn btn-large"
                data-ng-click="addContact()"
                data-ng-hide="addingContact">
            <i class="icon-plus"></i>
            Add Contact
        </button>
    </div>
</fieldset>

</fieldset>
</form>


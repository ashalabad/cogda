<form class="form-horizontal" name="leadForm" novalidate>
<fieldset>
<legend>
    <g:message code="suspect.label"/>
</legend>
<div class="row">
    <div class="span8">
        <button type="button" class="btn btn-mini" data-ng-click="editLead()"><i class="icon-edit"></i> <g:message code="default.edit.label" args="[message(code:'suspect.label')]"  /></button>
        <button type="button" class="btn btn-mini" data-ng-click="attachDocument()"><i class="icon-upload"></i> <g:message code="default.attach.label" args="[message(code:'document.label')]" /></button>
        <button type="button" class="btn btn-mini" data-ng-click="addAccountNote()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'leadNote.label')]" /></button>
        <button type="button" class="btn btn-mini" data-ng-click="addAccountContact()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'leadContact.label')]" /></button>
        <button type="button" class="btn btn-mini" data-ng-click="addAccountAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'leadAddress.label')]" /></button>
    </div>
    <div class="span4">
        <form class="form-search">
            <div class="input-append pull-left">
                <input type="text" class="span3 search-query" data-ng-model="searchString.$" placeholder="Search Prospect...">
            </div>
        </form>
    </div>
</div>
<tabset>
<tab heading="Main">

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
</tab>
<tab heading="Addresses ({{lead.leadAddresses.length}})">
    <fieldset class="embedded">
        <legend>Addresses</legend>

        <div data-ng-repeat="address in lead.leadAddresses | orderBy:'primaryAddress':'reverse' | filter:searchString">
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
    </fieldset></tab>
<tab heading="Contacts ({{lead.leadContacts.length}})">
    <fieldset class="embedded">
        <legend>Contacts</legend>
        <tabset>
            <tab data-ng-repeat="contact in lead.leadContacts"
                 heading="Contact - {{contact.firstName}} {{contact.lastName}}">

                <g:render template="/lead/leadContact/partials/indexPartial"/>
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
                        </fieldset>
                    </tab>
                </tabset>
            </tab>
        </tabset>

        <div data-ng-controller="AddLeadContactController">
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
</tab>
<tab heading="Documents ({{lead.files.length}})"></tab>
<tab heading="Notes ({{lead.leadNotes.length}})">
    <ul class="inline">
        <li data-ng-repeat="leadNote in lead.leadNotes | orderBy:'lastUpdated' | filter:searchString" class="span11 well well-small">
            <address>
                <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountNote(note)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                <strong>{{leadNote.lastUpdated | date:'MM/dd/yyyy @ h:mm a'}}<br> {{leadNote.noteType.code}} </strong><br>
                <p class="text-info"><em>{{leadNote.note.notes}}</em></p>
            </address>
        </li>
    </ul>

    <div data-ng-controller="AddNoteController">
        <div class="well" data-ng-show="addingLeadNote">
            <div data-ng-form="leadNoteForm" class="form-horizontal">
                <fieldset class="embedded">
                    <legend>
                        Add Note
                    </legend>
                    <g:render template="/lead/leadNote/partials/editPartial"/>

                    <div class="form-actions">
                        <button type="submit"
                                class="btn btn-primary"
                                data-ng-click="saveLeadNote(leadNote)">
                            <i class="icon-plus icon-white"></i>
                            Add Note</button>
                        <button type="button"
                                class="btn"
                                data-ng-click="cancelAddLeadNote()">
                            <i class="icon-ban-circle"></i>
                            Cancel</button>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
</tab>
</tabset>
</fieldset>
</form>


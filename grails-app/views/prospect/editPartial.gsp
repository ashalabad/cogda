<form class="form-horizontal" name="leadForm" novalidate>
<fieldset>
<legend>
    <g:message code="prospect.label"/>
</legend>

<div class="row">
    <div class="span4 pull-right">
        <form class="form-search">
            <div class="input-append pull-left">
                <input type="text" class="span3 search-query" data-ng-model="searchString.$"
                       placeholder="Search Prospect...">
            </div>
        </form>
    </div>
</div>
<tabset>
    <tab heading="Main">

        <div data-ng-hide="editingLead">
            <div data-ng-include="" src="'/lead/showPartial'"></div>
            %{--<g:render template="/lead/partials/showPartial"/>--}%

        </div>
        <hr>

        <div data-ng-show="editingLead">
            <div data-ng-include="" src="'/lead/editPartial'"></div>
            %{--<g:render template="/lead/partials/editPartial"/>--}%

        </div>
    </tab>
    <tab heading="Addresses ({{lead.leadAddresses.length}})">
        <fieldset class="embedded">
            <div data-ng-repeat="address in lead.leadAddresses | orderBy:'primaryAddress':'reverse' | filter:searchString">
                <div data-ng-controller="EditAddressController">
                    <div class="form-horizontal">
                        <div class="well" data-ng-hide="editingAddress">
                            <div data-ng-include="" src="'/leadAddress/showPartial'"></div>
                            %{--<g:render template="/lead/leadAddress/partials/showPartial"/>--}%
                        </div>

                        <div class="well" data-ng-show="editingAddress" data-ng-form="addressForm">
                            <div data-ng-include="" src="'/leadAddress/editPartial'"></div>
                            %{--<g:render template="/lead/leadAddress/partials/editPartial"/>--}%
                            <div class="form-actions">
                                <button type="submit"
                                        class="btn btn-primary"
                                        data-ng-click="updateAddress(address)">
                                    <i class="icon-pencil icon-white"></i>
                                    <g:message code="default.button.update.label"/> <g:message code="address.label"
                                                                                               default="Address"/>
                                </button>
                                <button class="btn btn-danger"
                                        type="button"
                                        data-ng-click="deleteAddress(address, $index)">
                                    <i class="icon-remove icon-white"></i>
                                    <g:message code="default.button.delete.label"/> <g:message code="address.label"
                                                                                               default="Address"/>
                                </button>
                                <button type="button"
                                        class="btn"
                                        data-ng-click="cancelEditAddress()">
                                    <i class="icon-ban-circle"></i>
                                    <g:message code="default.button.cancel.label"/></button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>


            <div data-ng-controller="AddAddressController">
                <div class="well" data-ng-show="addingAddress">
                    <div data-ng-form="addressForm" class="form-horizontal">
                        <fieldset class="embedded">
                            <legend>
                                <g:message code="default.add.label" args="[message(code: 'address.label')]"/>
                            </legend>
                            <div data-ng-include="" src="'/leadAddress/editPartial'"></div>
                            %{--<g:render template="/lead/leadAddress/partials/editPartial"/>--}%

                            <div class="form-actions">
                                <button type="submit"
                                        class="btn btn-primary"
                                        data-ng-click="saveAddress(address)">
                                    <i class="icon-plus icon-white"></i>
                                    <g:message code="default.add.label" args="[message(code: 'address.label')]"/>
                                </button>
                                <button type="button"
                                        class="btn"
                                        data-ng-click="cancelAddAddress()">
                                    <i class="icon-ban-circle"></i>
                                    <g:message code="default.button.cancel.label"/></button>
                            </div>
                        </fieldset>
                    </div>
                </div>

                <br>

                <button type="button"
                        class="btn"
                        data-ng-click="addAddress()"
                        data-ng-hide="addingAddress">
                    <i class="icon-plus"></i>
                    <g:message code="default.add.label" args="[message(code: 'address.label')]"/>
                </button>
            </div>
        </fieldset></tab>
    <tab heading="Contacts ({{lead.leadContacts.length}})">
        <fieldset class="embedded">
            <tabset>
                <tab data-ng-repeat="contact in lead.leadContacts  | orderBy:'primaryContact':'reverse' | filter:searchString"
                     heading="Contact - {{contact.firstName}} {{contact.lastName}}">
                    <div data-ng-include="" src="'/leadContact/indexPartial'"></div>
                    %{--<g:render template="/lead/leadContact/partials/indexPartial"/>--}%

                </tab>
            </tabset>

            <div data-ng-controller="AddLeadContactController">
                <div class="well" data-ng-show="addingContact">
                    <div data-ng-form="contactForm" class="form-horizontal">
                        <fieldset class="embedded">
                            <legend>
                                <g:message code="default.add.label" args="[message(code: 'contact.label')]"/>
                            </legend>
                            <div data-ng-include="" src="'/leadContact/editPartial'"></div>
                            %{--<g:render template="/lead/leadContact/partials/editPartial"/>--}%

                            <div class="form-actions">
                                <button type="submit"
                                        class="btn btn-primary"
                                        data-ng-click="saveContact(contact)">
                                    <i class="icon-plus icon-white"></i>
                                    <g:message code="default.add.label" args="[message(code: 'contact.label')]"/>
                                </button>
                                <button type="button"
                                        class="btn"
                                        data-ng-click="cancelAddContact()">
                                    <i class="icon-ban-circle"></i>
                                    <g:message code="default.button.cancel.label"/></button>
                            </div>
                        </fieldset>
                    </div>
                </div>

                <br>

                <button type="button"
                        class="btn"
                        data-ng-click="addContact()"
                        data-ng-hide="addingContact">
                    <i class="icon-plus"></i>
                    <g:message code="default.add.label" args="[message(code: 'contact.label')]"/>
                </button>
            </div>

        </fieldset>
    </tab>
    <tab heading="Documents ({{lead.files.length}})">
        <div class="well">Coming Soon...</div>
    </tab>

    <tab heading="Notes ({{lead.leadNotes.length}})">
        <ul class="inline">
            <li data-ng-repeat="leadNote in lead.leadNotes | orderBy:'lastUpdated' | filter:searchString"
                class="span11 well well-small">
                <div data-ng-controller="EditLeadNoteController">
                    <address data-ng-hide="editingLeadNote">
                        <button type="button" class="btn btn-danger btn-mini pull-right"
                                data-ng-click="deleteLeadNote()"><i
                                class="icon-edit"></i> <g:message code="default.button.delete.label"/></button>
                        <button type="button" class="btn btn-info btn-mini pull-right" data-ng-click="editLeadNote()"><i
                                class="icon-edit"></i> <g:message code="default.button.edit.label"/></button>
                        <strong>{{leadNote.lastUpdated | date:'MM/dd/yyyy @ h:mm a'}}<br> {{leadNote.noteType.code}}
                        </strong><br>

                        <p class="text-info"><em>{{leadNote.note.notes}}</em></p>
                    </address>

                    <div class="well" data-ng-show="editingLeadNote" data-ng-form="leadNoteForm">
                        <div data-ng-include src="'/leadNote/editPartial'"></div>
                        %{--<g:render template="/lead/leadNote/partials/editPartial"/>--}%
                        <div class="form-actions">
                            <button type="submit"
                                    class="btn btn-primary"
                                    data-ng-click="updateLeadNote(leadNote)">
                                <i class="icon-pencil icon-white"></i>
                                <g:message code="default.button.update.label" default="Update"/> <g:message
                                        code="note.label" default="Note"/>
                            </button>
                            <button class="btn btn-danger"
                                    type="button"
                                    data-ng-click="deleteLeadNote(leadNote, $index)">
                                <i class="icon-remove icon-white"></i>
                                <g:message code="default.button.delete.label" default="Delete"/> <g:message
                                        code="note.label" default="Note"/>
                            </button>
                            <button type="button"
                                    class="btn"
                                    data-ng-click="cancelEditLeadNote()">
                                <i class="icon-ban-circle"></i>
                                <g:message code="default.button.cancel.label"/></button>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div data-ng-include="" src="'/leadNote/addPartial'"></div>
        %{--<g:render template="/lead/leadNote/partials/addPartial"/>--}%

    </tab>
</tabset>
</fieldset>
</form>


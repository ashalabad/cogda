<div data-ng-controller="EditContactController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContact">
            <g:render template="/lead/leadContact/partials/showPartial"/>
            <tabset>
                <tab heading="Contact Addresses ({{contact.leadContactAddresses.length }})">
                    <fieldset class="embedded">
                        <div data-ng-repeat="address in contact.leadContactAddresses | orderBy:'primaryAddress':'reverse' | filter:searchString">
                            <g:render template="/lead/leadContactAddress/partials/indexPartial"/>
                        </div>

                        <div data-ng-controller="AddLeadContactAddressController">
                            <div class="well" data-ng-show="addingContactAddress">
                                <div data-ng-form="contactAddressForm" class="form-horizontal">
                                    <fieldset class="embedded">
                                        <g:render template="/lead/leadContactAddress/partials/editPartial"/>

                                        <div class="form-actions">
                                            <button type="submit"
                                                    class="btn btn-primary"
                                                    data-ng-click="saveContactAddress(address)">
                                                <i class="icon-plus icon-white"></i>
                                                <g:message code="default.add.label" args="[message(code:'contactAddress.label')]"/>
                                                </button>
                                            <button type="button"
                                                    class="btn"
                                                    data-ng-click="cancelAddContactAddress()">
                                                <i class="icon-ban-circle"></i>
                                                <g:message code="default.button.cancel.label"/></button>
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
                                <g:message code="default.add.label" args="[message(code:'contactAddress.label')]"/>
                            </button>
                        </div>
                    </fieldset>
                </tab>
                <tab heading="Contact Phone Numbers ({{contact.leadContactPhoneNumbers.length}})">
                    <fieldset class="embedded">
                        <legend></legend>

                        <div data-ng-repeat="contactPhoneNumber in contact.leadContactPhoneNumbers | filter:searchString">
                            <g:render template="/lead/leadContactPhoneNumber/partials/indexPartial"/>
                        </div>
                        <g:render template="/lead/leadContactPhoneNumber/partials/addPartial"/>
                    </fieldset>
                </tab>
                <tab heading="Contact Email Addresses ({{contact.leadContactEmailAddresses.length}})">
                    <fieldset class="embedded">
                        <div data-ng-repeat="contactEmailAddress in contact.leadContactEmailAddresses | filter:searchString">
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
                    <g:message code="default.button.update.label"/> <g:message code="contact.label" default="Contact"/>
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContact(contact, $index)">
                    <i class="icon-remove icon-white"></i>
                    <g:message code="default.button.delete.label"/> <g:message code="contact.label" default="Contact"/>
                </button>
                <button type="button"
                        class="btn"
                        data-ng-click="cancelEditContact()">
                    <i class="icon-ban-circle"></i>
                    <g:message code="default.button.cancel.label"/></button>
            </div>
        </div>
    </div>
</div>



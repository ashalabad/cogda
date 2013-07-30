<fieldset class="embedded">
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
        <br/>
        <br/>
        <div data-ng-hide="addingContact">
        <tabset data-ng-hide="addingContact">
            <tab data-ng-repeat="contact in lead.leadContacts  | orderBy:'primaryContact':'reverse' | filter:searchString"
                 heading="Contact - {{contact.firstName}} {{contact.lastName}}">
                <div data-ng-include="" src="'/leadContact/indexPartial'"></div>
                %{--<g:render template="/lead/leadContact/partials/indexPartial"/>--}%

            </tab>
        </tabset>
        </div>
    </div>
</fieldset>
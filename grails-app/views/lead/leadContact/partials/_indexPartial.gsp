<div data-ng-controller="EditContactController">
    <div class="form-horizontal">
        <div class="well" data-ng-hide="editingContact">
            <div data-ng-include="" src="'/leadContact/showPartial'"></div>
            %{--<g:render template="/lead/leadContact/partials/showPartial"/>--}%

        </div>

        <div data-ng-hide="editingContact">
            <tabset>
                <tab heading="Addresses ({{contact.leadContactAddresses.length }})">
                    <g:render template="/lead/leadContactAddress/partials/addEditShowPartial"/>
                </tab>
                <tab heading="Phone Numbers ({{contact.leadContactPhoneNumbers.length}})">
                    <fieldset class="embedded">
                        <div data-ng-include src="'/leadContactPhoneNumber/addPartial'"></div>
                        %{--<g:render template="/lead/leadContactPhoneNumber/partials/addPartial"/>--}%
                        <br/>

                        <div data-ng-repeat="contactPhoneNumber in contact.leadContactPhoneNumbers | filter:searchString">
                            <div data-ng-include src="'/leadContactPhoneNumber/indexPartial'"></div>
                            %{--<g:render template="/lead/leadContactPhoneNumber/partials/indexPartial"/>--}%
                        </div>
                    </fieldset>
                </tab>
                <tab heading="Email Addresses ({{contact.leadContactEmailAddresses.length}})">
                    <fieldset class="embedded">
                        <div data-ng-include src="'/leadContactEmailAddress/addPartial'"></div>
                        <br/>
                        %{--<g:render template="/lead/leadContactEmailAddress/partials/addPartial"/>--}%
                        <div data-ng-repeat="contactEmailAddress in contact.leadContactEmailAddresses | filter:searchString">
                            <div data-ng-include src="'/leadContactEmailAddress/indexPartial'"></div>
                            %{--<g:render template="/lead/leadContactEmailAddress/partials/indexPartial"/>--}%
                        </div>
                    </fieldset>
                </tab>
            </tabset>
        </div>

        <div class="well" data-ng-show="editingContact" data-ng-form="contactForm">
            <div data-ng-include="" src="'/leadContact/editPartial'"></div>
            %{--<g:render template="/lead/leadContact/partials/editPartial"/>--}%
            <div class="form-actions">
                <button type="submit"
                        class="btn btn-primary"
                        data-ng-click="updateContact(contact)">
                    <i class="icon-pencil icon-white"></i>
                    <g:message code="default.button.update.label"/> <g:message code="contact.label" default="Contact"/>
                </button>
                <button class="btn btn-danger"
                        type="button"
                        data-ng-click="deleteContact(contact)">
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



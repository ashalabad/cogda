<div data-ng-controller="AddContactPhoneNumberController">
    <div class="well" data-ng-show="addingContactPhoneNumber">
        <div data-ng-form="contactPhoneNumberForm" class="form-horizontal">
            <fieldset class="embedded">
                <legend>
                    <g:message code="default.add.label" args="[message(code: 'contactPhoneNumber.label')]"/>
                </legend>
                <g:render template="/lead/leadContactPhoneNumber/partials/editPartial"/>

                <div class="form-actions">
                    <button type="submit"
                            class="btn btn-primary"
                            data-ng-click="saveContactPhoneNumber(contactPhoneNumber)">
                        <i class="icon-plus icon-white"></i>
                        <g:message code="default.add.label" args="[message(code: 'contactPhoneNumber.label')]"/>
                    </button>
                    <button type="button"
                            class="btn"
                            data-ng-click="cancelAddContactPhoneNumber()">
                        <i class="icon-ban-circle"></i>
                        <g:message code="default.button.cancel.label"/>
                    </button>
                </div>
            </fieldset>
        </div>
    </div>

    <br>

    <button type="button"
            class="btn"
            data-ng-click="addContactPhoneNumber()"
            data-ng-hide="addingContactPhoneNumber">
        <i class="icon-plus"></i>
        <g:message code="default.add.label" args="[message(code: 'contactPhoneNumber.label')]"/>
    </button>
</div>
<div data-ng-controller="AddLeadNoteController">
    <div class="well" data-ng-show="addingLeadNote">
        <div data-ng-form="leadNoteForm" class="form-horizontal">
            <fieldset class="embedded">
                <legend>
                    <g:message code="default.add.label" args="[message(code: 'note.label')]"/>
                </legend>
                <div data-ng-include="" src="'/leadNote/editPartial'"></div>
                %{--<g:render template="/lead/leadNote/partials/editPartial"/>--}%

                <div class="form-actions">
                    <button type="submit"
                            class="btn btn-primary"
                            data-ng-click="saveLeadNote(leadNote)">
                        <i class="icon-plus icon-white"></i>
                        <g:message code="default.add.label" args="[message(code: 'note.label')]"/></button>
                    <button type="button"
                            class="btn"
                            data-ng-click="cancelAddLeadNote()">
                        <i class="icon-ban-circle"></i>
                        <g:message code="default.button.cancel.label"/></button>
                </div>
            </fieldset>
        </div>
    </div>
    <button type="button"
            class="btn"
            data-ng-click="addLeadNote()"
            data-ng-hide="addingLeadNote">
        <i class="icon-plus"></i>
        <g:message code="default.add.label" args="[message(code: 'note.label')]" />
    </button>
</div>
<div data-ng-controller="AddLeadNoteController">
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
    <button type="button"
            class="btn"
            data-ng-click="addLeadNote()"
            data-ng-hide="addingLeadNote">
        <i class="icon-plus"></i>
        Add Lead Note
    </button>
</div>
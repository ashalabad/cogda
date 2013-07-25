<fieldset class="embedded">
    <legend data-ng-hide="addingLeadNote"><g:message code="note.label" default="Note"/></legend>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadNoteForm['leadNote.noteType'].$invalid && leadNoteForm['leadNote.noteType'].$dirty, success: leadNoteForm['leadNote.noteType'].$valid && leadNoteForm['leadNote.noteType'].$dirty}">
        <label class="control-label">
            <g:message code="lead.subType.label" default="Type"/>
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <div data-ng-repeat="noteType in noteTypes">
                <label><input type="radio"
                              data-ng-model="leadNote.noteType"
                              data-ng-value="noteType"
                              name='leadNote.noteType'>
                    &nbsp;<span data-ng-bind="noteType.description"></span>
                </label>
            </div>
        </div>
    </div>

    <div class="control-group fieldcontain "
         data-ng-class="{error: leadNoteForm['leadNote.note.notes'].$invalid && leadNoteForm['leadNote.note.notes'].$dirty, success: leadNoteForm['leadNote.note.notes'].$valid && leadNoteForm['leadNote.note.notes'].$dirty}">
        <label for="leadNote.note.notes" class="control-label"><g:message code="note.notes.label"
                                                                          default="Notes"/></label>

        <div class="controls">
            <g:textArea name="leadNote.note.notes" cols="40" rows="5" maxlength="500"
                        data-ng-model="leadNote.note.notes"/>
        </div>
    </div>

</fieldset>
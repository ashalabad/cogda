<fieldset class="embedded"><legend><g:message code="note.label" default="Note"/></legend>

    <div class="control-group fieldcontain"
         data-ng-class="{error: noteForm['leadNote.noteType'].$invalid && noteForm['leadNote.noteType'].$dirty, success: noteForm['leadNote.noteType'].$valid && noteForm['leadNote.noteType'].$dirty}">
        <label class="control-label">
            <g:message code="lead.subType.label" default="Type"/>
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <div data-ng-repeat="noteType in noteTypes">
                <label class="radio inline" for='noteType[{{$index}}]'><span
                        class="radioSpan">{{noteType.description}}</span><input type="radio"
                                                                                data-ng-model="leadNote.noteType"
                                                                                data-ng-value="noteType"
                                                                                name='leadNote.noteType'
                                                                                id="noteType[{{$index}}]"/>
                </label>
            </div>
        </div>
    </div>

    <div class="control-group fieldcontain "
         data-ng-class="{error: noteForm['leadNote.note.notes'].$invalid && noteForm['leadNote.note.notes'].$dirty, success: noteForm['leadNote.note.notes'].$valid && noteForm['leadNote.note.notes'].$dirty}">
        <label for="leadNote.note.notes" class="control-label"><g:message code="note.notes.label"
                                                                          default="Notes"/></label>

        <div class="controls">
            <g:textArea name="leadNote.note.notes" cols="40" rows="5" maxlength="500"
                        data-ng-model="leadNote.note.notes"/>
            <span class="help-inline">${hasErrors(bean: leadNoteInstance?.note, field: 'description', 'error')}</span>
        </div>
    </div>

</fieldset>
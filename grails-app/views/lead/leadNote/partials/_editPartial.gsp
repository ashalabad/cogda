<fieldset class="embedded">
    <legend data-ng-hide="addingLeadNote"><g:message code="note.label" default="Note"/></legend>

    <div class="control-group fieldcontain" data-ng-class="{error: leadNoteForm['noteType'].$invalid}">
        <label class="control-label">
            ${message(code:'leadNote.label')}
        </label>
        <div class="controls">
            <g:select class="input-small" name="noteType" data-ng-model="leadNote.noteType.id" from="${com.cogda.domain.admin.NoteType.listOrderByCode()}" optionKey="id"  />
            <span class="help-inline" data-ng-show="errors.noteType ">{{ errors.noteType }}</span>
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
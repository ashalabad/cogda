<div class="tab-pane" id="addNotesContent">
    <g:hiddenField name="accountId" value="${accountInstance?.id}"/>
    <div class="control-group fieldcontain">
        <label for="noteType" class="control-label"><g:message code="accountNote.noteType.label"/></label>
        <div class="controls">
            <g:select id="noteType" name="noteType.id" from="${com.cogda.domain.admin.NoteType.list()}" optionKey="id" value="" class="many-to-one" noSelection="['null': '']"/>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label for="note.notes" class="control-label"><g:message code="accountNote.note.notes.label" /></label>
        <div class="controls">
            <g:textArea name="note.notes" cols="40" rows="5" maxlength="500" value=""/>
        </div>
    </div>

    <div class="add">
        <div class="btn btn-danger add-field" id="closeAddNoteTab"><i class="icon-remove-circle"></i> Cancel</div>
        <div class="btn btn-success add-field" onclick="saveAccountNote();"><i class="icon-plus"></i> Add Note</a>
    </div>

</div>
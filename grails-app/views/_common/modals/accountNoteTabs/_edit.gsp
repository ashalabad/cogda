<div class="tab-pane" id="editNotesContent">
    <g:hiddenField name="accountId" value="${accountNoteInstance?.id}"/>
    <div class="control-group fieldcontain ">
        <label for="noteType" class="control-label"><g:message code="accountNote.noteType.label" default="Note Type"/></label>
        <div class="controls">
            <g:select id="noteType" name="noteType.id" from="${com.cogda.domain.admin.NoteType.list()}" optionKey="id" value="${accountNoteInstance?.noteType?.id}" class="many-to-one" noSelection="['null': '']"/>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label for="note.notes" class="control-label"><g:message code="accountNote.note.notes.label" default="Notes"/></label>
        <div class="controls">
            <g:textArea name="note.notes" cols="80" rows="5" maxlength="500" value="${accountNoteInstance?.note?.notes}"/>
        </div>
    </div>

    <div class="edit">
        <div class="btn btn-danger edit-field" id="closeEditNoteTab"><i class="icon-remove-circle"></i> Cancel</div>
        <div class="btn btn-success edit-field" onclick="saveAccountNote();"><i class="icon-edit"></i> Update</div>
    </div>
</div>
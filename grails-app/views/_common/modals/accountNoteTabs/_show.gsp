<div class="tab-pane" id="showNotesContent">
    <table class="table">
        <tbody>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountNote.noteType.label" /></td>

            <td valign="top" class="value">${accountNoteInstance?.noteType?.encodeAsHTML()}</td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountNote.note.notes.label" /></td>

            <td valign="top" class="value">${accountNoteInstance?.note?.notes}</td>

        </tr>


        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountNote.dateCreated.label" /></td>

            <td valign="top" class="value"><g:formatDate date="${accountNoteInstance?.dateCreated}" /></td>

        </tr>

        <tr class="prop">
            <td valign="top" class="name"><g:message code="accountNote.lastUpdated.label" /></td>

            <td valign="top" class="value"><g:formatDate date="${accountNoteInstance?.lastUpdated}" /></td>

        </tr>

        </tbody>
    </table>

    <div class="edit">
        <div class="btn btn-danger show-field" id="closeShowNoteTab"><i class="icon-remove-circle"></i> Close</div>
    </div>
</div>
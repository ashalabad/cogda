<%@ page import="com.cogda.domain.admin.NoteType; com.cogda.common.LeadSubType; com.cogda.multitenant.LeadNote" %>

<fieldset class="embedded"><legend><g:message code="leadNote.note.label" default="Note"/></legend>
    <g:hiddenField name="leadNote.id" value="${leadNoteInstance?.id}"/>
    <g:hiddenField name="leadNote.version" value="${leadNoteInstance?.version}"/>
    <div class="control-group fieldcontain ${hasErrors(bean: leadNoteInstance, field: 'noteType', 'error')}">
        <label for="${prefix}noteType" class="control-label">
            <g:message code="leadNote.create.label" default="Create Note"/>:
        </label>

        <div class="controls">
            <g:radioGroup values="${NoteType.list().collect { it.code }}" name="${prefix}noteType"
                          labels="${NoteType.list().collect { it.description }}"
                          value="${leadNoteInstance?.note?.description}">
                <label class="radio inline">${it.label}${it.radio}</label>
            </g:radioGroup>

        </div>
    </div>
    <div class="control-group fieldcontain ${hasErrors(bean: leadNoteInstance, field: 'note.notes', 'error')} ">
        <label for="${prefix}notes" class="control-label"><g:message code="leadNote.note.notes.label"
                                                                 default="Notes"/></label>

        <div class="controls">
            <g:textArea name="${prefix}notes" cols="40" rows="5" maxlength="500" value="${noteInstance?.notes}"/>
            <span class="help-inline">${hasErrors(bean: leadNoteInstance, field: 'notes', 'error')}</span>
        </div>
    </div>

</fieldset>
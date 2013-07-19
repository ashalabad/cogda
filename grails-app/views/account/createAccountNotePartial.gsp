<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form class="form-horizontal" name="accountNoteAddForm">
            <fieldset>
                <div class="control-group fieldcontain"
                     data-ng-class="{error: accountNoteAddForm['noteType'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountNote.noteType.label')}
                    </label>
                    <div class="controls">
                        <g:select class="input-small" name="noteType.id" data-ng-model="accountNote.noteType.id" required="required" from="${com.cogda.domain.admin.NoteType.listOrderByCode()}" optionKey="id"  noSelection="['': 'Type']" />
                        <span class="help-inline" data-ng-show="errors.noteType ">{{ errors.noteType }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountNoteAddForm['note.notes'].$invalid}">
                    <textArea data-ng-model="accountNote.note.notes" rows="10" style="width:520px;" required="required" name="notes"></textArea>
                    <span class="help-inline error" data-ng-show="errors.note.notes "> {{ errors.note.notes }}</span>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
        <button type="button" class="btn btn-primary" data-ng-click="saveAccountNote(accountNote)" ><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
    </div>
</div>
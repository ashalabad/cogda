<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form class="form-horizontal" name="accountNoteEditForm">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountNoteEditForm['noteType'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountNote.noteType.label')}
                    </label>
                    <div class="controls">
                        <g:select class="input-small" name="noteType" data-ng-model="account.accountNotes[noteIndex].noteType.id" from="${com.cogda.domain.admin.NoteType.listOrderByCode()}" optionKey="id"  />
                        <span class="help-inline" data-ng-show="errors.noteType ">{{ errors.noteType }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountNoteEditForm['notes'].$invalid}">
                    <textArea data-ng-model="account.accountNotes[noteIndex].note.notes" rows="10" style="width:520px;" required="required" name="notes"></textArea>
                    <span class="help-inline error" data-ng-show="errors.note.notes "> {{ errors.notes }}</span>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="updateAccount()" ><i class="icon-save"></i> ${message(code:'default.button.update.label')}</button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> ${message(code:'default.button.cancel.label')}</button>
    </div>
</div>
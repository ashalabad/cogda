<fieldset class="embedded"><legend><g:message code="note.label" default="Note"/></legend>

    <div class="control-group fieldcontain"
         data-ng-class="{error: leadForm['leadNotes[0].noteType'].$invalid && leadForm['leadNotes[0].noteType'].$dirty, success: leadForm['leadNotes[0].noteType'].$valid && leadForm['leadNotes[0].noteType'].$dirty}">
        <label class="control-label">
            <g:message code="lead.subType.label" default="Type"/>
            <span class="required-indicator">*</span>
        </label>

        <div class="controls">
            <div data-ng-repeat="noteType in noteTypes">
                <label class="radio inline" for='noteType[{{$index}}]'><span
                        class="radioSpan">{{noteType.description}}</span><input type="radio"
                                                                                data-ng-model="lead.leadNotes[0].noteType"
                                                                                data-ng-value="noteType"
                                                                                name='leadNotes[0].noteType'
                                                                                id="noteType[{{$index}}]"/>
                </label>
            </div>
        </div>
    </div>

    <div class="control-group fieldcontain "
         data-ng-class="{error: leadForm['leadNotes[0].note.notes'].$invalid && leadForm['leadNotes[0].note.notes'].$dirty, success: leadForm['leadNotes[0].note.notes'].$valid && leadForm['leadNotes[0].note.notes'].$dirty}">
        <label for="leadNotes[0].note.notes" class="control-label"><g:message code="note.notes.label"
                                                                              default="Notes"/></label>

        <div class="controls">
            <g:textArea name="leadNotes[0].note.notes" cols="40" rows="5" maxlength="500"
                        data-ng-model="lead.leadNotes[0].note.notes"/>
        </div>
    </div>

</fieldset>
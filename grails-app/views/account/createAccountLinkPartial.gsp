<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountLinkAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountLinkAddForm['account'].$invalid}">
                    <label class="control-label">
                        ${message(code:'accountContact.label')}
                    </label>
                    <div class="controls">
                        <select data-ng-model="accountContact" data-ng-options="a.accountContact for a in accountContacts">
                            <option value="">--Select--</option>
                        </select>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="saveAccountLink()"><i class="icon-link"></i> <g:message code="accountContactLink.link.label" args="[message(code:'accountContact.label')]" /></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
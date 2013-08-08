<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountContactLinkAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountContactLinkAddForm['account'].$invalid}">
                    <label class="control-label">
                        ${message(code:'account.label')}
                    </label>
                    <div class="controls">
                        <select data-ng-model="account" data-ng-options="a.accountName for a in accounts">
                            <option value="">--Select--</option>
                        </select>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-ng-click="saveAccountContactLink()"><i class="icon-link"></i> <g:message code="accountContactLink.link.label" args="[message(code:'account.label')]" /></button>
        <button type="button" class="btn" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
    </div>
</div>
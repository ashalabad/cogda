<%@ page import="com.cogda.domain.admin.AccountType" %>
<div class="modal">
    <div class="modal-header">
        <h3>{{title}}</h3>
    </div>
    <div class="modal-body" >
        <form name="accountEditForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain">
                    <label class="control-label" for="accountType">
                        <g:message code="account.accountType.label" />
                    </label>
                    <div class="controls">
                        <g:select id="accountType" name="accountType" data-ng-model="account.accountType.id" from="${AccountType.listOrderByCode()}" optionKey="id" optionValue="code" />
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountEditForm['accountName'].$invalid}">
                    <label class="control-label" for="accountName">
                        <g:message code="account.accountName.label" />
                    </label>
                    <div class="controls">
                        <input type="text" id="accountName" name="accountName" data-ng-model="account.accountName" class="input-xlarge">
                        <span class="help-inline" data-ng-show="errors.accountName ">{{ errors.accountName }}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain" data-ng-class="{error: accountEditForm['accountCode'].$invalid}">
                    <label class="control-label" for="accountCode">
                        <g:message code="account.accountCode.label" />
                    </label>
                    <div class="controls">
                        <input type="text" id="accountCode" name="accountCode" data-ng-model="account.accountCode" class="input-xlarge">
                        <span class="help-inline" data-ng-show="errors.accountCode ">{{ errors.accountCode }}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        <g:message code="account.isMarket.label" />
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{active:account.isMarket==true}" data-ng-click="account.isMarket=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{active:account.isMarket==false}" data-ng-click="account.isMarket=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        <g:message code="account.favorite.label" />
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{active:account.favorite==true}" data-ng-click="account.favorite=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{active:account.favorite==false}" data-ng-click="account.favorite=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>

            </fieldset>
            <div class="form-actions">
                <button type="button" class="btn btn-danger" data-ng-click="cancel()"><i class="icon-remove"></i> <g:message code="default.button.cancel.label"/></button>
                <button type="button" class="btn btn-primary" data-ng-click="updateAccount(account)"><i class="icon-save"></i> <g:message code="default.button.save.label"/></button>
            </div>
        </form>
    </div>
</div>
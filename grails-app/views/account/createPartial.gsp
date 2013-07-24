<%@ page import="com.cogda.domain.admin.AccountType" %>
<div class="row">
    <div class="well span8 offset2">

        <h3>{{title}}</h3>

        <form name="accountAddForm" class="form-horizontal">
            <fieldset>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddForm['accountType'].$invalid}">
                    <label class="control-label" for="accountType">
                        <g:message code="account.accountType.label" />
                    </label>
                    <div class="controls">
                        <g:select id="accountType" name="accountType" data-ng-model="account.accountType.id" from="${AccountType.listOrderByCode()}" optionKey="id" optionValue="code" noSelection="['': 'Type']"/>
                        <span class="help-inline" data-ng-show="errors.accountType ">{{ errors.accountType }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddForm['accountName'].$invalid}">
                    <label class="control-label" for="accountName">
                        <g:message code="account.accountName.label" />
                    </label>
                    <div class="controls">
                        <input type="text" id="accountName" name="accountName" data-ng-model="account.accountName" class="input-xlarge">
                        <span class="help-inline" data-ng-show="errors.accountName ">{{ errors.accountName }}</span>
                    </div>
                </div>
                <div class="control-group fieldcontain" data-ng-class="{error: accountAddForm['accountCode'].$invalid}">
                    <label class="control-label" for="accountCode">
                        <g:message code="account.accountCode.label" />
                    </label>
                    <div class="controls">
                        <input type="text" id="accountCode" name="accountCode" data-ng-model="account.accountCode" class="input-xlarge">
                        <span class="help-inline" data-ng-show="errors.accountAddressType ">{{ errors.accountCode }}</span>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        <g:message code="account.isMarket.label" />
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.isMarket==true}" data-ng-click="account.favorite=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.isMarket==false}" data-ng-click="account.favorite=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>

                <div class="control-group fieldcontain">
                    <label class="control-label">
                        <g:message code="account.favorite.label" />
                    </label>
                    <div class="controls">
                        <div class="btn-group" data-toggle="buttons-radio">
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.favorite==true}" data-ng-click="account.favorite=true"><g:message code="default.yes.label"/></button>
                            <button type="button" class="btn" data-ng-class="{'btn-primary':account.favorite==false}" data-ng-click="account.favorite=false"><g:message code="default.no.label"/></button>
                        </div>
                    </div>
                </div>
            </fieldset>
            <div class="span6">
                <button type="button" class="btn btn-primary pull-right" data-ng-click="saveAccount()"><i class="icon-save"></i> <g:message code="default.button.create.label"/></button>
            </div>
        </form>
    </div>
</div>
<div class="pageHeader">
    <h3>{{pageHeader}}<button type="button" class="btn pull-right" data-ng-click="createAccount()"><i class="icon-plus"></i> <g:message code="default.create.label" args="[message(code: 'account.label')]"></g:message></button></h3>
</div>

<div class="gridStyle ng-cloak" data-ng-grid="accountGridOptions"></div>

%{--<pre ng-bind="toJSON(accountData)"></pre>--}%

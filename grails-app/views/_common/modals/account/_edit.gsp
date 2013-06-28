<%@ page import="com.cogda.multitenant.Account;com.cogda.domain.admin.AccountType" %>
<form class="form-inline" id="accountForm_${accountInstance?.id}">
    <fieldset id="accountFieldset">
        <div id="accountData">
            <div class="field">
                <label>Account Type</label>
                <span id="accountTypeLbl" class="editShow accountType accountShow">${accountInstance?.accountType.code}</span>
                <g:select class="editHide accountEdit" id="accountType" name="accountType.id" from="${AccountType.listOrderByCode()}" optionKey="id" optionValue="code" value="${accountInstance?.accountType?.id}" />
            </div>
            <div class="field">
                <label>Account Name</label>
                <span id="accountNameLbl" class="editShow accountName accountShow">${accountInstance?.accountName}</span>
                <input type="text" id="accountName" class="editHide accountName accountEdit input-xlarge" value="${accountInstance?.accountName}">
            </div>
            <div class="field">
                <label>Account Code</label>
                <span id="accountCodeLbl" class="editShow accountCode accountShow">${accountInstance?.accountCode}</span>
                <input type="text" id="accountCode" class="editHide accountCode accountEdit input-xlarge" value="${accountInstance?.accountCode}">
            </div>
        </div>
        <div class="add">
            <a class="btn add-field editShow accountShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
            <a class="btn btn-primary add-field editHide accountEdit" href="#" onclick="saveAccount();"><i class="icon-save"></i> Save</a>
        </div>
    </fieldset>
</form>
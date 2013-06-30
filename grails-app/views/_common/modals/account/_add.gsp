<r:require module="account"/>

<div class="modal hide fade" id="accountAddModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="accountHeader">
            <h3>Add Account</h3>
        </div>

    </div>
    <div class="modal-body">
        <form class="form-inline" id="accountForm">
            <fieldset>
                <div id="accountData">
                    <div class="field">
                        <g:select class="accountType accountEdit" id="accountType" name="accountType.id" from="${com.cogda.domain.admin.AccountType.list()}" optionKey="id" optionValue="code" required="" value="" noSelection="['': 'Account Type']"/>
                    </div>
                    <div class="field">
                        <input type="text" id="accountName" class="accountName accountEdit" placeholder="Account Name">
                    </div>
                    <div class="field">
                        <input type="text" id="accountCode" class="accountCode accountEdit" placeholder="Account Code">
                    </div>
                </div>
            </fieldset>
            <fieldset id="mailFieldset">
                <legend>Account Mailing Address</legend>
                <div class="field address">
                    <input type="text" class="addressOne input-xlarge" placeholder="Address Line 1">
                    <input type="text" class="addressTwo input-xlarge" placeholder="Address Line 2">
                    <input type="text" class="addressThree input-xlarge" placeholder="Address Line 3">
                    <input type="text" class="city input-xlarge" placeholder="City">
                    <div class="stateContainer">
                        <g:select class="tate input-small" name="state" from="${com.cogda.common.UsState.values()}" optionKey="key" noSelection="['': 'State']"/>
                    </div>
                    <input type="text" class="zip input-small" placeholder="Zipcode">
                </div>
            </fieldset>
            <fieldset id="accountContactFieldset">
                <legend>Primary Contact</legend>
                <div class="field">
                    <input type="text" id="firstName" class="firstName accountEdit" placeholder="First Name">
                </div>
                <div class="field">
                    <input type="text" id="middleName" class="middleName accountEdit" placeholder="Middle Name">
                </div>
                <div class="field">
                    <input type="text" id="lastName" class="lastName accountEdit" placeholder="Last Name">
                </div>
            </fieldset>
            <fieldset id="EmailFieldset">
                <legend>Contact's Email Address</legend>
                <div class="field">
                    <input type="text" id="email_0" placeholder="Email Address">
                </div>
                <div class="add" id="addEmailBtn">
                    <a class="btn btn-mini add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add</a>
                </div>
            </fieldset>
            <fieldset id="PhoneFieldset">
                <legend>Contact's Phone Number</legend>
                <div class="field">
                    <input type="text" id="phone_0" placeholder="Phone Number">
                </div>
                <div class="add" id="addPhone">
                    <a class="btn btn-mini add-field" href="#" onclick="addPhoneField();"><i class="icon-plus"></i> Add</a>
                </div>
            </fieldset>
            <fieldset id="contactMailFieldset">
                <legend>Contact's Mailing Address</legend>
                <div class="field address">
                    <input type="text" class="addressOne input-xlarge" placeholder="Address Line 1">
                    <input type="text" class="addressTwo input-xlarge" placeholder="Address Line 2">
                    <input type="text" class="addressThree input-xlarge" placeholder="Address Line 3">
                    <input type="text" class="city input-xlarge" placeholder="City">
                    <div class="stateContainer">
                        <g:select class="tate input-small" name="state" from="${com.cogda.common.UsState.values()}" optionKey="key" noSelection="['': 'State']"/>
                    </div>
                    <input type="text" class="zip input-small" placeholder="Zipcode">
                </div>
                <div class="add" id="addMail">
                    <a class="btn btn-mini add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add</a>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <a class="btn btn-primary" href="#" data-dismiss="modal" onclick="saveAccount();"><i class="icon-save"></i> Save</a>
    </div>
</div>
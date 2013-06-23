<div class="modal hide fade" id="accountModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="accountHeader">
            <h3>Account Details</h3>
        </div>

    </div>
    <div class="modal-body">
        <form class="form-inline" id="accountForm">
            <fieldset>
                <div id="accountData">
                    <div class="field">
                        <label>Account Type</label>
                        <span id="accountTypeLbl" class="editShow accountType accountShow"></span>
                        <g:select class="editHide accountType accountEdit" id="accountType" name="accountType.id" from="${AccountType.list()}" optionKey="id" optionValue="code" required="" value="" noSelection="['': '']"/>
                    </div>
                    <div class="field">
                        <label>Account Name</label>
                        <span id="accountNameLbl" class="editShow accountName accountShow"></span>
                        <input type="text" id="accountName" class="editHide accountName accountEdit">
                    </div>
                    <div class="field">
                        <label>Account Code</label>
                        <span id="accountCodeLbl" class="editShow accountCode accountShow"></span>
                        <input type="text" id="accountCode" class="editHide accountCode accountEdit">
                    </div>
                    <fieldset id="AddressFieldset">
                        <legend>Mailing Addresses</legend>
                        <div class="field address template edit">
                            <label class="addressLbl">Mailing Address</label>
                            <input type="text" class="addressOne input-xlarge" placeholder="address line 1">
                            <input type="text" class="addressTwo input-xlarge" placeholder="address line 2">
                            <input type="text" class="addressThree input-xlarge" placeholder="address line 3">
                            <input type="text" class="city input-xlarge" placeholder="city">
                            <div class="stateContainer">
                                <select class="state input-small">
                                    <option value="AL">AL</option>
                                    <option value="AK">AK</option>
                                    <option value="GA">GA</option>
                                </select>
                            </div>
                            <input type="text" class="zip input-small" placeholder="zipcode">
                            <div class="save">
                                <a href="#" class="btn btn-mini cancel" onclick="cancelNewAddress($(this));">Cancel</a>
                                <a class="btn btn-mini btn-primary" href="#" onclick="saveAddress();"><i class="icon-save"></i> Save</a>
                            </div>
                        </div>
                        <div class="field address data show">
                            <label class="viewAddressLbl">Mailing Address</label> <a class="btn btn-mini editAddress template" href="#" onclick="editAddress();"><i class="icon-edit"></i> Edit</a>
                            <div class="addressOne">123 Childs st</div>
                            <div class="addressTwo">Ste 213</div>
                            <div class="addressThree"></div>
                            <span class="city">Athens</span>, <span class="state">GA</span> <span class="zipcode">30601</span>
                        </div>
                        <div class="add" id="addMail">
                            <a class="btn btn-mini add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add</a>
                        </div>
                    </fieldset>
                    <div class="field">
                        <label>First Name</label>
                        <span id="firstNameLbl" class="editShow firstName accountShow"></span>
                        <input type="text" id="firstName" class="editHide firstName accountEdit">
                    </div>
                    <div class="field">
                        <label>Middle Name</label>
                        <span id="middleNameLbl" class="editShow middleName accountShow"></span>
                        <input type="text" id="middleName" class="editHide middleName accountEdit">
                    </div>
                    <div class="field">
                        <label>Last Name</label>
                        <span id="lastNameLbl" class="editShow lastName accountShow"></span>
                        <input type="text" id="lastName" class="editHide lastName accountEdit">
                    </div>
                    <fieldset id="contactAddressEmailFieldset">
                        <legend>Email Addresses</legend>
                        <div id="emailPrimary">Primary</div>
                        <a class="btn btn-mini btn-primary saveEmail template" href="#" onclick="saveEmail();"><i class="icon-save"></i> Save</a>
                        <div class="add" id="addEmail">
                            <a class="btn btn-mini add-field" href="#" onclick="addEmailAddressField();"><i class="icon-plus"></i> Add</a>
                        </div>
                    </fieldset>
                    <fieldset id="contactAddressPhoneFieldset">
                        <legend>Phone Numbers</legend>
                        <a class="btn btn-mini btn-primary savePhone template" href="#" onclick="savePhone();"><i class="icon-save"></i> Save</a>
                        <div class="field">
                            <label>Phone</label>
                            <input type="text" id="phone_0">
                        </div>
                        <div class="add" id="addPhone">
                            <a class="btn btn-mini add-field" href="#" onclick="addPhoneField();"><i class="icon-plus"></i> Add</a>
                        </div>
                    </fieldset>
                    <fieldset id="contactAddressFieldset">
                        <legend>Mailing Addresses</legend>
                        <div class="field address template edit">
                            <label class="addressLbl">Mailing Address</label>
                            <input type="text" class="addressOne input-xlarge" placeholder="address line 1">
                            <input type="text" class="addressTwo input-xlarge" placeholder="address line 2">
                            <input type="text" class="addressThree input-xlarge" placeholder="address line 3">
                            <input type="text" class="city input-xlarge" placeholder="city">
                            <div class="stateContainer">
                                <select class="state input-small">
                                    <option value="AL">AL</option>
                                    <option value="AK">AK</option>
                                    <option value="GA">GA</option>
                                </select>
                            </div>
                            <input type="text" class="zip input-small" placeholder="zipcode">
                            <div class="save">
                                <a href="#" class="btn btn-mini cancel" onclick="cancelNewAddress($(this));">Cancel</a>
                                <a class="btn btn-mini btn-primary" href="#" onclick="saveAddress();"><i class="icon-save"></i> Save</a>
                            </div>
                        </div>
                        <div class="field address data show">
                            <label class="viewAddressLbl">Mailing Address</label> <a class="btn btn-mini editAddress template" href="#" onclick="editAddress();"><i class="icon-edit"></i> Edit</a>
                            <div class="addressOne">123 Childs st</div>
                            <div class="addressTwo">Ste 213</div>
                            <div class="addressThree"></div>
                            <span class="city">Athens</span>, <span class="state">GA</span> <span class="zipcode">30601</span>
                        </div>
                        <div class="add" id="addMail">
                            <a class="btn btn-mini add-field" href="#" onclick="addMailingAddressField();"><i class="icon-plus"></i> Add</a>
                        </div>
                    </fieldset>

                </div>
                <div class="add">
                    <a class="btn btn-mini add-field editShow accountShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
                    <a class="btn btn-mini btn-primary add-field editHide accountEdit" href="#" onclick="saveAccount();"><i class="icon-save"></i> Save</a>
                </div>
            </fieldset>

        </form>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn btn-primary" data-dismiss="modal">Done</a>
    </div>
</div>
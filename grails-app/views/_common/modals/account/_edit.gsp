<r:require module="account"/>

<div class="modal hide fade" id="accountModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <div id="accountHeader">
            <h3></h3>
        </div>
    </div>

    <div class="modal-body">

        <ul class="nav nav-tabs" id="myTab">
            <li><a href="#summary" data-toggle="tab">Summary</a></li>
            <li><a href="#submissions" data-toggle="tab">Submissions</a></li>
            <li><a href="#contacts" data-toggle="tab">Contacts</a></li>
            <li><a href="#notes" data-toggle="tab">Notes</a></li>
            <li><a href="#editAccount" data-toggle="tab">Account Details</a></li>

        </ul>

        <div class="tab-content">
            <div class="tab-pane" id="summary">
                summary
            </div>
            <div class="tab-pane" id="submissions">
                Submission data table here
            </div>
            <div class="tab-pane" id="contacts">
                <section id="list-accountContacts" class="first">
                    <table class="table table-bordered" id="accountContactList">
                        <thead>
                        <tr>

                            <th>${message(code: 'account.accountContact.name.label')}</th>

                            <th>${message(code: 'account.accountContact.email.label')}</th>

                            <th>${message(code: 'account.accountContact.phone.label')}</th>


                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </section>
            </div>
            <div class="tab-pane" id="notes">
                notes data table here
            </div>
            <div class="tab-pane" id="editAccount">
                <form class="form-inline" id="accountForm">
                    <fieldset>
                        <div id="accountData">
                            <div class="field">
                                <label>Account Type</label>
                                <span id="accountTypeLbl" class="editShow accountType accountShow"></span>
                                <g:select class="editHide accountType accountEdit" id="accountType" name="accountType.id" from="${com.cogda.domain.admin.AccountType.list()}" optionKey="id" optionValue="code"/>
                            </div>
                            <div class="field">
                                <label>Account Name</label>
                                <span id="accountNameLbl" class="editShow accountName accountShow"></span>
                                <input type="text" id="accountName" class="editHide accountName accountEdit input-xlarge">
                            </div>
                            <div class="field">
                                <label>Account Code</label>
                                <span id="accountCodeLbl" class="editShow accountCode accountShow"></span>
                                <input type="text" id="accountCode" class="editHide accountCode accountEdit input-xlarge">
                            </div>
                            <fieldset id="mailFieldset">
                                <legend>Mailing Addresses - TODO:</legend>
                            </fieldset>
                        </div>
                        <div class="add">
                            <a class="btn btn-mini add-field editShow accountShow" href="#" onclick="toggleEdit();"><i class="icon-edit"></i> Edit</a>
                            <a class="btn btn-mini btn-primary add-field editHide accountEdit" href="#" onclick="saveAccount();"><i class="icon-save"></i> Save</a>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>

    </div>
</div>
<ul class="nav nav-tabs" id="accountContactTabs">
    <li class="active"><a href="#listAccountContacts" data-toggle="tab">Contact List</a></li>
</ul>

<div class="tab-content" id="accountContactTabsContent">
    <div class="tab-pane active" id="listAccountContacts">
        <ul class="nav">
            <li class="btn" id="createContactButton"><i class="icon-plus"></i> Create New Contact</li>
            <li class="btn" id="addContactButton"><i class="icon-plus"></i> Add Existing Contact</li>
        </ul>

        <section id="list-accountContacts" class="first">
            <table class="table table-bordered" id="accountContactList">
                <thead>
                <tr>

                    <th>${message(code: 'accountContact.contact.label')}</th>

                    <th>${message(code: 'accountContact.accountContactEmailAddresses.primary.label')}</th>

                    <th>${message(code: 'accountContact.accountContactPhoneNumbers.primary.label')}</th>

                    <th><div class="span1"></div></th>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </section>
    </div>
</div>
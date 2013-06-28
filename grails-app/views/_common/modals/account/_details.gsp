
<ul class="nav nav-tabs" id="myTab">
    <li><a href="#summary" data-toggle="tab">Summary</a></li>
    <li><a href="#submissions" data-toggle="tab">Submissions</a></li>
    <li><a href="#contacts" data-toggle="tab">Contacts</a></li>
    <li><a href="#notes" data-toggle="tab">Notes</a></li>
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
                <g:each in="${accountInstance.accountContacts}" var="accountContact">
                    <td>${accountContact.fullName}</td>
                    <td>${accountContact.primaryAccountEmailAddress}</td>
                    <td>${accountContact.primaryAccountPhoneNumber}</td>
                </g:each>
                </tbody>
            </table>
        </section>
    </div>
    <div class="tab-pane" id="notes">
        notes data table here
    </div>
</div>

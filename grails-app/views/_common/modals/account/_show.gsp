<ul class="nav nav-tabs" id="myTabs">
    <li><a href="#submissions" data-toggle="tab">Submissions</a></li>
    <li><a href="#contacts" data-toggle="tab">Contacts</a></li>
    <li><a href="#notes" data-toggle="tab">Notes</a></li>
</ul>

<div class="tab-content" id="myTabsContent">
    <div class="tab-pane" id="submissions">
        <g:render template="/_common/modals/accountSubmissionsTabs/list"/>
    </div>
    <div class="tab-pane" id="contacts">
        <g:render template="/_common/modals/accountContactTabs/list"/>
    </div>
    <div class="tab-pane" id="notes">
        <g:render template="/_common/modals/accountNoteTabs/list"/>
    </div>
</div>

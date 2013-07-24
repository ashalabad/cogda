<div ng-cloak>

    <div class="row">
        <div class="span5 accountDisplay lead">
            <h3 class="center">
                <span data-ng-bind='account.accountName'></span>
            </h3>
            <hr class="darkHR">
            <h4 class="center">
                <span class="gridIcon" data-ng-show="account.favorite==true"><span class="label label-warning"><i class="icon-star"></i></span></span>
                <span class="gridIcon" data-ng-show="account.isMarket==true"><span class="label label-info"><i class="icon-columns"></i></span></span>
                <span data-ng-bind="account.accountType.code"></span>
                <span data-ng-bind="account.accountCode"></span>
            </h4>
        </div>
        <div class="span3 well well-small minWell" data-ng-show="formattedPrimaryAddress">
            <a target="_blank" data-ng-href="http://maps.google.com?q={{mapPrimaryAddress}}" class="btn btn-mini btn-info pull-right"><i class="icon-map-marker"></i> <g:message code="default.button.map.label" /></a>
            <div data-ng-bind-html-unsafe="formattedPrimaryAddress"></div>
            <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountAddress(primaryAccountAddress)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
        </div>
        <div class="span3" data-ng-hide="formattedPrimaryAddress">
            <button type="button" class="btn btn-primary" data-ng-click="addAccountAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountAddress.primaryAddress.label')]" /></button>
        </div>
        <div class="span3 well well-small minWell" data-ng-show="formattedPrimaryContact">
            <h4 class="text-center"><g:message code="account.primaryContact.label"/></h4>
            <div data-ng-bind-html-unsafe="formattedPrimaryContact" class="text-center"></div><br>
            <button type="button" class="btn btn-mini pull-right" data-ng-click="showAccountContact(primaryAccountContact)" ><i class="icon-eye-open"></i> <g:message code="default.button.show.label" /></button>
        </div>
        <div class="span3" data-ng-hide="formattedPrimaryContact">
            <button type="button" class="btn btn-primary" data-ng-click="addAccountContact(true)"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.primaryContact.label')]" /></button>
        </div>
    </div>
    <div class="row">
        <div class="span8">
            <button type="button" class="btn btn-mini" data-ng-click="editAccount()"><i class="icon-edit"></i> <g:message code="default.edit.label" args="[message(code:'account.label')]"  /></button>
            <button type="button" class="btn btn-mini" data-ng-click="createSubmission()"><i class="icon-plus"></i> <g:message code="default.create.label" args="[message(code:'submission.label')]" /></button>
            <button type="button" class="btn btn-mini" data-ng-click="attachDocument()"><i class="icon-upload"></i> <g:message code="default.attach.label" args="[message(code:'document.label')]" /></button>
            <div class="btn-group">
                <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountContact.contact.label')]" />
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li><a data-ng-click="addAccountContact(false)"><i class="icon-plus"></i> <g:message code="default.new.label" args="[message(code:'accountContact.contact.label')]" /></a></li>
                    <li><a data-ng-click="addAccountLink()"><i class="icon-link"></i> <g:message code="accountContactLink.link.label" args="[message(code:'accountContact.contact.label')]" /></a></li>
                </ul>
            </div>
            <button type="button" class="btn btn-mini" data-ng-click="addAccountNote()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountNote.note.notes.label')]" /></button>
            <button type="button" class="btn btn-mini" data-ng-click="addAccountAddress()"><i class="icon-plus"></i> <g:message code="default.add.label" args="[message(code:'accountAddress.address.label')]" /></button>
        </div>
        <div class="span4">
            <form class="form-search">
                <div class="input-append pull-left">
                    <input type="text" class="span3 search-query" data-ng-model="searchString.$" placeholder="Search Account">
                    <button type="button" data-ng-click="clearSearch()" class="btn"><g:message code="default.button.clear.label" /></button>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="span12">
            <tabset>
                <tab heading="Dashboard">
                    <div class="well">
                        Dashboard TBC
                    </div>
                </tab>

                <tab heading="Submissions">
                    <div class="well">
                        Submissions TBC
                    </div>
                </tab>

                <tab heading="Documents">
                    <div class="well">
                        Documents TBC
                    </div>
                </tab>

                <tab heading="Contacts">
                    <ul class="inline">
                        <li data-ng-repeat="contact in accountContacts | orderBy:'lastName' | filter:searchString " class="span5 well well-small fixedContactWellHeight">
                            <div class="btn-group pull-right">
                                <a class="btn btn-mini dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="icon-cog"></i> Manage Contact
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a data-ng-click="showAccountContact(contact)" ><i class="icon-eye-open"></i> <g:message code="default.button.show.label" /></a></li>
                                    <li><a data-ng-click="unlinkAccountContact(contact)" ><i class="icon-link"></i> <g:message code="accountContactLink.unlink.simple.label" /></a></li>
                                    <li data-ng-hide="contact.id == primaryAccountContact.id"><a data-ng-click="designatePrimaryAccountContact(contact)" ><i class="icon-asterisk"></i> Make Primary</a></li>
                                </ul>
                            </div>
                            <div data-ng-bind-html-unsafe="formatContact(contact)"></div>
                        </li>
                    </ul>
                </tab>



                <tab heading="Notes">
                    <ul class="inline">
                        <li data-ng-repeat="note in account.accountNotes | orderBy:'lastUpdated' | filter:searchString" class="span11 well well-small">
                            <address>
                                <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountNote(note)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                                <strong>{{note.lastUpdated | date:'MM/dd/yyyy @ h:mm a'}}<br> {{note.noteType.code}} </strong><br>
                                <p class="text-info"><em>{{note.note.notes}}</em></p>
                            </address>
                        </li>
                    </ul>
                </tab>
                <tab heading="Addresses ">
                    <ul class="inline">
                        <li data-ng-repeat="address in account.accountAddresses | orderBy:'primaryAddress':'reverse' | filter:searchString" class="span5 well fixedAddressWellHeight">
                            <a target="_blank" data-ng-href="http://maps.google.com?q={{}}" class="btn btn-mini btn-info pull-right"><i class="icon-map-marker"></i> <g:message code="default.button.map.label" /></a>
                            <button type="button" class="btn btn-mini pull-right" data-ng-click="editAccountAddress(address)" ><i class="icon-edit"></i> <g:message code="default.button.edit.label" /></button>
                            <div data-ng-bind-html-unsafe="formatAddress(address)"></div>
                        </li>
                    </ul>
                </tab>
            </tabset>
        </div>
    </div>
</div>
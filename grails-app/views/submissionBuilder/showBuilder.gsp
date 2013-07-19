<div id = "leadDetail">
    <h3>
        <a href="#">
            {{ lead.clientName }}
        </a>
    </h3>
</div>
<div class = "row">
    <div class="span4">

        <h4>Step 1: Select your LOBs</h4>
        <div class="well">
            <button type="button" class="btn btn-info">
                <i class="icon-plus icon-white"></i>
                Add new LOB/XDate
            </button>

            <div class="well-small">
                <ul class="unstyled" data-ng-repeat="leadLineOfBusiness in lead.leadLineOfBusinesses">
                    <li>
                        <input type="checkbox" name="selectedLeadLineOfBusiness"/>
                        {{ leadLineOfBusiness.lineOfBusiness.description }}
                        {{ leadLineOfBusiness.expirationDate }}
                    </li>
                </ul>
            </div>
        </div>

        <h4>Step 2: File Attachments</h4>
        <div class="well">
            <button type="button" class="btn btn-info">
                <i class="icon-plus icon-white"></i>
                Upload Files
            </button>

            <div class="well-small">
                <div class="input-append">
                    <input class="span2" id="appendedInputButtons" type="text" placeholder="Filter files...">
                    <button class="btn " type="button">
                        <i class="icon-search"></i>
                    </button>
                </div>
                <ul class="unstyled" data-ng-repeat="file in lead.files">
                    <li>

                        <input type="checkbox" name="selectedFile"/>

                            {{ file.fileName }}

                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="span8">
        <h4>Step 3: Select Your Markets</h4>
        <div class="well">
            <form class="form-search">
                <input type="text" class="input-large search-query" placeholder="Filter results..." data-ng-model="marketCriteria">

            </form>

            <div class="fixedHeightContainer">
                <div class="marketContactsContainer">

                    <accordion close-others="oneAtATime">
                            <accordion-group heading="{{accountContactLink.companyName}}" data-ng-repeat="accountContactLink in accountContactLinksToFilter() | filter:filterAccounts | orderBy:'companyName'">
                                <ul class="unstyled" data-ng-repeat="accountContactLinkInner in accountContactLinks | filter:{companyName: accountContactLink.companyName} | orderBy:'contactLastName'">
                                    <li style="vertical-align: middle">
                                        <label class="checkbox">
                                            <input type="checkbox" name="selectedcontact" style="vertical-align: middle"/>
                                            <strong>
                                                {{accountContactLinkInner.contactLastName}}, {{accountContactLinkInner.contactFirstName}}
                                            </strong>
                                            {{accountContactLinkInner.contactEmail}}
                                        </label>
                                    </li>
                                </ul>
                            </accordion-group>
                    </accordion>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="span12">
        <h4>Step 4: Submissions to be Sent</h4>
        <div class="well">

            <div class="well-small">
                <div class="unstyled" data-ng-repeat="submission in submission.submissions">

                </div>
            </div>
        </div>


    </div>
</div>
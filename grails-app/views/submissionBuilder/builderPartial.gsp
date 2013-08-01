<tabset>
    <tab heading="Submission Builder" data-ng-model="submissionBuilderTab" active="submissionBuilderTab.active">
        <div class = "row">
            <div class="span4">
                <h3></h3>
            </div>
            <div class="span6">
                <h3 class="blueText text-center">{{ master.parentSubmission.lead.clientName }}</h3>
            </div>
            <div class="span2"><div data-ng-hide="editingSubmission==true" class="btn btn-mini pull-right" data-ng-click="clear()"><i class="icon-refresh"></i> Reset</div></div>
        </div>
        <div class = "row">
            <div class="span4">

                <h4>
                    Step 1: Select LOBs
                    <button type="button" class="btn btn-mini pull-right">
                        <i class="icon-plus"></i>
                        Add
                    </button>
                </h4>

                <div class="lobContainer">
                    <div class="well well-small">
                        <ul class="unstyled" data-ng-repeat="lineOfBusiness in master.parentSubmission.lead.linesOfBusiness | orderBy: 'lineOfBusiness.description'">
                            <li>
                                <label class="checkbox">
                                    <input type="checkbox" data-ng-model="lobs[lineOfBusiness.id]" />
                                    <span popover-title="{{lineOfBusiness.description}} Details" popover="{{formatLOBDetails(lineOfBusiness)}}" popover-trigger="hover" popover-placement="right">
                                        {{lineOfBusiness.description}} - {{lineOfBusiness.expirationDate | date:'MM/dd/yy'}}
                                    </span>
                                </label>
                            </li>
                        </ul>
                    </div>
                </div>

                <h4>
                    Step 2: Attach Documents
                    <button type="button" class="btn btn-mini pull-right">
                        <i class="icon-upload"></i>
                        Upload
                    </button>
                </h4>

                <div class="docContainer">
                    <div class="well well-small" >
                        <ul class="unstyled" data-ng-repeat="doc in testDocs">
                            <li>
                                <label class="checkbox">
                                    <input type="checkbox" data-ng-model="docs[doc.id]" />
                                    {{ doc.docName }}
                                </label>
                            </li>
                        </ul>
                    </div>
                </div>

            </div>
            <div class="span8">
                <h4>Step 3: Select Your Markets</h4>
                <div class="well">
                    <form class="form-search">
                        <div class="input-append pull-left">
                            <input type="text" class="input-large search-query" data-ng-pattern="/[A-Za-z0-9]/" placeholder="Filter Markets..." data-ng-model="marketCriteria">
                            <button type="button" data-ng-click="marketCriteria = ''" class="btn"><g:message code="default.button.clear.label" /></button>
                        </div>
                    </form>
                    <br>
                    <tabset>

                        <tab heading="Favorite Markets">
                            <accordion close-others="oneAtATime">
                                <div class="fixedHeightContainer">
                                    <div class="marketContainer">
                                        <accordion-group heading="{{accountContactLink.accountName}}" data-ng-repeat="accountContactLink in favoriteMarkets | filter:marketCriteria | orderBy:'accountName' | unique:'accountName'">
                                            <ul class="inline" data-ng-repeat="accountContactLinkInner in favoriteMarkets | filter: accountContactLink.accountName | orderBy: 'accountContactName'">
                                                <g:render template="marketList" />
                                            </ul>
                                        </accordion-group>
                                    </div>
                                </div>
                            </accordion>
                        </tab>

                        <tab heading="All Markets">
                            <accordion close-others="oneAtATime">
                                <div class="fixedHeightContainer">
                                    <div class="marketContainer">
                                        <accordion-group heading="{{accountContactLink.accountName}}" data-ng-repeat="accountContactLink in allMarkets | filter:marketCriteria | orderBy:'accountName' | unique:'accountName'">
                                            <ul class="inline" data-ng-repeat="accountContactLinkInner in allMarkets | filter: accountContactLink.accountName | orderBy: 'accountContactName'">
                                                <g:render template="marketList" />
                                            </ul>
                                        </accordion-group>
                                    </div>
                                </div>
                            </accordion>
                        </tab>

                        <tab heading="Sponsored Markets">
                            <accordion close-others="oneAtATime">
                                <div class="fixedHeightContainer">
                                    <div class="marketContainer">
                                    </div>
                                </div>
                            </accordion>
                        </tab>

                        <tab heading="In-House Markets">
                            <accordion close-others="oneAtATime">
                                <div class="fixedHeightContainer">
                                    <div class="marketContainer">
                                        <accordion-group heading="{{accountContactLink.accountContactName}}" data-ng-repeat="accountContactLink in accountContactMarkets | filter:marketCriteria | orderBy:'accountContactName'| unique:'accountContactName'">
                                            <ul class="inline" data-ng-repeat="accountContactLinkInner in accountContactMarkets | filter: accountContactLink.accountContactName | orderBy: 'accountName'">
                                                <li class="inline">
                                                    <label class="checkbox">
                                                        <input type="checkbox" data-ng-model="markets[accountContactLinkInner.linkId]" />
                                                        <strong>
                                                            <span class="blueText">
                                                                {{accountContactLinkInner.accountName}}</a>
                                                            </span>
                                                        </strong>
                                                    </label>
                                                </li>
                                            </ul>
                                        </accordion-group>
                                    </div>
                                </div>
                            </accordion>
                        </tab>

                    </tabset>


                </div>
                <button data-ng-show="editingSubmission==false" class="btn btn-primary pull-right" data-ng-click="buildSubmissions()"><i class="icon-sitemap"></i> Build Submission</button>
                <button data-ng-show="editingSubmission==true" class="btn pull-right" data-ng-click="cancelUpdate()"><i class="icon-undo"></i> Cancel</button>
                <button data-ng-show="editingSubmission==true" class="btn btn-primary pull-right" data-ng-click="updateSubmission(); submissionsTab.active=true"><i class="icon-save"></i> Update Submission</button>

            </div>
        </div>
    </tab>
    <tab heading="Submissions ({{master.childSubmissions.length}})" data-ng-model="submissionsTab" active="submissionsTab.active">
        %{--<div data-ng-show="editingSubmission==true" class="alert alert-info">Currently Editing submission #{{index}}</div>--}%
        <div class="row">
            <div class="span12">
                <ul class="inline">
                    <li data-ng-repeat="submission in master.childSubmissions | orderBy:'index'" class="span5 well well-small">

                        <div class="row ">
                            <div class="span5">
                                <div class="btn btn-mini btn-danger pull-right" data-ng-hide="editingSubmission==true" data-ng-click="deleteSubmission(submission)"><i class="icon-remove"></i> Delete</div>
                                <h4 data-ng-repeat="market in submission.markets">
                                    {{filterAndPrintMarket(market)}}
                                    %{--<div class="btn btn-mini pull-right" data-ng-hide="editingSubmission==true" data-ng-click="editSubmission(submission); submissionBuilderTab.active=true"><i class="icon-edit"></i> Edit</div>--}%
                                </h4>
                            </div>
                        </div>

                        <div class="row">
                            <div class="span5">
                                <div class="btn pull-right"><i class="icon-pencil"> Message</i></div>
                                <label for="targetDate"><strong>Target Date:</strong>
                                    <input id="targetDate" class="input-small" type="text" value="{{getLobTargetDate(submission.lobs[0])}}" />
                                </label>
                            </div>
                        </div>

                        <table class="table table-condensed">
                            <thead>
                                <tr><th></th><th>LOB</th><th>Target Premium</th></tr>
                            </thead>
                            <tbody>
                            <tr data-ng-repeat="lob in submission.lobs">
                                <td>
                                    <div class="btn btn-mini btn-danger" data-ng-click="removeLOBFromSubmission($parent.$index, lob)"><i class="icon-remove"></i></div>
                                </td>
                                <td>
                                    {{getLobDescription(lob)}}
                                </td>
                                <td>
                                    <input class="inline" type="text" value="{{getLobPremium(lob)}}" />
                                </td>
                            </tr>
                            </tbody>
                        </table>

                        <table class="table">
                            <tbody>
                            <tr data-ng-repeat="doc in submission.docs">
                                <td>
                                    <i class="icon-file"></i> {{getTestDocName(doc)}}
                                </td>
                            </tr>
                            </tbody>
                        </table>

                    </li>
                </ul>
            </div>
        </div>
        <button data-ng-hide="master.childSubmissions.length == 0 || editingSubmission==true" data-ng-hide="editingSubmission==true" class="btn btn-primary pull-right" data-ng-click="sendSubmissions()"><i class="icon-envelope"></i> Send Submissions</button>
    </tab>
</tabset>


%{--<div class="row">--}%
    %{--<div class="span12">--}%
        %{--<pre>childSubmissions = {{master.childSubmissions | json}}</pre>--}%
        %{--<pre>markets = {{markets | json}}</pre>--}%
        %{--<pre>lobs = {{lobs | json}}</pre>--}%
    %{--</div>--}%
%{--</div>--}%
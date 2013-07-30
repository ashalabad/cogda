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
                                    <span popover-title="{{lineOfBusiness.lineOfBusiness.description}} Details" popover="{{formatLOBDetails(lineOfBusiness)}}" popover-trigger="hover" popover-placement="right">
                                        {{lineOfBusiness.lineOfBusiness.description}} - {{lineOfBusiness.expirationDate | date:'MM/dd/yy'}}
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
                        <ul class="unstyled" data-ng-repeat="doc in master.parentSubmission.lead.docs">
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
                <button data-ng-show="editingSubmission==false" class="btn btn-primary pull-right" data-ng-click="buildSubmission()"><i class="icon-sitemap"></i> Build Submission</button>
                <button data-ng-show="editingSubmission==true" class="btn pull-right" data-ng-click="cancelUpdate()"><i class="icon-undo"></i> Cancel</button>
                <button data-ng-show="editingSubmission==true" class="btn btn-primary pull-right" data-ng-click="updateSubmission(); submissionsTab.active=true"><i class="icon-save"></i> Update Submission</button>

            </div>
        </div>
    </tab>
    <tab heading="Submissions ({{master.childSubmissions.length}})" data-ng-model="submissionsTab" active="submissionsTab.active">
        <div data-ng-show="editingSubmission==true" class="alert alert-info">Currently Editing submission #{{index}}</div>
        <button data-ng-hide="master.childSubmissions.length == 0 || editingSubmission==true" data-ng-hide="editingSubmission==true" class="btn btn-primary pull-right" data-ng-click="sendSubmissions()"><i class="icon-envelope"></i> Send Submissions</button>
        <div class="row">
            <div class="span12">
                <ul class="inline">
                    <li data-ng-repeat="submission in master.childSubmissions | orderBy:'index'" class="span5 well well-small">
                        <h5>Submission #{{submission.index}}
                            <div class="btn btn-mini btn-danger pull-right" data-ng-hide="editingSubmission==true" data-ng-click="deleteSubmission(submission)"><i class="icon-remove"></i> Delete</div>
                            <div class="btn btn-mini pull-right" data-ng-hide="editingSubmission==true" data-ng-click="editSubmission(submission); submissionBuilderTab.active=true"><i class="icon-edit"></i> Edit</div>
                        </h5>
                        <div class="btn btn-block" data-ng-click="lobCollapse{{submission.index}} = !lobCollapse{{submission.index}}">Lines of Business</div>
                        <ul collapse="lobCollapse{{submission.index}}">
                            <li data-ng-repeat="lob in submission.lobs">
                                {{filterAndPrintLOB(lob)}}
                            </li>
                        </ul>
                        <div class="btn btn-block" data-ng-click="docCollapse{{submission.index}} = !docCollapse{{submission.index}}">Submission Documents</div>
                        <ul collapse="docCollapse{{submission.index}}">
                            <li data-ng-repeat="doc in submission.docs">
                            </li>
                        </ul>
                        <div class="btn btn-block" data-ng-click="marketCollapse{{submission.index}} = !marketCollapse{{submission.index}}">Markets</div>
                        <ul collapse="marketCollapse{{submission.index}}">
                            <li data-ng-repeat="market in submission.markets">
                                {{filterAndPrintMarket(market)}}
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </tab>
</tabset>


%{--<div class="row">--}%
    %{--<div class="span12">--}%
        %{--<pre>master = {{master.childSubmissions | json}}</pre>--}%
        %{--<pre>markets = {{markets | json}}</pre>--}%
        %{--<pre>lobs = {{lobs | json}}</pre>--}%
    %{--</div>--}%
%{--</div>--}%
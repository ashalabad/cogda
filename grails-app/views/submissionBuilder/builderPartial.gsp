<div class = "row">
    <div class="span4">
        <h3><g:message code="submissionBuilder.label"/></h3>
    </div>
    <div class="span8">
        <h3 class="blueText text-center">{{ submission.lead.clientName }}</h3>
    </div>
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
                <ul class="unstyled" data-ng-repeat="lineOfBusiness in submission.lead.linesOfBusiness | orderBy: 'lineOfBusiness.description'">
                    <li>
                        <label class="checkbox">
                            <input type="checkbox" />
                            <span popover-title="{{lineOfBusiness.lineOfBusiness.description}} Details" popover="{{formatLOBDetails(lineOfBusiness)}}" popover-trigger="hover" popover-placement="right">
                                {{lineOfBusiness.lineOfBusiness.description}} - {{lineOfBusiness.expirationDate | date:'M/d/yy @ h:mm a'}}
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
            <div class="well well-small">
                <ul class="unstyled" data-ng-repeat="file in submission.lead.files">
                    <li>
                        <label class="checkbox">
                            <input type="checkbox" name="selectedFile" />
                            {{ file.fileName }}
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
                                                <input type="checkbox" name="{{accountContactLinkInner.linkId}}" data-ng-click="toggleAccountContactLink(accountContactLinkInner.linkId,this.checked)"/>
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
        <g:link class="btn btn-primary pull-right"><i class="icon-sitemap"></i> Build Submission</g:link>
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
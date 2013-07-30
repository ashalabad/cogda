<h3 class="center"><g:message code="submissionBuilder.label"/></h3>
<div class = "row">
    <div class="span2">

    </div>
    <div class="span8 well">
        <form class="form-search">
            <div class="input-append pull-left">
                <input type="text" class="input-large search-query" data-ng-pattern="/[A-Za-z0-9]/" data-ng-model="searchString" placeholder="Search for Prospect...">
                <button type="button" data-ng-click="clearSearch()" class="btn"><g:message code="default.button.clear.label" /></button>
            </div>
            <g:link controller="prospect" action="create" class="btn btn-primary pull-right"><i class="icon-plus"></i> Add New Prospect</g:link>
        </form>
    </div>

    <div class="span2">

    </div>
</div>

<div class="row">

    <div class="span2">

    </div>

    <div class="span8 well">
        <div class="fixedHeightContainer">
            <div class="prospectContainer">
                <ul data-ng-repeat="lead in leadList" class="nav nav-list">
                    <li class="well well-small">
                        {{lead.clientName}}
                        <button type="button"class="btn btn-primary btn-mini pull-right" data-ng-click = "buildSubmission(lead)">
                            <i class="icon-list-alt"></i> Start Building
                        </button>
                    </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="span2">

    </div>

</div>

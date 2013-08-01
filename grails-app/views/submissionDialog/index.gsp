<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="angularLayout" />
        <g:set var="layout_nosecondarymenu"	value="true" scope="request"/>
        <g:set var="layout_nomainmenu"		value="true" scope="request"/>
        <title>
            <g:message code="submissionDialog.label"/>
        </title>
        <r:require module="submissionDialog"/>
    </head>

    <body>
        <content tag="header">
            <!-- Empty Header -->
        </content>
        <section id="submissionDialogSection" class="first" data-ng-app="submissionDialogApp">

            <div class="row" data-ng-controller="submissionDialogMainController">
                <div class="span3">

                    <ul class="nav navbar-nav">
                        <li>
                            <a href="#">&laquo; <g:message code="submissionDialog.mySubmissions.label"></g:message></a>
                        </li>
                    </ul>

                    <div class="well">
                        <g:render template = "rootSubmissionPartial"/>

                        <button type="button" class="btn btn-primary" data-ng-click="showSubmissionSummary()">
                            <g:message code="submissionDialog.submissionSummary.label"/>
                        </button>

                        <g:render template = "childSubmissionsPartial"/>

                    </div>
                </div>
                <div class="span9">

                  <div data-ng-view=""></div>

                </div>

            </div>

        </section>
    </body>
</html>
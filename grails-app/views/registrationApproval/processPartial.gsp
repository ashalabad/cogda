<ul class="breadcrumb">
    <li><a href="#/list">Registration Approval List</a></li>
</ul>

<div class="row">

    <div class="span4">

        <h3><g:message code="registrationApproval.label"/></h3>

            <form name="processRegistrationForm" method="post" class="form-horizontal" data-ng-show="!isProcessingApproval()">
                <fieldset class="form">
                    <div class="control-group fieldcontain">
                        <label for="subDomain" class="control-label">
                            <g:message code="registration.subDomain.label" default="Sub Domain" />
                        </label>
                        <div class="controls">
                            <input type="text" name="subDomain" name="subDomain"
                                   id="subDomain" data-ng-required="true" data-ng-model="subDomain"
                                   data-ng-pattern="/[A-Za-z]/" data-ng-class="{error:!isApproveButtonClickable()}"
                                   data-ng-Minlength="2">
                            <span class="help-inline">{{ subDomain }}.${grailsApplication.config.grails.domainURL}</span>
                        </div>
                    </div>
                </fieldset>
                <div class="form-actions">

                    <button type="button"  value="Approve" class="btn btn-primary"
                            data-ng-click="approveRegistration(processRegistrationForm)"
                            data-ng-disabled="!isApproveButtonClickable()">
                        <i class="icon-check"></i>&nbsp;
                        <g:message code="registrationApproval.approve.button"/>
                    </button>
                </div>
            </form>

            <div data-ng-show="isProcessingApproval()" class="center">
                <div class="alert alert-info">
                    <g:message code="registrationApproval.processingApproval.message"/>
                </div>
                <img src="${resource(dir:'images', file:'spinner.gif')}" title="Processing... Please wait."/>
            </div>
    </div>

    <div class="offset1 span7">

        <h3><g:message code="registrationApproval.currentRegistration.label"/>

        <span class="pull-right" data-ng-show="isRejectable()">
            <button class="btn btn-danger" data-ng-click="rejectRegistration(processRegistrationForm)"
                    data-ng-show="!isProcessingApproval()" data-ng-disabled="!isRejectButtonClickable()">
                <i class="icon-remove"></i>&nbsp;
                <g:message code="registrationApproval.reject.button"/>
            </button>
        </span>
        </h3>

        <g:render template="/registrationApproval/showRegistration"/>

    </div>
</div>
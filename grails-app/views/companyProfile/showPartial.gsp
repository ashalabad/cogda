<form name="companyProfileShow" class="form-horizontal">
    <legend>
        <g:message code="companyProfile.label"/>
        <span class="pull-right">
            <button class="btn btn-success" data-ng-click = "editCompanyProfile()">
                <i class="icon-edit"></i>
                <g:message code="default.button.edit.label"/>
            </button>
        </span>
    </legend>

    <div class="control-group fieldcontain ">
        <label class="control-label">
            <g:message code="companyProfile.published.label" />
        </label>
        <div class="controls">
            <span class="" data-ng-class="{true:'label label-success', false:'label '}[companyProfile.published == true]">
                {{ companyProfile.published ? 'Published' : 'Not Published' }}
            </span>
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="company.companyName.label" />
        </label>
        <div class="controls">
            {{ companyProfile.company.companyName }}
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="company.parentCompany.companyName.label" />
        </label>
        <div class="controls">
            {{ companyProfile.company.parentCompany.companyName }}
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.website" />
        </label>
        <div class="controls">
            {{ companyProfile.website }}
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyDescription" />
        </label>
        <div class="controls">
            {{ companyProfile.companyDescription }}
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.businessSpecialties" />
        </label>
        <div class="controls">
            {{ companyProfile.businessSpecialties }}
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.associations" />
        </label>
        <div class="controls">
            {{ companyProfile.associations }}
            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyProfileContacts.label" />
        </label>
        <div class="controls">

            <ul data-ng-repeat = "companyProfileContact in companyProfile.companyProfileContacts">
                <li>
                    {{ companyProfileContact.userProfile.firstName }}
                    {{ companyProfileContact.userProfile.lastName }}
                </li>
            </ul>

            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyProfileAddresses.label" />
        </label>
        <div class="controls">

            <ul data-ng-repeat = "companyProfileContact in companyProfile.companyProfileContacts">
                <li>
                    {{ companyProfileContact.userProfile.firstName }}
                    {{ companyProfileContact.userProfile.lastName }}
                </li>
            </ul>

            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyProfilePhoneNumbers.label" />
        </label>
        <div class="controls">

            <ul class = "unstyled" data-ng-repeat = "companyProfilePhoneNumber in companyProfile.companyProfilePhoneNumbers">
                <li>
                    {{ companyProfilePhoneNumber.phoneNumber }}  {{ companyProfilePhoneNumber.primary ? 'Primary' : '' }}
                </li>
            </ul>

            <span class="help-inline"></span>
        </div>
    </div>
</form>
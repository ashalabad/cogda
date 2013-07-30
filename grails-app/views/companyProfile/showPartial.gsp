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
            <g:message code="companyProfile.companyWebsite" />
        </label>
        <div class="controls">
            <a data-ng-href="{{companyProfile.companyWebsite}}" target="_blank">
                {{ companyProfile.companyWebsite }}
            </a>
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

            <ul class="unstyled" data-ng-repeat = "companyProfileContact in companyProfile.companyProfileContacts | orderBy:lastName" >
                <li>
                    <a data-ng-click="showNotImpMessage = !showNotImpMessage">
                    {{ companyProfileContact.userProfile.firstName }} {{ companyProfileContact.userProfile.lastName }}

                    </a>
                    &nbsp;
                    <span data-ng-show="showNotImpMessage" class="label label-warning">&raquo; Will link to the public User Profile
                    </span>
                </li>
            </ul>

            <span class="help-inline"></span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyProfileAddresses.label" />(es)
        </label>
        <div class="controls">


            <div data-ng-repeat = "companyProfileAddress in companyProfile.companyProfileAddresses">
                <address>
                    <p data-ng-show="companyProfileAddress.address.addressOne">
                        <strong>

                            {{companyProfileAddress.address.addressOne}}
                        </strong>
                    </p>
                    <p data-ng-show="companyProfileAddress.address.addressTwo">
                        {{companyProfileAddress.address.addressTwo}}
                    </p>
                    <p data-ng-show="companyProfileAddress.address.addressThree">
                        {{companyProfileAddress.address.addressThree}}
                    </p>
                    <p data-ng-show="companyProfileAddress.address.city">
                        {{companyProfileAddress.address.city}}
                    </p>
                    <p data-ng-show="companyProfileAddress.address.state">
                        {{companyProfileAddress.address.state}}
                    </p>
                    <p data-ng-show="companyProfileAddress.address.country">
                        {{companyProfileAddress.address.country | uppercase }}
                    </p>
                </address>
            </div>
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
                    ({{companyProfilePhoneNumber.label}}) {{ companyProfilePhoneNumber.phoneNumber }}

                    <span class="label label-info" data-ng-show="companyProfilePhoneNumber.primary">{{ companyProfilePhoneNumber.primary ? 'Primary' : '' }}</span>
                </li>
            </ul>

            <span class="help-inline"></span>
        </div>
    </div>
</form>
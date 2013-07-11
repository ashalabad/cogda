<form name="companyProfileDetail" class="form-horizontal">
    <legend>
        <g:message code="companyProfile.label"/>
        <span class="pull-right">
            <button class="btn " data-ng-click = "showCompanyProfile()">

                <g:message code="default.button.cancel.label"/>
            </button>
        </span>
    </legend>

    <div class="control-group fieldcontain ">
        <label for="published" class="control-label">
            <g:message code="companyProfile.published.label" />
        </label>
        <div class="controls">
            <input type="checkbox" name="published" id="published" data-ng-model="companyProfile.published"/>
            <span class="help-inline" data-ng-show="errors.published">{{ errors.published }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="company.companyName.label" />
        </label>
        <div class="controls">
            {{ companyProfile.company.companyName }}
            <span class="help-inline" data-ng-show="errors. ">{{ errors. }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="company.parentCompany.companyName.label" />
        </label>
        <div class="controls">
            {{ companyProfile.company.parentCompany.companyName }}
            <span class="help-inline" data-ng-show="errors. ">{{ errors. }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.website" />
        </label>
        <div class="controls">
            <input type="text" data-ng-model = "companyProfile.website" id="companyProfile.website" name="companyProfile.website"/>
            <span class="help-inline" data-ng-show="errors.website">{{ errors.website }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyDescription" />
        </label>
        <div class="controls">

            <textarea data-ng-model="companyProfile.companyDescription" name="companyProfile.companyDescription" id="companyProfile.companyDescription"></textarea>

            <span class="help-inline" data-ng-show="errors.companyDescription ">{{ errors.companyDescription }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.businessSpecialties" />
        </label>
        <div class="controls">
            <textarea data-ng-model="companyProfile.businessSpecialties" name="companyProfile.businessSpecialties" id="companyProfile.businessSpecialties"></textarea>
            <span class="help-inline" data-ng-show="errors.businessSpecialties ">{{ errors.businessSpecialties }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.associations" />
        </label>
        <div class="controls">
            {{ companyProfile.associations }}
            <textarea data-ng-model="companyProfile.associations" name="companyProfile.associations" id="companyProfile.associations"></textarea>
            <span class="help-inline" data-ng-show="errors.associations ">{{ errors.associations }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyProfileContacts.label" />
        </label>
        <div class="controls">

            <input type="text" data-ng-model="companyProfileContactUserProfile" data-ng-change

            <ul data-ng-repeat = "companyProfileContact in companyProfile.companyProfileContacts">
                <li>
                    {{ companyProfileContact.userProfile.firstName }}
                    {{ companyProfileContact.userProfile.lastName }}
                </li>
            </ul>

            <span class="help-inline" data-ng-show="errors. ">{{ errors. }}</span>
        </div>
    </div>

    <div class="control-group fieldcontain">
        <label class="control-label">
            <g:message code="companyProfile.companyProfileAddresses.label" />
        </label>
        <div class="controls" >

                <div data-ng-repeat = "companyProfileAddress in companyProfile.companyProfileAddresses">

                    <address class="inner-list-address">
                        <span data-ng-show="companyProfileAddress.address.addressOne">
                            <strong>{{ companyProfileAddress.address.addressOne }}</strong>
                        </span>

                        <span data-ng-show="companyProfileAddress.address.addressTwo">
                            <br> {{ companyProfileAddress.address.addressTwo }}
                        </span>

                        <span data-ng-show="companyProfileAddress.address.addressThree ">
                            <br> {{ companyProfileAddress.address.addressThree }}
                        </span>

                        <span data-ng-show="companyProfileAddress.address.zipcode">
                            <br> {{ companyProfileAddress.address.zipcode }}
                        </span>
                        <span data-ng-show="companyProfileAddress.address.city">
                            <br> {{ companyProfileAddress.address.city }}
                        </span>
                        <span data-ng-show="companyProfileAddress.address.state">
                            <br> {{ companyProfileAddress.address.state }}
                        </span>
                        <span data-ng-show="companyProfileAddress.address.country ">
                            <br> {{ companyProfileAddress.address.country  }}
                        </span>
                    </address>

                    <button class="btn btn-info btn-mini" data-ng-click="editCompanyProfileAddress()">
                        <i class="icon-edit"></i> Edit
                    </button>
                    <button class="btn btn-danger btn-mini" data-ng-click="deleteCompanyProfileAddress()">
                        <i class="icon-remove"></i> Delete
                    </button>

                </div>

            <span class="help-inline" data-ng-show="errors. ">{{ errors. }}</span>
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

            <span class="help-inline" data-ng-show="errors. ">{{ errors. }}</span>
        </div>
    </div>

    <div class="form-actions">
        <button type="button" class="btn btn-primary" id="companyProfileEditFormSave">
            <g:message code="default.button.update.label"/>
        </button>
        <button class="btn " data-ng-click = "showCompanyProfile()">
            <g:message code="default.button.cancel.label"/>
        </button>
    </div>
</form>
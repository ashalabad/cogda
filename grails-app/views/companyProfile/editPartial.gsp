<div name="companyProfileShow" class="form-horizontal" data-ng-hide="editingCompanyProfile">
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
</div>

<form name="companyProfileDetailForm"
      class="form-horizontal"
      data-ng-submit="updateCompanyProfile(companyProfile)"
      data-ng-show="editingCompanyProfile">
    <fieldset>
        <legend>
            <g:message code="companyProfile.label"/>
            <span class="pull-right">
                <button type="button" class="btn " data-ng-click = "showCompanyProfile()">
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
                <span class="help-inline" ></span>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label class="control-label" >
                <g:message code="company.parentCompany.companyName.label" />
            </label>
            <div class="controls">
                {{ companyProfile.company.parentCompany.companyName }}
                <span class="help-inline" ></span>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label class="control-label" for="companyWebsite">
                <g:message code="companyProfile.companyWebsite" />
            </label>
            <div class="controls">
                <input type="url"
                       data-ng-model = "companyProfile.companyWebsite"
                       id="companyWebsite"
                       name="companyWebsite"/>
                <span class="help-inline" data-ng-show="errors.companyWebsite ">  {{ errors.companyWebsite }}</span>
                <span class="label label-important" data-ng-show="companyProfileDetailForm['companyWebsite'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" data-ng-show="companyProfileDetailForm['companyWebsite'].$valid"> ${message(code:'default.valid.message')}  </span>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label class="control-label" for="companyDescription">
                <g:message code="companyProfile.companyDescription" />
            </label>
            <div class="controls">

                <textarea data-ng-model="companyProfile.companyDescription" name="companyDescription" id="companyDescription"></textarea>

                <span class="help-inline" data-ng-show="errors.companyDescription ">  {{ errors.companyDescription }}</span>
                <span class="label label-important" data-ng-show="companyProfileDetailForm['companyDescription'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" data-ng-show="companyProfileDetailForm['companyDescription'].$valid"> ${message(code:'default.valid.message')}  </span>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label class="control-label" for="businessSpecialties">
                <g:message code="companyProfile.businessSpecialties" />
            </label>
            <div class="controls">
                <textarea data-ng-model="companyProfile.businessSpecialties" name="businessSpecialties" id="businessSpecialties"></textarea>
                <span class="help-inline" data-ng-show="errors.businessSpecialties ">  {{ errors.businessSpecialties }}</span>
                <span class="label label-important" data-ng-show="companyProfileDetailForm['businessSpecialties'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" data-ng-show="companyProfileDetailForm['businessSpecialties'].$valid"> ${message(code:'default.valid.message')}  </span>
            </div>
        </div>

        <div class="control-group fieldcontain">
            <label class="control-label" for="associations">
                <g:message code="companyProfile.associations" />
            </label>
            <div class="controls">
                <textarea data-ng-model="companyProfile.associations" name="associations" id="associations"></textarea>
                <span class="help-inline" data-ng-show="errors.associations ">  {{ errors.associations }}</span>
                <span class="label label-important" data-ng-show="companyProfileDetailForm['associations'].$invalid"> ${message(code:'default.invalid.message')} </span>
                <span class="label label-success" data-ng-show="companyProfileDetailForm['associations'].$valid"> ${message(code:'default.valid.message')}  </span>
            </div>
        </div>
    </fieldset>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" id="companyProfileEditFormSave" data-ng-disabled="!canSave()">
            <g:message code="default.button.update.label"/>
        </button>
        <button type="button" class="btn " data-ng-click = "showCompanyProfile()">
            <g:message code="default.button.cancel.label"/>
        </button>
    </div>
</form>

<!-- START: Company Profile Contacts -->
<form class="form-horizontal">
    <fieldset class="embedded">
        <legend>
            <g:message code="companyProfile.companyProfileContacts.label" />
            <span class="pull-right">
                <button class="btn btn-success" data-ng-click="addUserProfilePhoneNumber()">
                    <i class="icon-plus"></i>
                    ${message(code: 'default.add.label', args: [message(code: 'companyProfile.companyProfileContacts.label', default: 'UserProfilePhoneNumber')])}
                </button>
            </span>
        </legend>
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
   </fieldset>
</form>
<!-- END: Company Profile Contacts -->

<!-- START: Company Profile Address -->
<div class="form-horizontal" data-ng-controller="editCompanyProfileAddressController">
    <fieldset class="embedded">
        <legend>
            <g:message code="companyProfile.companyProfileAddresses.label" />
            <span class="pull-right">
                <button class="btn btn-success " data-userprofile-id="${userProfileInstance?.id}" id="addUserProfilePhoneNumber" data-ng-model="addButton">
                    <i class="icon-plus"></i>
                    ${message(code: 'default.add.label', args: [message(code: 'companyProfile.companyProfileAddresses.label', default: 'UserProfilePhoneNumber')])}
                </button>
            </span>
        </legend>

    <div data-ng-repeat = "companyProfileAddress in companyProfile.companyProfileAddresses">

        <div class="control-group fieldcontain" data-ng-hide="editingCompanyProfileAddress">
            <label class="control-label">
                <g:message code="companyProfile.companyProfileAddresses.label" />
            </label>
            <div class="controls">

                <address class="inner-list-address" data-ng-hide="editingCompanyProfileAddress" data-ng-click="editingCompanyProfileAddress = true">
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

                <button class="btn btn-info" data-ng-click="editCompanyProfileAddress()" data-ng-hide="editingCompanyProfileAddress" data-ng-model="editButton">
                    <i class="icon-edit"></i> <g:message code = "default.button.edit.label"/>
                </button>
            </div>
        </div>

        <div
             data-ng-form = "editCompanyProfileAddressForm"
             data-ng-show="editingCompanyProfileAddress">

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['published'].$invalid, success: editCompanyProfileAddressForm['published'].$valid}">
                <label class="control-label">
                    Published?
                </label>
                <div class="controls">
                    <input type="checkbox" data-ng-model="companyProfileAddress.published" name="published"/>

                    <span class="help-inline" data-ng-show="errors.published ">  {{ errors.published }}</span>
                    <span class="error" ng-show="editCompanyProfileAddressForm['published'].$invalid"> </span>
                    <span class="success" ng-show="editCompanyProfileAddressForm['published'].$valid"> </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['primaryAddress'].$invalid, success: editCompanyProfileAddressForm['primaryAddress'].$valid}">
                <label class="control-label">
                    Primary Address?
                </label>
                <div class="controls">
                    <input type="checkbox" data-ng-model="companyProfileAddress.primaryAddress" name="primaryAddress" />

                    <span class="help-inline" data-ng-show="errors.primaryAddress ">  {{ errors.primaryAddress }}</span>
                    <span class="error" ng-show="editCompanyProfileAddressForm['primaryAddress'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="success" ng-show="editCompanyProfileAddressForm['primaryAddress'].$valid"> ${message(code:'default.valid.message')}  </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.addressOne'].$invalid, success: editCompanyProfileAddressForm['address.addressOne'].$valid}">
                <label class="control-label" data-ng-click="">
                    ${message(code:'companyProfileAddress.address.addressOne')}
                    <span class="required-indicator">*</span>
                </label>
                <div class="controls">
                    <input type="text"
                           data-ng-model="companyProfileAddress.address.addressOne"
                           name="address.addressOne"
                           required
                           placeholder="${message(code:'companyProfileAddress.address.addressOne')}"
                           autofocus />
                    <span class="help-inline error" data-ng-show="errors.address.addressOne "> {{ errors.address.addressOne }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.addressOne'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.addressOne'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.addressTwo'].$invalid, success: editCompanyProfileAddressForm['address.addressTwo'].$valid}">
                <label class="control-label">
                    ${message(code:'companyProfileAddress.address.addressTwo')}
                </label>
                <div class="controls">
                    <input type="text"
                           name="address.addressTwo"
                           data-ng-model="companyProfileAddress.address.addressTwo"
                           placeholder="${message(code:'companyProfileAddress.address.addressTwo')}"/>
                    <span class="help-inline" data-ng-show="errors.address.addressTwo ">{{ errors.address.addressTwo }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.addressTwo'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.addressTwo'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.addressThree'].$invalid, success: editCompanyProfileAddressForm['address.addressThree'].$valid}">
                <label class="control-label">
                    ${message(code:'companyProfileAddress.address.addressThree')}
                </label>
                <div class="controls">
                    <input type="text"
                            name="address.addressThree"
                           data-ng-model="companyProfileAddress.address.addressThree"
                           placeholder="${message(code:'companyProfileAddress.address.addressThree')}"/>
                    <span class="help-inline" data-ng-show="errors.address.addressThree ">{{ errors.address.addressThree }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.addressThree'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.addressThree'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.zipcode'].$invalid, success: editCompanyProfileAddressForm['address.zipcode'].$valid}">
                <label class="control-label">
                    ${message(code:'companyProfileAddress.address.zipcode')}
                </label>
                <div class="controls">
                    <input type="text"
                            name="address.zipcode"
                           data-ng-model="companyProfileAddress.address.zipcode"
                           placeholder="${message(code:'companyProfileAddress.address.zipcode')}"/>
                    <span class="help-inline" data-ng-show="errors.address.zipcode ">{{ errors.address.zipcode }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.zipcode'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.zipcode'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.city'].$invalid, success: editCompanyProfileAddressForm['address.city'].$valid}">
                <label class="control-label">
                    ${message(code:'companyProfileAddress.address.city')}
                </label>
                <div class="controls">
                    <input type="text"
                            name="address.city"
                           data-ng-model="companyProfileAddress.address.city"
                           placeholder="${message(code:'companyProfileAddress.address.city')}"/>
                    <span class="help-inline" data-ng-show="errors.address.city ">{{ errors.address.city }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.city'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.city'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.state'].$invalid, success: editCompanyProfileAddressForm['address.state'].$valid}">
                <label class="control-label">
                    ${message(code:'companyProfileAddress.address.state')}
                </label>
                <div class="controls">
                    <input type="text"
                            name="address.state"
                           data-ng-model="companyProfileAddress.address.state"
                           placeholder="${message(code:'companyProfileAddress.address.state')}"/>
                    <span class="help-inline" data-ng-show="errors.address.state ">{{ errors.address.state }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.state'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.state'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="control-group fieldcontain"
                 data-ng-class="{error: editCompanyProfileAddressForm['address.country'].$invalid, success: editCompanyProfileAddressForm['address.country'].$valid}">
                <label class="control-label">
                    ${message(code:'companyProfileAddress.address.country')}
                </label>
                <div class="controls">
                    <input type="text"
                            name="address.country"
                           data-ng-model="companyProfileAddress.address.country"
                           placeholder="${message(code:'companyProfileAddress.address.country')}"/>
                    <span class="help-inline" data-ng-show="errors.address.country ">{{ errors.address.country }}</span>
                    <span class="label label-important" ng-show="editCompanyProfileAddressForm['address.country'].$invalid"> ${message(code:'default.invalid.message')} </span>
                    <span class="label label-success" ng-show="editCompanyProfileAddressForm['address.country'].$valid"> ${message(code:'default.valid.message')} </span>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-success "
                        data-ng-click="updateCompanyProfileAddress(companyProfileAddress)"
                        data-ng-model="updateButton"
                        data-ng-disabled="!canSave(companyProfileAddress)">
                    Update
                </button>
                <button class="btn btn-danger " data-ng-click="deleteCompanyProfileAddress(companyProfileAddress.address)" data-ng-model="deleteButton">
                    <i class="icon-remove"></i> Delete
                </button>
                <button type="button" class="btn " data-ng-click="editingCompanyProfileAddress = false" data-ng-model="cancelButton">
                    Cancel
                </button>
            </div>
        </div>
    </div>
    </fieldset>
</div>
<!-- END: Company Profile Address -->

<!-- START: Company Profile Phone Numbers -->
<form class="form-horizontal">
    <fieldset class="embedded">
        <legend>
            <g:message code="companyProfile.companyProfilePhoneNumbers.label"/>
            <span class="pull-right">
                <button class="btn btn-success " data-ng-click="addCompanyProfilePhoneNumber()">
                    <i class="icon-plus"></i>
                    ${message(code: 'default.add.label', args: [message(code: 'companyProfile.companyProfilePhoneNumbers.label', default: 'UserProfilePhoneNumber')])}
                </button>
            </span>
        </legend>
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
    </fieldset>
</form>
<!-- END: Company Profile Phone Numbers -->
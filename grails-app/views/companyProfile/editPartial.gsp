<form name="companyProfileDetailForm"
      class="form-horizontal">
    <fieldset>
        <legend>
            <g:message code="companyProfile.label"/>
        </legend>

        <div id="showCompanyProfileDetail" class="form-horizontal" data-ng-hide="editingCompanyProfile">

            <g:render template="/companyProfile/companyProfileShow"/>

            <div class="form-actions">
                <button type="button"
                        class="btn btn-info"
                        data-ng-click = "editCompanyProfile()">
                    <i class="icon-edit icon-white"></i>
                    <g:message code="default.button.edit.label"/>
                </button>
            </div>
        </div>

        <div id="editCompanyProfileDetail" data-ng-show="editingCompanyProfile">

            <g:render template = "/companyProfile/companyProfileForm"/>

            <div class="form-actions">
                <button type="submit"
                        class="btn btn-success"
                        id="companyProfileEditFormSave"
                        data-ng-click="updateCompanyProfile(companyProfile)">
                    <i class="icon-pencil icon-white"></i>
                    <g:message code="default.button.update.label"/>
                </button>
                <button type="button"
                        class="btn "
                        data-ng-click = "showCompanyProfile()">
                    <i class="icon-ban-circle"></i>
                    <g:message code="default.button.cancel.label"/>
                </button>
            </div>

        </div>


    <!-- START: Company Profile Contacts -->
    <div data-ng-form name="companyProfileForm" class="form-horizontal">
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

                <input type="text" data-ng-model="companyProfileContactUserProfile" />

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
    </div>
    <!-- END: Company Profile Contacts -->

    <!-- START: Company Profile Address -->
    <div id="CompanyProfileAddresses" class="form-horizontal">
        <fieldset class="embedded">
            <legend>
                <g:message code="companyProfileAddress.label" />
            </legend>

            <div data-ng-repeat = "companyProfileAddress in companyProfile.companyProfileAddresses">
                <div data-ng-controller="editCompanyProfileAddressController">
                    <div class="control-group fieldcontain"
                         data-ng-hide="editingCompanyProfileAddress">
                        <label class="control-label">
                            <g:message code="companyProfile.companyProfileAddresses.label" />
                        </label>

                        <div class="controls">
                            <g:render template="/companyProfile/companyProfileAddressShow"/>
                        </div>
                        <div class="form-actions">
                            <button class="btn btn-info"
                                    type="button"
                                    data-ng-click="editCompanyProfileAddress()">
                                <i class="icon-edit"></i> <g:message code = "default.button.edit.label"/>
                            </button>
                            <button type="button"
                                    class="btn btn-danger "
                                    data-ng-click="deleteCompanyProfileAddress(companyProfileAddress, $index)"
                                    data-ng-disabled="!formActionsClickable">
                                <i class="icon-remove"></i> Delete
                            </button>
                        </div>
                    </div>

                    <div
                        data-ng-form = "companyProfileAddressForm"
                        data-ng-show="editingCompanyProfileAddress">

                        %{--render the shared addressFormFields--}%
                        <g:render template="/companyProfile/companyProfileAddressForm"/>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-success "
                                    data-ng-click="updateCompanyProfileAddress(companyProfileAddress)"
                                    data-ng-disabled="!canSave(companyProfileAddressForm) || !formActionsClickable">
                                <i class="icon-pencil"></i> Update
                            </button>
                            <button type="button"
                                    class="btn btn-danger "
                                    data-ng-click="deleteCompanyProfileAddress(companyProfileAddress, $index)"
                                    data-ng-disabled="!formActionsClickable">
                                <i class="icon-remove"></i> Delete
                            </button>
                            <button type="button"
                                    class="btn"
                                    data-ng-click="cancelEditCompanyProfileAddress()"
                                    data-ng-disabled="!formActionsClickable">
                                <i class="icon-ban-circle"></i>
                                Cancel
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Start: Add Address -->
            <div data-ng-controller="addCompanyProfileAddressController">
                <div data-ng-show="addingCompanyProfileAddress">
                    <div data-ng-form = "companyProfileAddressForm" class="form-horizontal">
                        <fieldset class="embedded">
                            <legend>
                                Add Address
                            </legend>

                            %{--render the shared companyProfileAddressForm--}%
                            <g:render template="/companyProfile/companyProfileAddressForm"/>

                            <div class="form-actions">
                                <button type="submit"
                                        class="btn btn-primary"
                                        data-ng-click="saveCompanyProfileAddress(companyProfileAddress)"
                                        data-ng-disabled="!canSave(companyProfileAddressForm) || !formActionsClickable">
                                    <i class="icon-plus icon-white"></i>
                                    Add Address</button>
                                <button type="button"
                                        class="btn"
                                        data-ng-click="cancelAddCompanyProfileAddress()"
                                        data-ng-disabled="!formActionsClickable">
                                    <i class="icon-ban-circle"></i>
                                    Cancel</button>
                            </div>
                        </fieldset>
                    </div>
                </div>

                <br>

                <button type="button"
                        class="btn"
                        data-ng-click="addCompanyProfileAddress()"
                        data-ng-hide="addingCompanyProfileAddress">
                    <i class="icon-plus"></i>
                    Add Address
                </button>
            </div>
            <!-- End: Add Address -->
        </fieldset>
    </div>
    <!-- END: Company Profile Address -->

    <!-- START: Company Profile Phone Numbers -->
    <div id="" class="form-horizontal">
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
    </div>
    <!-- END: Company Profile Phone Numbers -->
    </fieldset>
</form>
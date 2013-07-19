<form name="companyProfileDetailForm"
      novalidate="novalidate"
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

            </legend>
        <div class="control-group fieldcontain">
            <label class="control-label">
                <g:message code="companyProfile.companyProfileContacts.label" />
            </label>
            <div class="controls">

                <div data-ng-controller="addCompanyProfileContactController">

                    <div class = "row">

                        <div class="span6">

                            <div class="row">

                                <div class="span3">
                                <!-- User Profile Search -->
                                   <div class = "well">
                                    <label for="userProfileSearcher">
                                        <strong>Search:</strong>
                                    </label>

                                    <div class="input-append">
                                        <input class="input-medium"
                                               id="userProfileSearcher"
                                               data-ng-model="q"
                                               type="text"
                                               placeholder="Search User Profiles"
                                               data-ng-keyup="userProfileSearch()"
                                               data-ng-pattern="/[A-Za-z0-9]/"/>
                                        <button type="button" data-ng-click="clearUserProfileSearch()" class="btn">
                                            <i class="icon-minus-sign"></i>
                                        </button>
                                    </div>

                                <ul
                                    data-ng-repeat="userProfile in searchResults | filter:filterUserProfileResults"
                                    class="nav nav-list"
                                    style="margin-top:5px;">
                                    <li class="searchResultItem">
                                        <div style="padding: 5px; border: #B0C4DE solid;">
                                            {{userProfile.lastName}}
                                            <br>
                                            {{userProfile.firstName}}
                                            <br>
                                            {{userProfile.primaryEmailAddress}}
                                            <br>
                                            <button type="button"
                                                    class="btn btn-primary btn-mini"
                                                    data-ng-click = "saveCompanyProfileContact(userProfile, $index)">
                                                Add Contact <i class="icon-arrow-right icon-white"></i>
                                            </button>
                                        </div>
                                    </li>
                                </ul>

                            </div>

                                </div>


                                <div class="span3">
                                    <!-- Current Company Profile Contacts -->
                                    <div class="well">
                                        <strong>Current Company Contacts:</strong>

                                        <ul class="unstyled"
                                            data-ng-repeat = "companyProfileContact in companyProfile.companyProfileContacts">
                                            <div data-ng-controller="editCompanyProfileContactController">
                                                <li>
                                                    <span>
                                                        <button class="btn btn-danger btn-mini"
                                                                type="button"
                                                                data-ng-disabled="disableDeleteButton"
                                                                data-ng-click="deleteCompanyProfileContact(companyProfileContact, $index)">
                                                            <i class="icon-remove"></i>
                                                        </button>
                                                        {{ companyProfileContact.userProfile.firstName }}
                                                        {{ companyProfileContact.userProfile.lastName }}
                                                        </span>
                                                </li>
                                            </div>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
    <div id="CompanyProfilePhoneNumbers" class="form-horizontal">
        <fieldset class="embedded">
            <legend>
                <g:message code="companyProfile.companyProfilePhoneNumbers.label"/>
            </legend>
            <div class="control-group fieldcontain">
                <label class="control-label">
                    <g:message code="companyProfile.companyProfilePhoneNumbers.label" />
                </label>
                <div class="controls">
                    <div data-ng-controller = "addCompanyProfilePhoneNumberController" >
                        <div data-ng-form = "addCompanyProfilePhoneNumberForm" class="form-inline">
                            <label for="label">
                                <g:message code="companyProfilePhoneNumber.label.label"/>:
                            </label>
                            <input type = "text"
                                   name="label"
                                   id="label"
                                   data-ng-model="companyProfilePhoneNumber.label"
                                   class="input-mini"/>
                            <label for="label">
                                <g:message code="companyProfilePhoneNumber.phoneNumber.label"/>:
                            </label>
                            <input type = "text"
                                   name="phoneNumber"
                                   id="phoneNumber"
                                   data-ng-model="companyProfilePhoneNumber.phoneNumber"
                                   class="input-small"
                                   required="required"/>
                            <label class="checkbox">
                                <input type="checkbox"
                                       name="primaryPhoneNumber"
                                       id="primaryPhoneNumber"
                                       data-ng-model="companyProfilePhoneNumber.primaryPhoneNumber"
                                       />
                                <g:message code="companyProfileNumber.primary.label"/>
                            </label>
                            <button type="button"
                                    class="btn btn-info"
                                    data-ng-click="saveCompanyProfilePhoneNumber(companyProfilePhoneNumber)"
                                    data-ng-disabled="!canSave(addCompanyProfilePhoneNumberForm) || !formActionsClickable">
                                <i class="icon-plus"/>
                                Add
                            </button>
                        </div>
                    </div>
                    <div style="margin-top:5px;">
                    <strong>
                        <g:message code="companyProfilePhoneNumber.list.label"/>
                    </strong>
                        <ul class = "unstyled" data-ng-repeat = "companyProfilePhoneNumber in companyProfile.companyProfilePhoneNumbers">
                            <li>
                                <address data-ng-controller="editCompanyProfilePhoneNumberController">
                                    <button type="button"
                                            class="btn btn-danger btn-mini"
                                            data-ng-click="deleteCompanyProfilePhoneNumber(companyProfilePhoneNumber)"
                                            data-ng-disabled="!formActionsClickable">
                                        <i class="icon-remove"/>
                                    </button>
                                    <abbr data-ng-show="companyProfilePhoneNumber.label" title="{{ companyProfilePhoneNumber.label }}"> ({{companyProfilePhoneNumber.label}}):</abbr>
                                    {{ companyProfilePhoneNumber.phoneNumber }}
                                    <span class="label label-info" data-ng-show="companyProfilePhoneNumber.primaryPhoneNumber">
                                        {{ companyProfilePhoneNumber.primaryPhoneNumber ? 'Primary' : '' }}
                                    </span>
                                </address>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </fieldset>
    </div>
    <!-- END: Company Profile Phone Numbers -->

    </fieldset>
</form>
<div>
    <div modal="addAddressModalOpen" close="closeAddModal()" options="addModalOpts">

        <div class="modal-header">
            <h3><g:message code="companyProfile.companyProfileAddresses.label" /></h3>
        </div>
        <div class="modal-body">

            <form class="form-horizontal"
                  name="companyProfileAddressForm" data-ng-submit="saveCompanyProfileAddress(companyProfileAddress)">

                <fieldset>
                    %{--render the shared addressFormFields--}%
                    <g:render template="/companyProfile/companyProfileAddressForm"/>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-success "
                                data-ng-disabled="!canSave(companyProfileAddressForm)">

                            <g:message code="default.button.add.label"/>
                        </button>

                        <button type="button" class="btn "
                                data-ng-click="closeAddModal()">
                            <g:message code="default.button.cancel.label"/>
                        </button>
                    </div>
                </fieldset>
            </form>


        </div>

        <div class="modal-footer">
            <button class="btn btn-warning cancel" data-ng-click="closeAddModal()">Close</button>
        </div>
    </div>
</div>


<address class="inner-list-address" data-ng-hide="editingCompanyProfileAddress" data-ng-click="editCompanyProfileAddress()">
    <span data-ng-show="companyProfileAddress.address.addressOne">
        <strong>{{ companyProfileAddress.address.addressOne }}</strong>
        <span class="label label-info" data-ng-show="companyProfileAddress.primaryAddress">
            <g:message code="label.primary"/>
        </span>
    </span>

    <span data-ng-show="companyProfileAddress.address.addressTwo">
        <br> {{ companyProfileAddress.address.addressTwo }}
    </span>

    <span data-ng-show="companyProfileAddress.address.addressThree ">
        <br> {{ companyProfileAddress.address.addressThree }}
    </span>

    <span data-ng-show="companyProfileAddress.address.state">
        <br>{{ companyProfileAddress.address.city }} {{companyProfileAddress.address.state}}, {{ companyProfileAddress.address.zipcode }} {{ companyProfileAddress.address.country | uppercase  }}
    </span>

</address>
<section id="list-registrationApproval" class="first" data-ng-app="registrationApprovalApp" data-base-url="${createLink(uri: 'registrationApproval')}">
    <input type="text" data-ng-model="filterOptions.filterText" placeholder="Search..." class="search-query"/>

    <div class="gridStyle" data-ng-grid="gridOptions"></div>
</section>
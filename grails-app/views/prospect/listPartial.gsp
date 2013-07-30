<section id="list-suspect" class="first" data-ng-app="suspectApp" data-base-url="${createLink(uri: 'suspect')}">
    <input type="text" data-ng-model="filterOptions.filterText" placeholder="Search..." class="search-query"/>

    <div class="gridStyle ng-cloak" data-ng-grid="gridOptions"></div>
</section>
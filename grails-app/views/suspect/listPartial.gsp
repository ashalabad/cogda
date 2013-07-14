<section id="list-suspect" class="first" data-ng-app="suspectApp" data-base-url="${createLink(uri: 'suspect')}">
    <div class="row">
        <div class="span12">
            <input type="text" data-ng-model="filterOptions.filterText" placeholder="Search..." class="search-query"/>

            <div class="gridStyle" data-ng-grid="gridOptions"></div>
        </div>
    </div>
</section>



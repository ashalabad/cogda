<div class="row">
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.targetDate">
            {{ leadLineOfBusiness.targetDate |date:mediumShort }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.expirationDate">
            {{ leadLineOfBusiness.expirationDate }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.targetPremium">
            {{ leadLineOfBusiness.targetPremium }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.targetCommission">
            {{ leadLineOfBusiness.targetCommission }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.writingCompany.companyName">
            {{ leadLineOfBusiness.writingCompany.companyName }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.billingCompany.companyName">
            {{ leadLineOfBusiness.billingCompany.companyName }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.renewal">
            {{ leadLineOfBusiness.renewal }}
        </span>
    </div>
    <div class="span3">
        <span data-ng-show="leadLineOfBusiness.remarket">
            {{ leadLineOfBusiness.remarket }}
        </span>
    </div>
</div>
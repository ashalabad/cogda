<td data-title="${message(code: 'lineOfBusiness.category.label')}">
    {{ leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.description }}
</td>

<td data-title="${message(code: 'lineOfBusiness.label')}">
    {{ leadLineOfBusiness.lineOfBusiness.description }}
</td>

<td data-title="${message(code: 'leadLineOfBusiness.targetDate.label')}">
    {{ leadLineOfBusiness.targetDate | bsDateFilter | date: shortDate }}
</td>

<td data-title="${message(code: 'leadLineOfBusiness.expirationDate.label')}">
    {{ leadLineOfBusiness.expirationDate | bsDateFilter | date: shortDate }}
</td>

<td data-title="${message(code: 'leadLineOfBusiness.targetPremium.label')}">
    {{ leadLineOfBusiness.targetPremium | currency }}
</td>

<td data-title="${message(code: 'leadLineOfBusiness.targetCommission.label')}">
    {{ leadLineOfBusiness.targetCommission | currency }}
</td>
<td data-title="${message(code: 'leadLineOfBusiness.commissionRate.label')}">
    {{ leadLineOfBusiness.commissionRate }}
</td>
<td data-title="${message(code: 'leadLineOfBusiness.currentCarrier.label')}">
    {{ leadLineOfBusiness.currentCarrier }}
</td>
<td data-title="${message(code: 'leadLineOfBusiness.remarket.label')}">
    {{ leadLineOfBusiness.remarket }}
</td>

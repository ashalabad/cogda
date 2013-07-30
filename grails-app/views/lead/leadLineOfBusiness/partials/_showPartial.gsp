<td>
    {{ leadLineOfBusiness.lineOfBusiness.lineOfBusinessCategory.description }}
</td>

<td>
    {{ leadLineOfBusiness.lineOfBusiness.description }}
</td>

<td>
    {{ leadLineOfBusiness.targetDate | bsDateFilter | date: shortDate }}
</td>

<td>
    {{ leadLineOfBusiness.expirationDate | bsDateFilter | date: shortDate }}
</td>

<td>
    {{ leadLineOfBusiness.targetPremium | currency }}
</td>

<td>
    {{ leadLineOfBusiness.targetCommission | currency }}
</td>
<td>
    {{ leadLineOfBusiness.commissionRate }}
</td>
<td>
    {{ leadLineOfBusiness.currentCarrier }}
</td>
<td>
    {{ leadLineOfBusiness.remarket }}
</td>

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
    {{ leadLineOfBusiness.targetPremium }}
</td>

<td>
    {{ leadLineOfBusiness.targetCommission }}
</td>
<td>
    {{ leadLineOfBusiness.writingCompany }}
</td>
<td>
    {{ leadLineOfBusiness.billingCompany }}
</td>
<td>
    {{ leadLineOfBusiness.renewal }}
</td>
<td>
    {{ leadLineOfBusiness.remarket }}
</td>

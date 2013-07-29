<li class="inline">

    <label class="checkbox">
        <input type="checkbox" name="{{accountContactLinkInner.linkId}}" data-ng-click="toggleAccountContactLink(accountContactLinkInner.linkId,this.name)"/>
        <strong>
            <span class="blueText">
                %{--<a data-ng-href="/account#/accountContact/{{accountContactLinkInner.accountContactId}}" target="_blank">{{accountContactLinkInner.accountContactName}}</a> |--}%
                {{accountContactLinkInner.accountContactName}}
                <span data-ng-hide="accountContactLinkInner.accountContactUserProfile"> | {{accountContactLinkInner.accountContactEmail}}</span>

            </span>
        </strong>
        <span data-ng-show="accountContactLinkInner.accountContactUserProfile">
            <img tooltip-trigger="hover" tooltip="Cogda User" height="20" width="20" src="${resource(dir:'images',file:'cogdaG.png')}" />
        </span>
        <span data-ng-show="accountContactLinkInner.accountContactPrimary" class="label label-success"><i class="icon-asterisk"/></span>
        <span data-ng-show="accountContactLinkInner.accountContactFavorite" class="label label-warning"><i class="icon-star"></i></span>
    </label>
</li>
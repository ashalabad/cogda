<li class="inline">

    <label class="checkbox">
        <input type="checkbox" data-ng-model="markets[accountContactLinkInner.linkId]" />
        <strong>
            <span class="blueText">
                {{accountContactLinkInner.accountContactName}}
                <span data-ng-hide="accountContactLinkInner.accountContactUserProfile"> | {{accountContactLinkInner.accountContactEmail}}</span>
            </span>
        </strong>
        <span data-ng-show="accountContactLinkInner.accountContactUserProfile">
            <img tooltip-trigger="hover" tooltip-placement="right" tooltip="Cogda User" height="20" width="20" src="${resource(dir:'images',file:'cogdaG.png')}" />
        </span>
        <span data-ng-show="accountContactLinkInner.accountContactPrimary" class="label label-success"><i tooltip-trigger="hover" tooltip-placement="right" tooltip="Primary" class="icon-asterisk"/></span>
        <span data-ng-show="accountContactLinkInner.accountContactFavorite" class="label label-warning"><i tooltip-trigger="hover" tooltip-placement="right" tooltip="Favorite" class="icon-star"></i></span>
    </label>
</li>
<mt:hasTenant>
    <ul class="nav pull-right">

    <sec:ifNotLoggedIn>
        <li class="">
            <a href="${createLink(controller:"login", action:"index")}" >
                <i class="icon-user"></i>
                <g:message code="security.signin.label"/>
            </a>
        </li>
    </sec:ifNotLoggedIn>
    <sec:ifLoggedIn>
        <li class="dropdown dropdown-btn">
            <a class="dropdown-toggle" role="button" data-toggle="dropdown" data-target="#" href="#">
                <i class="icon-user icon-large icon-white"></i>
                <sec:username></sec:username>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu" role="menu">
                <li class=""><a href="${createLink(controller:"userProfileManager")}">
                    <i class="icon-user"></i>
                    <g:message code="userProfile.edit.label"/>
                </a></li>
                <li class=""><a href="${createLink(controller:"userSettings", action:"edit")}">
                    <i class="icon-cogs"></i>
                    <g:message code="user.settings.change.label"/>
                </a></li>

                <li class="divider"></li>
                <li class=""><a href="${createLink(controller:"logout")}">
                    <i class="icon-off"></i>
                    <g:message code="security.signoff.label"/>
                </a></li>
            </ul>
    </sec:ifLoggedIn>
        </li>
    </ul>

</mt:hasTenant>
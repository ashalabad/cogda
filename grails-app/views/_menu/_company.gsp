<sec:ifAllGranted roles = "ROLE_ADMINISTRATOR">
    <ul class="nav pull-right">
        <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="icon-wrench"></i>
                <g:message code="default.company.label"/><b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <li class="">
                    <a tabindex="-1" href="#"><b><g:message code="default.admin.hostAdmin.label"></g:message></b></a>
                </li>
                <li class="">
                    <a href="/companyProfile">
                        <i class="icon-edit"></i>
                        <g:message code = "companyProfile.menu.label"/>
                    </a>
                </li>
            </ul>
        </li>
    </ul>
</sec:ifAllGranted>
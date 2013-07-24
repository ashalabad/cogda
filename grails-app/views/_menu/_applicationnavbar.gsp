
<%--This is the Application Menu that is used by the Users to navigate throughout the system --%>
<div id="applicationNavBar" class="navbar ">
    <div class="navbar-inner" style="">
        <div class="container">
            <!-- .btn-navbar is used as the toggle for collapsed navbar content -->

            <div class="nav-collapse">

                <div class="pull-left">
                    <%--Left-side entries--%>

                    <ul class="nav">
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="/contact" data-target="#">
                                <i class="icon-user"></i>
                                <g:message code="contact.menu.label"/>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="">
                                    <a href="/contact">
                                        <i class="icon-list"></i>
                                        <g:message code = "contact.menu.list.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="#" onclick="newContact()">
                                        <i class="icon-plus-sign"></i>
                                        <g:message code = "contact.menu.create.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/contact/importContacts">
                                        <i class="icon-file"></i>
                                        <g:message code = "contact.menu.importcontacts.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/contact/inviteContacts">
                                        <i class="icon-envelope"></i>
                                        <g:message code = "contact.menu.invitecontacts.label"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="/account" data-target="#">
                                <i class="icon-user"></i>
                                <g:message code="account.menu.label"/>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="">
                                    <a href="/account">
                                        <i class="icon-folder-open"></i>
                                        <g:message code = "account.menu.list.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/account#/create" id="applicationNavBarAccountCreate">
                                        <i class="icon-plus-sign"></i>
                                        <g:message code = "account.menu.create.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/account/importAccounts">
                                        <i class="icon-file"></i>
                                        <g:message code = "account.menu.importaccounts.label"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="/suspect" data-target="#">
                                <i class="icon-user"></i>
                                <g:message code="suspect.menu.label"/>
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="">
                                    <a href="/suspect">
                                        <i class="icon-folder-open"></i>
                                        <g:message code = "suspect.menu.list.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/suspect#/create">
                                        <i class="icon-plus-sign"></i>
                                        <g:message code = "suspect.menu.create.label"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="#">
                                        <i class="icon-file"></i>
                                        <g:message code = "suspect.menu.importsuspects.label"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="/prospect" data-target="#">
                            <i class="icon-user"></i>
                            <g:message code="prospect.menu.label"/>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="">
                                <a href="/prospect">
                                    <i class="icon-folder-open"></i>
                                    <g:message code = "prospect.menu.list.label"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="/prospect#/create">
                                    <i class="icon-plus-sign"></i>
                                    <g:message code = "prospect.menu.create.label"/>
                                </a>
                            </li>
                            <li class="">
                                <a href="#">
                                    <i class="icon-file"></i>
                                    <g:message code = "prospect.menu.importprospects.label"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    </ul>

                </div>

                <div class="pull-right">
                    <%--Right-side Admin entries--%>
                    <g:if test="${!layout_noadminnavbar}">
                        <mt:hasTenant>
                            <sec:ifAllGranted roles = "ROLE_ADMINISTRATOR">
                                <g:render template="/_menu/adminnavbar"/>
                            </sec:ifAllGranted>
                        </mt:hasTenant>
                    </g:if>
                </div>

            </div>

        </div>
    </div>
</div>

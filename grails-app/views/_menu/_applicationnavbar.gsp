
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
                                    <a href="/contact/create">
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
                    </ul>

                </div>

                <div class="pull-right">
                    <%--Right-side entries--%>

                </div>

            </div>

        </div>
    </div>
</div>

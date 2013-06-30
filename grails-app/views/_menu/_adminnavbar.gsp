<%--This is the Application Menu that is used by the Administrators to navigate throughout the admin system --%>
<div id="applicationNavBar" class="navbar ">
    <div class="navbar-inner" style="">
        <div class="container">

            <div class="nav-collapse">

                <div class="pull-left">

                    <ul class="nav">
                        <%--User Entries--%>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <i class="icon-user"></i>
                                <g:message code="admin.menu.user.label"/><b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="">
                                    <a href="/user">
                                        <i class="icon-user"></i>
                                        <g:message code = "admin.menu.user"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/pendingUser">
                                        <i class="icon-user"></i>
                                        <g:message code = "admin.menu.pendingUser"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/completedUser">
                                        <i class="icon-user"></i>
                                        <g:message code = "admin.menu.completedUser"/>
                                    </a>
                                </li>
                                <li class="">
                                    <a href="/userImport">
                                        <i class="icon-file"></i>
                                        <g:message code = "userImport.menu.label"/>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

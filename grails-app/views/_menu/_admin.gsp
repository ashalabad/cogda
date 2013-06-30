<ul class="nav pull-right">
	<li class="dropdown">
		<a class="dropdown-toggle" data-toggle="dropdown" href="#">
    		<i class="icon-wrench"></i>
			<g:message code="default.admin.label"/><b class="caret"></b>
		</a>
		<ul class="dropdown-menu">
			<li class="">
				<a tabindex="-1" href="#"><b><g:message code="default.admin.hostAdmin.label"></g:message></b></a>
			</li>
            <li class="">
                <a href="/userImport/index">
                    <i class="icon-file"></i>
                    <g:message code = "userImport.menu.label"/>
                </a>
            </li>
            <li class="">
                <a href="/pendingUser">
                    <i class="icon-user"></i>
                    <g:message code = "pendingUser.menu.label"/>
                </a>
            </li>
			<li class="">
				<a href="${createLink(uri: '/systeminfo')}">
					<i class="icon-info-sign"></i>
					<g:message code="default.systeminfo.label"/>
				</a>
			</li>

            <g:if env="development"></g:if>
			<li class="dropdown-submenu">
				<a tabindex="-1" href="#">
					<i class="icon-sitemap"></i>
					All Controller
				</a>
				<ul class="dropdown-menu">
					<li class="controller"><g:link controller="home">HomeController</g:link></li>
					<li class="divider"></li>
					<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.logicalPropertyName } }">
						<g:if test="${c.logicalPropertyName != 'home'}">
							<li class="controller"><g:link controller="${c.logicalPropertyName}">${c?.fullName?.substring(c?.fullName?.lastIndexOf('.')+1)}</g:link></li>
						</g:if>
					</g:each>
				</ul>
			</li>
		</ul>
	</li>
</ul>

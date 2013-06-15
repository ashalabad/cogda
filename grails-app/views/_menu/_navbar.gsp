<div id="Navbar" class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<!-- .btn-navbar is used as the toggle for collapsed navbar content -->
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
			</a>

			<a class="brand" href="${createLink(uri: '/')}">
				<img class="logo" src="${resource(dir:'images',file:'cogdaG.png')}" alt="${meta(name:'app.name')}" />
				${meta(name:'app.name')}
				<small> v${meta(name:'app.version')}</small>
			</a>

       		<div class="nav-collapse">
       		


	  			<div class="pull-left">
					<%--Left-side entries--%>
	  			</div>

	  			<div class="pull-right">
					<%--Right-side entries--%>
					<%--NOTE: the following menus are in reverse order due to "pull-right" alignment (i.e., right to left)--%>
<%--					<g:render template="/_menu/language"/> --%>
					<g:render template="/_menu/info"/>
					<g:render template="/_menu/user"/><!-- NOTE: the renderDialog for the "Register" modal dialog MUST be placed outside the NavBar (at least for Bootstrap 2.1.1): see bottom of main.gsp -->
                    <sec:ifLoggedIn>
					    <g:render template="/_menu/admin"/>
                    </sec:ifLoggedIn>
<%-- 					<g:render template="/_menu/search"/> --%>
	  			</div>

			</div>
			
		</div>
	</div>
</div>

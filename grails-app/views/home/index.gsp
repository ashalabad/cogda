<%@ page import="com.cogda.multitenant.CustomerAccount;" %>
<html>

<head>
	<title><g:message code="default.tenant.welcome.title" args="[customerAccountInstance?.subDomain]"/> </title>
	<meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
</head>

<body>

	<section id="intro" class="first">
		<h1>${companyInstance?.companyName?.encodeAsHTML()}</h1>
		<p>
            <ul>
               <li>
                   <g:link controller="login" action="index">
                       Login
                   </g:link>
               </li>
               <li>
                   <g:link controller="userRegistration" action="index">
                       Register
                   </g:link>
               </li>
                <li>
                    <g:link controller="password" action="index">
                        Forgot Password
                    </g:link>
                </li>
            </ul>
		</p>
		<h2>Introduction</h2>
		<p>
			Kickstart is an extension for Grails in order to start your
			project with a good looking frontend. It is intended to be used in
			rapid application scenarios such as a Startup Weekend or a
			prototyping session with a customer. This plugin provides adapted
			scaffolding templates for standard CRUD pages using the Bootstrap web
			page template by Twitter. Additionally, Kickstart includes some
			general GSPs pages (e.g., about.gsp), a minimal logging/orientation
			Filter, and a language switcher.
		</p>
		<g:link class="btn btn-large btn-primary" controller="nextSteps">Next Steps</g:link>
	</section>

	<section id="info">
		<div class="row-fluid">
	    	<div class="span4">
		    	<div class="center">
					<img class="frontpageImage" src="${resource(dir: 'images/frontpage',file: 'bs-docs-twitter-github.png')}" />
					<h3>Bootstrap 2.3.0</h3>
				</div>
				<p>
                    Information about Bootstrap
				</p>
			</div>
	    	<div class="span4">
		    	<div class="center">
					<i class="icon-flag icon-7x" style="font-size: 7em;"></i>
					<h3>Font Awesome 3.0.2</h3>
				</div>
				<p>
                   Information about Font Awesome
				</p>
			</div>
	    	<div class="span4">
		    	<div class="center">
					<img class="frontpageImage" src="${resource(plugin: 'kickstart-with-bootstrap', dir: 'images/frontpage',file: 'html5css3js8.png')}"/>
					<h3>Tech Foundation</h3>
				</div>
				<p>
                Bootstrap is based on elements of HTML 5, CSS 3, Javascript 1.8, and jQuery 1.7 with progressively enhanced
				components to enable a responsive design of the website.
				</p>
			</div>
	    </div>
	</section>

	<section id="info2">
		<div class="row-fluid">
	    	<div class="span4">
		    	<div class="center">
					<img class="" src="${resource(plugin: 'kickstart-with-bootstrap', dir: 'images/frontpage',file: 'Datepicker.png')}" />
					<h3>Datepicker</h3>
				</div>
				<p><a href ="https://github.com/eternicode/bootstrap-datepicker">Datepicker for Bootstrap</a> by Stefan Petre and Andrew Rowls</p>
			</div>
	    	<div class="span4">
		    	<div class="center">
					<img class="" src="${resource(plugin: 'kickstart-with-bootstrap', dir: 'images/frontpage',file: 'flags_preview_large.png')}" />
					<h3>Language Selector</h3>
				</div>
				<p>Language selector that uses the <a href="http://www.famfamfam.com/lab/icons/flags/">FamFamFam flag icons</a> by Mark James.
				</p>
			</div>
	    	<div class="span4">
		    	<div class="center">
					<img class="frontpageImage" src="${resource(plugin: 'kickstart-with-bootstrap', dir: 'images/frontpage',file: 'browser_logos.png')}" />
					<h3>Browser support</h3>
				</div>
				<p>Bootstrap is tested and supported in major modern browsers like Chrome 14, Safari 5+, Opera 11, Internet Explorer 7, and Firefox 5.</p>
			</div>
	    </div>
	</section>


</body>

</html>

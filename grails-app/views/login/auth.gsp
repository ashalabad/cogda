
<html>
<head>
    <title><g:message code="spring.security.login.title" args="${mt.rootCompanyName()}"/></title>
    <meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu" value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu" value="${true}" scope="request"/>
</head>

<body>
<br>
<section id="login">
    <div class="row">
        <div class="span3"></div>
        <div class="span7 well well-large">
            <h3> <g:message code="springSecurity.login.header"/> </h3>
            <form id='loginForm' class='form-horizontal' action='${postUrl}' method='POST' autocomplete='off'>
                <fieldset class="form">
                    <div class="control-group fieldcontain ${hasErrors(bean: _DemoPageInstance, field: 'name', 'error')} ">
                        <label for='username' class="control-label"><g:message code="springSecurity.login.username.label"/>:</label>
                        <div class="controls">
                            <input type='text' class='span4' name='j_username' id='username'/>
                        </div>
                    </div>
                    <div class="control-group fieldcontain ${hasErrors(bean: _DemoPageInstance, field: 'name', 'error')} ">
                        <label for='password' class="control-label"><g:message code="springSecurity.login.password.label"/>:</label>
                        <div class="controls">
                            <input type='password' class='span4' name='j_password' id='password'/>
                        </div>
                    </div>

                </fieldset>
                <div class="controls">
                    <input type='submit' id="submit" class="btn btn-success" value='${message(code: "springSecurity.login.button")}'/>
                    <g:link controller="password" action="index" class="btn pull-right"><i class="icon-question-sign"></i> Forgot Password</g:link>
                </div>
            </form>
        </div>
        <div class="span3"></div>
    </div>
</section>

<script type='text/javascript'>
    <!--
    (function() {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>

</body>
</html>


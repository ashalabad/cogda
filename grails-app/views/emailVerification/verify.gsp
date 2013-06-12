<html>
    <head>
        <title><g:message code="emailVerification.title"/></title>
        <meta name="layout" content="kickstart" />
        <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
        <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
        <g:set var="layout_noflashmessage"	value="${true}" scope="request"/>
    </head>

    <body>
        <section id="emailVerificationSection" class="">
                <div class="container">
                    <g:if test="${flash.message}">
                        <div class="alert alert-info">
                            ${flash.message}
                        </div>
                    </g:if>

                    <g:if test="${flash.error}">
                        <div class="alert alert-error">
                            ${flash.error}
                        </div>
                    </g:if>

                </div>
        </section>
    </body>
</html>
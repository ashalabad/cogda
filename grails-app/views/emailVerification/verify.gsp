<html>
    <head>
        <title><g:message code="emailVerification.title"/></title>
        <meta name="layout" content="kickstart" />
        <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
        <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    </head>

    <body>
        <content tag="header">
            <!-- Empty Header -->
        </content>

        <section id="Error" class="">
            <div class="big-message">
                <div class="container">
                    <h1>
                        <g:if test="${flash.message}">
                            <div class="alert-info">
                                ${flash.message}
                            </div>
                        </g:if>

                        <g:if test="${flash.error}">
                            <div class="alert-error">
                                ${flash.error}
                            </div>
                        </g:if>
                    </h1>
                </div>
            </div>
        </section>
    </body>
</html>
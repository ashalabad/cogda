<html>
<head>
    <title><g:message code="userImport.title"/></title>
    <meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu"		value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	value="${true}" scope="request"/>
    <g:set var="layout_noflashmessage"	value="${true}" scope="request"/>
</head>

<body>

<content tag="header">
    <!-- Empty Header -->
</content>

<section id="userImportSection" class="first">

    <g:if test = "${flash.error}">
        <div class="alert alert-error" id="errorMessages" >
            ${flash.error}
        </div>
    </g:if>

    <g:if test = "${flash.message}">
        <div class="alert alert-success" id="messages">
            ${flash.message}
        </div>
    </g:if>

    <g:hasErrors bean="${command}">
        <div class="alert alert-error">
            <g:renderErrors bean="${command}" as="list" />
        </div>
    </g:hasErrors>

    <div id="informationDiv" class="big-message">
        <div class="container">

            <h2><g:message code="userImport.title" default="User Import"/></h2>

                <div class = "alert alert-info">
                    <g:message code = "userImport.import.instructions"/>
                </div>
        </div>
    </div>

    <div class = "row">
        <div class="span3 bs-docs-sidebar" id="navbarDiv">
            <ul class="nav nav-list bs-docs-sidenav affix">
                <li>
                    <a href="#importUserFileHeading">
                        <i class="icon-chevron-right"></i>
                        <g:message code="userImport.title"/>
                    </a>
                    <a href="#importFileInstructions">
                        <i class="icon-chevron-right"></i>
                        <g:message code="userImport.instructions"/>
                    </a>

                </li>
            </ul>
        </div>
        <div class="span9">

            <section id="importUserFileHeading" class = "sectionSnip">
                <h3>
                    <g:message code="userImport.title"/>
                </h3>
                <g:uploadForm class = "form-horizontal" name="userImportForm" autocomplete="off" novalidate="novalidate"
                              action="importUserFile" controller="userImport">

                    <fieldset class="form">

                        <div class="control-group fieldcontain ">
                            <label for="firstRowContainsHeadings" class="control-label">
                                <g:message code="userImport.firstRowContainsHeadings.label" default="First Row Has Headings" />
                            </label>
                            <div class="controls">
                                <bs:checkBox name="myBoolean" value="${params.firstRowContainsHeadings}" />
                                <span class="help-inline"></span>
                            </div>
                        </div>

                        <div class="control-group fieldcontain  required">
                            <label for="file" class="control-label">
                                <g:message code="userImport.importFile.label" default="User File" /> <span class="required-indicator">*</span> </label>
                            <div class="controls">
                                <input type = "file" name="file" id="file" required=""/>
                                <span class="help-inline"></span>
                            </div>
                        </div>
                        <div class="form-actions">
                            <g:submitButton name="userImportButton" class="btn btn-primary" value="${message(code: 'userImport.import.button', default: 'Import')}" />
                        </div>
                    </fieldset>
                </g:uploadForm>
            </section>


            <section id="importFileInstructions" class = "sectionSnip">
                <div class="pretty">
                    <h3>
                        <g:message code="userImport.title.fileLayout"/>
                    </h3>
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th>
                                *
                                <a class = "popoverLink"
                                   data-toggle="popover"
                                   data-placement="top"
                                   data-trigger="hover"
                                   data-content="<g:message code="userImport.username.instructions"/>"
                                   title=""
                                   data-original-title="<g:message code="userImport.username.label"/>">
                                    <g:message code="registration.username.label"/>
                                </a>
                            </th>
                            <th>
                                *
                                <a class = "popoverLink"
                                   data-toggle="popover"
                                   data-placement="top"
                                   data-trigger="hover"
                                   data-content="<g:message code="userImport.firstName.instructions"/>"
                                   title=""
                                   data-original-title="<g:message code="userImport.firstName.label"/>">
                                    <g:message code="registration.firstName.label"/>
                                </a>

                            </th>
                            <th>
                                *
                                <a href = "#"
                                   class = "popoverLink"
                                   data-toggle="popover"
                                   data-placement="top"
                                   data-trigger="hover"
                                   data-content="<g:message code="userImport.lastName.instructions"/>"
                                   title=""
                                   data-original-title="<g:message code="userImport.lastName.label"/>">
                                    <g:message code="registration.lastName.label"/>


                                </a>
                            </th>
                            <th>
                                *
                                <a href = "#"
                                   class = "popoverLink"
                                   data-toggle="popover"
                                   data-placement="top"
                                   data-trigger="hover"
                                   data-content="<g:message code="userImport.emailAddress.instructions"/>"
                                   title=""
                                   data-original-title="<g:message code="userImport.emailAddress.label"/>">
                                    <g:message code="registration.emailAddress.label"/>
                                </a>
                            </th>
                            <th>
                                <a href = "#"
                                   class = "popoverLink"
                                   data-toggle="popover"
                                   data-placement="top"
                                   data-trigger="hover"
                                   data-content="<g:message code="userImport.securityRoles.instructions"/>"
                                   title=""
                                   data-original-title="<g:message code="userImport.securityRoles.label"/>">
                                    <g:message code="userImport.securityRoles.field.label"/>
                                </a>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>oscarcogda</td>
                            <td>Oscar</td>
                            <td>Mighty</td>
                            <td>oscarmighty@cogda.com</td>
                            <td>
                                "<g:each in = "${assignableRoleInstances.take(2)}" status="i" var="role"><g:if test = "${i != 0}">,</g:if>${role.authority}</g:each>"
                            </td>
                        </tr>
                        <tr>
                            <td>janecogda</td>
                            <td>Jane</td>
                            <td>Jaynes</td>
                            <td>janejaynes@codga.com</td>
                            <td>
                                <g:each in = "${assignableRoleInstances.take(1)}" status="i" var="role">
                                    <g:if test = "${i != 0}">
                                        ,
                                    </g:if>
                                    ${role.authority}
                                </g:each>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <br/>

                <div class="pretty">
                    <h4>
                        <g:message code = "userImport.file.snippet.title"></g:message>
                    </h4>
                    <div class = "well">
                        oscarcogda,Oscar,Mighty,oscarmighty@cogda.com,"ROLE_BRANCH_MANAGER,ROLE_COMPANY_MANAGER"
                        janecogda,Jane,Jaynes,janejaynes@codga.com,ROLE_BRANCH_MANAGER
                    </div>
                </div>

                <div class="pretty">
                    <h4>
                        <g:message code = "userImport.file.example.download"/>

                    </h4>

                    <img class="" src="${resource(dir: 'images/kudos', file: 'DownloadCrate.png')}" title="${message(code: "userImport.file.example.download")}"/>
                    <a href = "https://s3-us-west-2.amazonaws.com/cogda-production/cogdaweb/documents/CogdaUserImportFileExample.csv">
                        <g:message code = "userImport.file.example.label"/>
                    </a>


                </div>

                <br/>

                <div class="pretty">
                    <h4>
                        <g:message code = "userImport.availableRoles.title"/>

                    </h4>

                    <dl>
                    <g:each in = "${assignableRoleInstances}" status="i" var="role">
                        <dt>${role.authority}</dt>
                        <dd>${role.description}</dd>
                    </g:each>

                    </dl>
                </div>
            </section>
        </div>
    </div>



</section>

<script>

    $(document).ready(function() {
        $('.popoverLink').popover();

        $('.popoverLink').click(function(e) {e.preventDefault()});

        var $window = $(window);
        // side bar
        $('.bs-docs-sidenav').affix({
            offset: {
                top: function () { return $window.width() <= 980 ? 290 : 210 }
                , bottom: 270
            }
        });
    });

</script>

</body>
</html>
<html>
<head>
    <title><g:message code="userImport.title"/></title>
    <meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu"		    value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu"	    value="${true}" scope="request"/>
    <g:set var="layout_noflashmessage"	    value="${true}" scope="request"/>
    <r:require module="jqueryFileUpload"></r:require>
    <script src="${resource(dir:"js/userImport", file:'index.js')}" type="text/javascript" ></script>
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
        <div class="alert alert-success" id="messages" >
            ${flash.message}
        </div>
    </g:if>

    <h2><g:message code="userImport.title" default="User Import"/></h2>

    <div class="alert alert-error" style="display: none;" id="errorMessages"></div>

    <div class="alert alert-success" style="display: none;" id="messages"></div>



    <ul class="nav nav-pills" id = "fileImportTab">
        <li class="active" id="stepOneItem">
            <a href="#stepOne" data-toggle="pill">Step 1: Import Instructions &raquo;</a>
        </li>
        <li class="" id="stepTwoItem">
            <a href="#stepTwo" data-toggle="pill">Step 2: Upload CSV &raquo;</a>
        </li>
        <li class="disabled" id="stepThreeItem">
            <a href="#stepThree" data-toggle="pill">Step 3: Import Validation </a>
        </li>
    </ul>


        <div class="tab-content">
            <div class="tab-pane active" id="stepOne">
                <g:render template="userImportInstructions"/>
            </div>
            <div class="tab-pane" id="stepTwo">
                <div id="stepTwoContent">

                    <!-- The fileinput-button span is used to style the file input field as button -->
                    <span class="btn btn-success fileinput-button" id="fileInputButtonSpan">
                        <i class="icon-plus icon-white"></i>
                        <span>Please Select Your Import File</span>
                        <!-- The file input field used as target for the file upload widget -->
                        <input id="fileupload" type="file" name="file">
                    </span>

                    <!-- The global progress bar -->
                    <div id="progress" class="progress progress-success progress-striped" style="display:none;">
                        <div class="bar"></div>
                    </div>
                    <!-- The container for the uploaded files -->
                    <div id="files" class="files"></div>

                </div>
            </div>
            <div class="tab-pane" id="stepThree">

                <p>
                    Please review the imported user data below and then choose one of the following actions.
                </p>

                <p>
                    Items marked with an <span class="badge badge-important"><i class="icon-remove"></i></span> were not imported successfully.
                </p>

                <div id = "actionButtonBar">
                    <p>
                        <g:link controller = "pendingUser" action="index" class="btn btn-success">
                            Manage Pending Users
                        </g:link>
                    </p>
                </div>

                <div id="stepThreeContent">

                </div>
            </div>
        </div>

</section>

</body>
</html>
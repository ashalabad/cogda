<!DOCTYPE html>
<html>
<head>
    <title>Company Manager</title>
    <r:require module="company"/>
    <r:layoutResources/>
</head>

<body data-ng-app="companyApp" data-base-url="${createLink(uri: '/company/')}">
<div class="container-fluid">

    <div class = "row-fluid">
        <h1>Company Manager</h1>
        <div class = "span9">
            <!-- Placeholder for view - Injects the views dynamically into the div for data-ng-view -->
            <div data-ng-view=""></div>

        </div>
        <div class="span3">
            nothing
        </div>
    </div>
</div>
<r:layoutResources/>
</body>
</html>
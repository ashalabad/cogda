
<%@ page import="com.cogda.multitenant.CustomerAccount;" %>
<html>

<head>
    <title><g:message code="default.tenant.welcome.title" args="[customerAccountInstance?.subDomain]"/> </title>
    <meta name="layout" content="kickstart" />
    <g:set var="layout_nomainmenu" value="${true}" scope="request"/>
    <g:set var="layout_nosecondarymenu" value="${true}" scope="request"/>
    <script type="text/javascript" src="http://code.angularjs.org/1.1.0/angular.min.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi?ext.js"></script>
    <script type='text/javascript'>

        var app = angular.module('', []);
        app.directive('chart', function() {
            return {
                link: function($scope, $elm, $attr) {
                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Date');
                    data.addColumn('number', 'Count');
                    data.addRows([
                        ['Past Due', 3],
                        ['0-3 Days', 14],
                        ['4-6 Days', 3],
                        ['7-9 Days', 8],
                        ['10+ Days', 6]
                    ]);

                    // Set chart options
                    var options = {
                        'legend':'labeled',
                        'pieSliceText': 'none',
                        'is3D':'true',
                        'width':350,
                        'chartArea': {'width':'100%', 'heigth':'100%', 'top':'0'}
                    };

                    // Instantiate and draw our chart, passing in some options.
                    var chart = new google.visualization.PieChart($elm[0]);
                    chart.draw(data, options);
                }
            }
        });
        app.directive('columnchart', function() {
            return {
                link: function($scope, $elm, $attr) {
                    // Create the data table.
                    var data = new google.visualization.DataTable();
                    data.addColumn('string', 'Date');
                    data.addColumn('number', 'Count');
                    data.addRows([
                        ['Past Due', 3],
                        ['0-3 Days', 14],
                        ['4-6 Days', 3],
                        ['7-9 Days', 8],
                        ['10+ Days', 6]
                    ]);

                    // Set chart options
                    var options = {
                        'legend':'labeled',
                        'pieSliceText': 'none',
                        'is3D':'true',
                        'width':350,
                        'chartArea': {'width':'100%', 'heigth':'100%', 'top':'0'}
                    };

                    // Instantiate and draw our chart, passing in some options.

                    var chart = new google.visualization.ColumnChart($elm[0]);
                    chart.draw(data, options);
                }
            }
        });

        google.setOnLoadCallback(function() {
            angular.bootstrap(document.body, ['']);
        });
        google.load('visualization', '1', {packages: ['corechart']});


    </script>
</head>

<body>

<sec:ifNotLoggedIn>
    <section id="intro">
        <mt:hasTenant>
            <h1>${companyInstance?.companyName?.encodeAsHTML()}</h1>
        </mt:hasTenant>
        <p>
            Today’s insurance industry is complex and there’s little accountability. If you doubt this, count the number of emails you see flying around trying to get work completed. Email was never designed to be a workflow solution.
        </p>
        <p>
            COGDA brings agents, wholesalers and carriers together on a single interactive platform, expediting the entire submission process by delivering a seamless flow of information, statistical underwriting, carrier appetite, and market intelligence from agent prospecting to individual carrier sales. COGDA allows data and communication exchanges between agency management systems and COGDA, so all parties have a streamlined connection, increasing sales, preventing duplication, providing accountability and reducing email dependencies.
        </p>

    </section>

    <section id="info">
        <div class="row-fluid">
            <div class="span4">
                <div class="left">
                    <h4>FOR AGENTS</h4>
                </div>
                <p>
                    The insurance industry has become ultra-competitive. Retaining customers is difficult and generating new business is even more challenging. COGDA provides the tools Agents need to convert prospects into clients, increase commissions and thrive in today’s fast-paced market.
                </p>
            </div>
            <div class="span4">
                <div class="left">
                    <h4>FOR WHOLESALERS</h4>
                </div>
                <p>
                    No more missing documents. COGDA keeps all submission documentation and correspondence in one place AND in chronological order. Get organized; view all submissions on the same screen and easily add documents to in-process submissions. COGDA makes the submission process easy!
                </p>
            </div>
            <div class="span4">
                <div class="left">
                    <h4>FOR CARRIERS</h4>
                </div>
                <p>
                    COGDA enables you to effectively communicate your appetite to your agent base. COGDA also allows you to be the first to receive and respond to Agents’ submissions and tell you when and why you lost a submission in real time, so you are not chasing business that is already bound.
                </p>
            </div>
        </div>
    </section>
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
    <section id="chart1" class="first">
        <div class="row-fluid">
            <div class="span6">
                <div class="left">
                    <h4>Open Submission</h4>
                </div>
                <div chart></div>
            </div>
            <div class="span6">
                <div class="left">
                    <h4>Upcoming Prospect Xdate</h4>
                </div>

                <div columnchart></div>
            </div>
        </div>
    </section>
    <section id="chart2">
        <div class="row-fluid">
            <div class="span6">
                <div class="left">
                    <h4>Submission by LOB</h4>
                </div>
                <div columnchart></div>
            </div>
            <div class="span6">
                <div class="left">
                    <h4>Submissions by Market</h4>
                </div>
                <div columnchart></div>
            </div>
        </div>
    </section>
</sec:ifLoggedIn>

</body>

</html>
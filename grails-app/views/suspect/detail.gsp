<%--
  Created by IntelliJ IDEA.
  User: chewy
  Date: 6/26/13
  Time: 3:01 PM
  To change this template use File | Settings | File Templates.
--%>


<%@ page import="org.apache.commons.lang3.StringUtils; com.cogda.multitenant.Lead" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="kickstart"/>
    <g:set var="entityName" value="${message(code: 'suspect.label', default: 'Suspect')}"/>
    <g:set var="layout_nosecondarymenu" value="true" scope="request"/>
    <g:set var="layout_nomainmenu" value="true" scope="request"/>
</head>

<body>
<section id="detail-suspect" class='first'>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12" style="background-color:WhiteSmoke; margin-bottom:7px; padding-left:3px">
                <span class="span3" style="padding-left: 23px;margin-top: 3px;padding-top: 5px;">
                    <strong><small><g:message code="suspect.nameandaddress.label"
                                              default="Suspect Name & Address"/></small></strong><br>
                    <small>${fieldValue(bean: suspectInstance, field: "clientName")}</small><br/>
                    <small>${fieldValue(bean: suspectInstance, field: "primaryAddress")}</small><br/>
                    <g:if test='${suspectInstance.leadAddress.address.addressTwo != null && !StringUtils.isBlank(suspectInstance.leadAddress.address.addressTwo)}'>
                        <small>${fieldValue(bean: suspectInstance.leadAddress, field: "addressTwo")}</small><br/>
                    </g:if>
                    <small>${fieldValue(bean: suspectInstance, field: 'city')},${fieldValue(bean: suspectInstance, field: 'state')} ${fieldValue(bean: suspectInstance, field: 'zipCode')}</small>
                </span>
                <span class="span3" style="padding-left: 23px;margin-top: 3px;padding-top: 5px;">
                    <strong><small><g:message code="suspect.contactAndPhoneNumbers.label"
                                              default="Contact & Phone Numbers"/></small></strong><br>
                    <small><g:message code="suspect.name.label"
                                      default="Name:"/> &nbsp; ${suspectInstance.leadContact.getFullName().encodeAsHTML()}<br>
                    </small>
                    <small><g:message code="lead.phoneNumber.label"
                                      default="Phone Number:"/>: &nbsp;${fieldValue(bean: suspectInstance, field: 'phoneNumber')}<br>
                    </small>
                </span>
                <span class="span3" style="padding-left: 23px;margin-top: 3px;padding-top: 5px;">
                    <strong><small><g:message code="lead.industryClassification.label"
                                              default="Industry Classification"/></small></strong><br>
                    <small><g:message code="suspect.businessType.label" default="Type"/>&nbsp ${suspectInstance.businessType.description.encodeAsHTML()}<br></small>
                    <small><g:message code="suspect.sicCode.label" default="SIC:"/>
                    </small>
                </span>
                <span class="span3" style="padding-left: 23px;margin-top: 3px;padding-top: 5px;">
                    <strong><small>Producers &amp; CSR</small></strong><br>
                    <address>
                        <small>PR1:&nbsp;Alex Smith</small><br>
                        <small>PR2:</small><br>
                        <small>CSR:&nbsp;Nancy James</small><br>
                    </address>
                </span>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span4">
                <div class="accordion" id="accordion2">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseTwo">
                                Client Documents (8)
                            </a>
                        </div>

                        <div id="collapseTwo" class="accordion-body collapse">
                            <div class="accordion-inner" style="background-color:WhiteSmoke">
                                <table class="table table-condensed">
                                    <thead>
                                    <tr>
                                        <th><small>File Name</small></th>
                                        <th><small>Created/Modified</small></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td><a href="#">AccordApp2012.pdf</a></td>
                                        <td>12/21/2012</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">loss_reports_2013.pdf</a></td>
                                        <td>2/1/2013</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">Fork_lift_pictures.jpg</a></td>
                                        <td>9/1/2012</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">AccordApp2011.pdf</a></td>
                                        <td>12/21/2012</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">loss_reports_2012.pdf</a></td>
                                        <td>2/1/2013</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">equipment_pictures.jpg</a></td>
                                        <td>9/1/2012</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">loss_reports_2011.pdf</a></td>
                                        <td>6/2/2011</td>
                                    </tr>
                                    <tr>
                                        <td><a href="#">FinancialReport2012.pdf</a></td>
                                        <td>1/13/2012</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2"
                               href="#collapseThree">
                                Client Notes
                            </a>
                        </div>

                        <div id="collapseThree" class="accordion-body collapse">
                            <div class="accordion-inner" style="background-color:WhiteSmoke;padding-top: 17px;">
                                <p class="text-center">
                                    <input type="text" class="input-medium search-query" placeholder="Showing All">
                                    <button type="submit" class="btn btn-small">Search</button>
                                </p>
                                <ul class="nav nav-list"
                                    style="margin-top: 17px;margin-bottom: 17px;padding-left: 7px;padding-right: 7px;">
                                    <li class="divider"></li>
                                </ul>
                                <textarea class="span12" rows="6" placeholder="New Note..."
                                          style=" margin-bottom: 0px;"></textarea>

                                <p class="text-center">
                                    <label class="radio inline">
                                        <input type="radio" id="inlineCheckbox1" value="option1">Call
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" id="inlineCheckbox2" value="option2">Visit
                                    </label>
                                    <label class="radio inline">
                                        <input type="radio" id="inlineCheckbox3" value="option3">Other
                                    </label>
                                </p>

                                <p class="text-center">
                                    <button class="btn btn-primary" type="button" style="margin-top:3px">Create</button>
                                </p>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="span8"
                 style="border:1px solid; border-radius:5px; border-color:LightGray;margin-left: 7px;padding-left: 0px; height:600px">
                <!--Body content-->
            </div>
        </div>
    </div>
</section>
</body>
</html>

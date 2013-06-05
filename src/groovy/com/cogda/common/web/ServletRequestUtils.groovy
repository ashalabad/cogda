package com.cogda.common.web

import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 3/23/13
 * Time: 11:50 PM
 * To change this template use File | Settings | File Templates.
 */
class ServletRequestUtils {

    /**
     * Retrieves the request properties of interest to the DigitalSignature process. Map property names
     * match those in the DigitalSignature class for binding properties.
     * @param request
     * @return Map
     */
    public static Map retrieveRequestProps(HttpServletRequest request){
        Map reqProps = [:]

//        requestMap.scheme = request.getScheme(); 			  // http || https
//        requestMap.serverName =  request.getServerName();   // dev.hostname.com
//        requestMap.serverPort = request.getServerPort();    // 80
//        requestMap.contextPath = request.getContextPath();  // /mywebapp e.g. /rapcomm
//        requestMap.servletPath = request.getServletPath();  // /servlet/MyServlet e.g. /OrganizationName/controller/action/id
//        requestMap.pathInfo = request.getPathInfo();        // /a/b;c=123
//        requestMap.queryString = request.getQueryString();  // d=789

        reqProps.scheme = request.scheme                      // http  https
        reqProps.serverName = request.serverName              // dev.hostname.com  localhost
        reqProps.requestServerIpAddress = request.localAddr   // IP Address
        reqProps.serverPort = request.serverPort              // 80 8080
        reqProps.contextPath = request.contextPath            // /mywebapp e.g. /rapcomm
        reqProps.servletPath = request.servletPath            // /servlet/MyServlet e.g. /OrganizationName/controller/action/id
        reqProps.pathInfo = request.pathInfo                  // /a/b;c=123
        reqProps.queryString = request.queryString            // d=789
        reqProps.requestLocalName = request.localName         // when hosted as ROOT - /companyName.  If not hosted as ROOT - /webappname
        reqProps.requestServerName = request.serverName       // /servlet/MyServlet e.g. /OrganizationName/controller/action/id

        return reqProps
    }

    public static String retrieveLocalName(HttpServletRequest request){
        return request.localName
    }
}

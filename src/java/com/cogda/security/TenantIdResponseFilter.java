package com.cogda.security;

import com.google.gson.*;
import grails.plugin.multitenant.core.ast.MultiTenantAST;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/24/13
 * Time: 10:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class TenantIdResponseFilter implements Filter {
    private static final String TENANT_ID_PROPERTY_KEY = "\"" + MultiTenantAST.TENANT_ID_FIELD_NAME + "\":";
    private static final String TENANT_ID_REPLACEMENT_KEY = "\"NULLION\":\"NULLION\"";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String serverName = request.getServerName();
        int periodCount = countCharInstances(serverName, '.');
        boolean subDomainPresent = (periodCount > 2);

        // A CustomerAccount exists

        if (subDomainPresent) {
            TenantIdResponseWrapper tenantIdResponseWrapper = new TenantIdResponseWrapper((HttpServletResponse) response);

            chain.doFilter(request, tenantIdResponseWrapper);
            String contentType = tenantIdResponseWrapper.getContentType();
            if (contentType != null && contentType.contains("json")) {
                PrintWriter out = response.getWriter();
                CharArrayWriter caw = new CharArrayWriter();
                String jsonString = tenantIdResponseWrapper.toString();

                // make our modifications
                if (jsonString.contains(MultiTenantAST.TENANT_ID_FIELD_NAME)) {
                    // we must replace the following from the outputted response
                    // e.g. ,"tenantId":110002,
                    // e.g. {"tenantId":110002}
                    // e.g. "tenantId":110002,
                    // e.g. "tenantId":110002}

                    int idx = 0;
                    while (idx != -1) {
                        idx = jsonString.indexOf(TENANT_ID_PROPERTY_KEY, idx);
                        if (idx != -1) {
                            int start = idx;
                            idx = idx + TENANT_ID_PROPERTY_KEY.length();
                            int indexOfNextComma = jsonString.indexOf(",", idx);
                            int indexOfNextEndCurly = jsonString.indexOf("}", idx);
                            int end = -1;
                            if (indexOfNextComma != -1 && indexOfNextEndCurly != -1) {
                                if (indexOfNextComma < indexOfNextEndCurly) {
                                    end = indexOfNextComma;
                                } else {
                                    end = indexOfNextEndCurly;
                                }
                            }

                            if (idx != -1 && end != -1) {
                                jsonString = jsonString.replaceAll(jsonString.substring(start, end), TENANT_ID_REPLACEMENT_KEY);
                            }
                        }
                    }
                    jsonString = jsonString.replaceAll("," + TENANT_ID_REPLACEMENT_KEY, "");
                    jsonString = jsonString.replaceAll("\\{" + TENANT_ID_REPLACEMENT_KEY + ",", "{");
                }
                response.setContentLength(jsonString.length());
                caw.write(jsonString);
                out.write(caw.toString());
                out.close();
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * Returns the number of occurrences of the passed in character.
     *
     * @param s
     * @param c
     * @return int
     */
    private int countCharInstances(String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }
}

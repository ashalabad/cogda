package com.cogda.security;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: christopher
 * Date: 6/24/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class TenantIdResponseWrapper extends HttpServletResponseWrapper{
    private CharArrayWriter output;

    public String toString() {
        return output.toString();
    }

    public PrintWriter getWriter(){
        return new PrintWriter(output);
    }

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @throws IllegalArgumentException
     *          if the response is null
     */
    public TenantIdResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new CharArrayWriter();
    }


}

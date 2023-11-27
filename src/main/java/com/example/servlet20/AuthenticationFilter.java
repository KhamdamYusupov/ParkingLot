package com.example.servlet20;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Enumeration;

public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String basicAuthCode = "";
        final Enumeration<String> headerNames = ((HttpServletRequest) request).getHeaderNames();
        boolean authDetailsFound = false;
        while (headerNames.hasMoreElements()) {
            final var headerName = headerNames.nextElement();
            if (headerName.equalsIgnoreCase("authorization")) {
                authDetailsFound = true;
                basicAuthCode = ((HttpServletRequest) request).getHeader(headerName);
            }
        }
        if(!authDetailsFound) {
            response.getWriter().println("You must enter authentication details!");
        }
        String encode = basicAuthCode.trim().split(" ")[1];
        String code = new String(Base64.getDecoder().decode(encode), StandardCharsets.UTF_8);
        String user = code.split(":")[0];
        String password = code.split(":")[1];
        if (user.equalsIgnoreCase("admin") && password.equalsIgnoreCase("123")) {
            filterChain.doFilter(request, response);
        } else {
            response.getWriter().println("You must be authenticated in order to use this function!");
        }
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

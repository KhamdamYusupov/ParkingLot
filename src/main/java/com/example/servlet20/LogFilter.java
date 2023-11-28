package com.example.servlet20;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("The request URI is: " + ((HttpServletRequest) request).getRequestURI());
        System.out.println("The remote IP address " + request.getRemoteAddr());
        System.out.println("The headers of the request are: ");
        Enumeration<String> headerNames = ((HttpServletRequest) request).getHeaderNames();
        while(headerNames.hasMoreElements()) {
            final var headerName = headerNames.nextElement();
            System.out.println(headerName + " : " + ((HttpServletRequest) request).getHeader(headerName));
        }
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

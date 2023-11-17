package com.example.servlet20;


import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;

public class FilterServlet implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if(name.equalsIgnoreCase("admin") && password.equalsIgnoreCase("password")){
            chain.doFilter(request, response);
        } else{
            writer.println("username or password error");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.include(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

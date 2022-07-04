package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.filter.wrapper.XSSRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * The type XSS security filter.
 * Filter the request parameters.
 */
@WebFilter(filterName = "XSSSecurityFilter", urlPatterns = "/*")
public class XSSSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest) request), response);
    }
}

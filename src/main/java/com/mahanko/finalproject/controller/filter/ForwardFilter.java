package com.mahanko.finalproject.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "ForwardFilter", dispatcherTypes = DispatcherType.FORWARD, urlPatterns = "/pages/*")
public class ForwardFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        // FIXME: 27.04.2022
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpServletResponse.setDateHeader("Expires", 0); // Proxies.
        chain.doFilter(request, response);
    }
}

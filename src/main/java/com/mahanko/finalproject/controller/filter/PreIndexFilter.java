package com.mahanko.finalproject.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "PreIndexFilter", urlPatterns = "/index.jsp") // urlPatterns - на кого срабатывают
public class PreIndexFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession(false);
        logger.log(Level.INFO, "Session in PreIndexFilter : {}.", session != null ? session.getId() : "session is not created");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}

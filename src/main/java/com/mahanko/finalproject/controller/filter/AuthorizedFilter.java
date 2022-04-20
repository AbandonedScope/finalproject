package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "AuthorizedFilter", urlPatterns = "/pages/*",
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class AuthorizedFilter implements Filter {
    private String indexPath;

    @Override
    public void init(FilterConfig config) throws ServletException {
        indexPath = config.getInitParameter("INDEX_PATH");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        // FIXME: 19.04.2022
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpServletResponse.setDateHeader("Expires", 0); // Proxies.
        String loginURI = httpServletRequest.getContextPath().concat("/").concat(PagePath.INDEX);
        boolean loggedIn = session != null && session.getAttribute(ParameterType.USER) != null;
        boolean loginRequest = httpServletRequest.getRequestURI().equals(loginURI);
        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
        }
    }
}

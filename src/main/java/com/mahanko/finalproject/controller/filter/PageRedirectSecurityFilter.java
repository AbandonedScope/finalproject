package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.PagePath;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "PageRedirectSecurityFilter", urlPatterns = "/pages/admin/*",
            initParams = {@WebInitParam(name = "MAIN_PAGE", value = "/pages/main.jsp")})
public class PageRedirectSecurityFilter implements Filter {
    private String mainPath;

    @Override
    public void init(FilterConfig config) throws ServletException {
        mainPath = config.getInitParameter("MAIN_PAGE");
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
        boolean isAdmin = session != null && ((CustomerEntity)session.getAttribute(ParameterType.USER)).getRole() == RoleType.ADMIN;
        if (isAdmin) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + mainPath);
        }
    }

    @Override
    public void destroy() {
    }

}

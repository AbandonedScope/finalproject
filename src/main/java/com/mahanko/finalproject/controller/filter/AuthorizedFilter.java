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

@WebFilter(filterName = "AuthorizedFilter", urlPatterns = "/pages/*")
public class AuthorizedFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String loginURI = httpServletRequest.getContextPath().concat("/").concat(PagePath.LOGIN);
        String registrationURI = httpServletRequest.getContextPath().concat("/").concat(PagePath.REGISTRATION);
        CustomerEntity user = (CustomerEntity) session.getAttribute(ParameterType.USER);
        boolean loggedIn = user != null && user.getRole() != RoleType.GUEST;
        boolean loginRequest = httpServletRequest.getRequestURI().equals(loginURI);
        boolean registrationRequest = httpServletRequest.getRequestURI().equals(registrationURI);
        if (loggedIn || loginRequest || registrationRequest) {
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath().concat("/").concat(PagePath.LOGIN));
        }
    }
}

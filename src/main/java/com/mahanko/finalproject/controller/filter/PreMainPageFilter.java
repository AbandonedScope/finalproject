package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuSection;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "PreMainPageFilter", urlPatterns = "/index.jsp")
public class PreMainPageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        ServletContext servletContext = session.getServletContext();
        MenuSectionService sectionService = MenuSectionServiceImpl.getInstance();
        List<MenuSection> sections;
        try {
            sections = sectionService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        servletContext.setAttribute(AttributeType.SECTIONS, sections);
        chain.doFilter(request, response);
    }
}

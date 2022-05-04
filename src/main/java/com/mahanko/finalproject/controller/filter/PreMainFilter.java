package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.MenuItemComposite;
import com.mahanko.finalproject.model.service.MenuItemService;
import com.mahanko.finalproject.model.service.impl.MenuItemServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "PreMainFilter", urlPatterns = "/index.jsp")
public class PreMainFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        MenuItemService service = new MenuItemServiceImpl();
        List<MenuItemComposite> items = null;
        try {
            items = service.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        session.setAttribute(ParameterType.MENU_ITEMS, items);
        chain.doFilter(request, response);
    }
}

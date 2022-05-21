package com.mahanko.finalproject.controller.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.MenuSectionService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import com.mahanko.finalproject.model.service.impl.MenuSectionServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "PreAddMenuItemFilter", urlPatterns = "/pages/authorized/admin/add-menu-item.jsp")
public class PreAddMenuItemFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        IngredientService service = new IngredientServiceImpl();
        try {
            Gson gson = new GsonBuilder().create();
            String jsonIngredients = gson.toJson(service.findAll());
            MenuSectionService sectionService = new MenuSectionServiceImpl();
            // FIXME: 27.04.2022
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                session.setAttribute(ParameterType.SECTIONS, sectionService.findAll());
                session.setAttribute(ParameterType.INGREDIENTS, jsonIngredients);
            }

        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        chain.doFilter(request, response);
    }
}

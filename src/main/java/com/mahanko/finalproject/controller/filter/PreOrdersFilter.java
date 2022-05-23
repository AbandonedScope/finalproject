package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "PreOrdersFilter", urlPatterns = "/pages/authorized/admin/orders.jsp")
public class PreOrdersFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        OrderService service = new OrderServiceImpl();
        try {
            List<OrderEntity> orders = service.findAll();
            request.setAttribute(ParameterType.ORDERS, orders);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}

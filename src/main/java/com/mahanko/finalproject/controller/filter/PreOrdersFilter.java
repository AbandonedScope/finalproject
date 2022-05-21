package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.AttributeType;
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
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        OrderService service = new OrderServiceImpl();
        try {
            List<OrderEntity> orders = service.findAll();
            request.setAttribute(AttributeType.ORDERS, orders);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        chain.doFilter(request, response);
    }
}

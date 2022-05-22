package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.OrderEntity;
import com.mahanko.finalproject.model.service.OrderService;
import com.mahanko.finalproject.model.service.impl.OrderServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "PreUserOrdersPageFilter", urlPatterns = "/pages/authorized/orders.jsp")
public class PreUserOrdersPageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        CustomerEntity customer = (CustomerEntity) session.getAttribute(AttributeType.USER);
        OrderService service = new OrderServiceImpl();
        try {
            List<OrderEntity> userOrders = service.findOrdersByCustomerId(customer.getId());
            if (!userOrders.isEmpty()) {
                httpRequest.setAttribute(ParameterType.USER_ORDERS, userOrders);
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        chain.doFilter(request, response);
    }
}

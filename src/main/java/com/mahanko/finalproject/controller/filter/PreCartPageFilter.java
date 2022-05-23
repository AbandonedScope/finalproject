package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.model.entity.PaymentType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@WebFilter(filterName = "PreCartPageFilter", urlPatterns = "/pages/cart.jsp")
public class PreCartPageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        var paymentTypes = Arrays.stream(PaymentType.values()).map(PaymentType::toString).collect(Collectors.toList());
        httpRequest.setAttribute(ParameterType.PAYMENT_TYPES, paymentTypes);
        chain.doFilter(request, response);
    }
}

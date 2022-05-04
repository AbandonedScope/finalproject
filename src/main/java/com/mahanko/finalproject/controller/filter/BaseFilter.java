package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "BaseFilter", urlPatterns = "/*") // urlPatterns - на кого срабатывают
public class BaseFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpResponse.setDateHeader("Expires", 0); // Proxies.
        if (session.getAttribute(ParameterType.USER) == null) {
            CustomerEntity customer = CustomerEntity.newBuilder()
                    .setRole(RoleType.GUEST)
                    .build();
            session.setAttribute(ParameterType.USER, customer);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

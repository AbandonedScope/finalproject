package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.LocaleType;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(filterName = "BaseFilter", urlPatterns = "/*") // urlPatterns - на кого срабатывают
public class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        Cookie[] cookies = httpRequest.getCookies();
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpResponse.setDateHeader("Expires", 0); // Proxies.
        if (session.getAttribute(AttributeType.USER) == null) {
            CustomerEntity customer = CustomerEntity.newBuilder()
                    .setRole(RoleType.GUEST)
                    .build();
            session.setAttribute(AttributeType.USER, customer);
            Optional<Cookie> cookieOptional = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(CookieName.LOCALE))
                    .findFirst();
            cookieOptional.ifPresentOrElse(cookie -> session.setAttribute(AttributeType.LOCALE, cookie.getValue()),
                    () -> {
                        Cookie localCookie = new Cookie(CookieName.LOCALE, LocaleType.DEFAULT);
                        session.setAttribute(AttributeType.LOCALE, LocaleType.DEFAULT);
                        httpResponse.addCookie(localCookie);
                    });
        }
        chain.doFilter(request, response);
    }
}

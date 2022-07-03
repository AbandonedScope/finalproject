package com.mahanko.finalproject.controller.filter;

import com.mahanko.finalproject.controller.AttributeType;
import com.mahanko.finalproject.controller.command.CommandType;
import com.mahanko.finalproject.controller.filter.permission.UserPermission;
import com.mahanko.finalproject.model.entity.CustomerEntity;
import com.mahanko.finalproject.model.entity.RoleType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.mahanko.finalproject.controller.ParameterType.COMMAND;

/**
 * The type Command permission filter class determines what commands
 * the client can use.
 * @see jakarta.servlet.Filter
 * @see UserPermission
 */
@WebFilter(filterName = "CommandPermissionFilter", urlPatterns = "/controller")
public class CommandPermissionFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setContentType("text/html");
        logger.log(Level.INFO, "CommandPermissionFilter - doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String commandString = httpServletRequest.getParameter(COMMAND);
        if (commandString == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        CommandType command = CommandType.of(commandString);
        RoleType role = RoleType.GUEST;

        CustomerEntity user = (CustomerEntity) session.getAttribute(AttributeType.USER);
        if (user != null) {
            role = user.getRole();
        }

        UserPermission type;
        switch (role) {
            case ADMIN:
                type = UserPermission.ADMIN;
                break;
            case CUSTOMER:
                type = UserPermission.CUSTOMER;
                break;
            default:
                type = UserPermission.GUEST;
                break;
        }

        boolean isPermitted = type.isPermitted(command);

        if (!isPermitted) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }
}

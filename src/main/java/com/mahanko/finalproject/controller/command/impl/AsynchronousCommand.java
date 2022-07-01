package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.util.ToJsonConverter;
import com.mahanko.finalproject.util.ValidationsToJsonConverter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class AsynchronousCommand implements Command {
    protected Router fillResponse(HttpServletResponse response, boolean success) {
        Router router = new Router();
        router.setType(Router.Type.NONE);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            router.setType(Router.Type.NONE);
        } else {
            // TODO: 01.07.2022
        }

        return router;
    }

    protected Router fillResponse(HttpServletResponse response, boolean success, List<String> validationMessages) throws IOException {
        Router router = new Router();
        router.setType(Router.Type.NONE);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ToJsonConverter<List<String>> converter = new ValidationsToJsonConverter();
            String validations = converter.convert(validationMessages);
            response.getWriter().write(validations);
        }

        return router;
    }
}

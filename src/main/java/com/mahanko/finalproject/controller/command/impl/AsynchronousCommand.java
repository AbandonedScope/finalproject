package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.command.Command;
import com.mahanko.finalproject.util.ToJsonConverter;
import com.mahanko.finalproject.util.ValidationsToJsonConverter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * The command, that is supposed to return {@link Router} with {@link com.mahanko.finalproject.controller.Router.Type} NONE.
 */
public abstract class AsynchronousCommand implements Command {
    /**
     * Manage the response status.
     * @param response The response
     * @param success Whether command executed successfully or not
     * @return The router
     */
    protected Router fillResponse(HttpServletResponse response, boolean success) {
        Router router = new Router();
        router.setType(Router.Type.NONE);
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }

        return router;
    }

    /**
     * Manage the response status. In case if command executed unsuccessfully, set response content type to application/json and fills response with validation messages.
     * @param response The response
     * @param success Whether command executed successfully or not
     * @param validationMessages Validation messages to fill with response
     * @return The router
     * @throws IOException The IOException
     */
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

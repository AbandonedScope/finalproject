package com.mahanko.finalproject.controller.command.impl;

import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.exception.CommandException;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.service.IngredientService;
import com.mahanko.finalproject.model.service.impl.IngredientServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveIngredientCommand extends AsynchronousCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Router router;
        try {
            String ingredientIdString = request.getParameter(ParameterType.INGREDIENT_ID);
            long ingredientId = Long.parseLong(ingredientIdString);
            IngredientService ingredientService = IngredientServiceImpl.getInstance();
            boolean removed = ingredientService.remove(ingredientId);
            router = fillResponse(response, removed);
        } catch (NumberFormatException e) {
            logger.log(Level.ERROR, e);
            throw new CommandException(e);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        return router;
    }
}

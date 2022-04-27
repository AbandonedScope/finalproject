package com.mahanko.finalproject.util;

import com.google.gson.*;
import com.mahanko.finalproject.controller.ParameterType;
import com.mahanko.finalproject.exception.ServiceException;
import com.mahanko.finalproject.model.entity.menu.IngredientComponent;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomJsonParser {
    private CustomJsonParser() {
    }

    public static Map<String, String> parse(HttpServletRequest request) throws ServiceException {
        try {
            Map<String, String> parameters = new HashMap<>();
            JsonObject jsonRequestObject = JsonParser.parseReader(request.getReader()).getAsJsonObject();
            String command = jsonRequestObject.get(ParameterType.COMMAND).getAsString();
            parameters.put(ParameterType.COMMAND,command);
            JsonObject menuItem = jsonRequestObject.get("menu_item").getAsJsonObject();
            String menuItemName = menuItem.get("name").getAsString();
            String menuItemCost = menuItem.get("cost").getAsString();
            JsonArray ingredients = menuItem.get("ingredients").getAsJsonArray();
            Gson gson = new GsonBuilder().create();
            List<IngredientComponent> ingredientsList = new ArrayList<>();
            ingredients.forEach(ingredient -> ingredientsList.add(gson.fromJson(ingredient, IngredientComponent.class)));
            return parameters;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}

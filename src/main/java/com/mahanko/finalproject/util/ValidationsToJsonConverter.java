package com.mahanko.finalproject.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mahanko.finalproject.controller.Router;
import com.mahanko.finalproject.controller.ValidationMessage;

import java.io.IOException;
import java.util.List;

public class ValidationsToJsonConverter implements ToJsonConverter<List<String>> {

    @Override
    public String convert(List<String> messages) {
        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(ValidationMessage.VALIDATION_MESSAGES, gson.toJsonTree(messages.toArray()));
        return jsonObject.toString();
    }
}

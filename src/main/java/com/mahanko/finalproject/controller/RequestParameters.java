package com.mahanko.finalproject.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestParameters {
    private final Map<String, String> singleParameters = new HashMap<>();
    private final Map<String, List<String>> multipleParameters = new HashMap<>();

    public String put(String parameterName, String value) {
        return singleParameters.put(parameterName, value);
    }

    public List<String> put(String parameterName, List<String> value) {
        return multipleParameters.put(parameterName, value);
    }

    public String get(String parameterName) {
        return singleParameters.get(parameterName);
    }

    public List<String> getMultiple(String parameterName) {
        return multipleParameters.get(parameterName);
    }

    public boolean fillRequestWithValidations(HttpServletRequest request) {
        List<String> messages = getMultiple(ParameterType.VALIDATION_MESSAGES);
        boolean isNotEmpty = messages != null && !messages.isEmpty();
        if (isNotEmpty) {
            for (String message : messages) {
                request.setAttribute(message, false);
            }
        }

        return isNotEmpty;
    }
}

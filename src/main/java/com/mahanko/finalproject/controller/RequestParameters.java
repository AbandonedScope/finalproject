package com.mahanko.finalproject.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type RequestParameters class.
 */
public class RequestParameters {
    private final Map<String, String> singleParameters = new HashMap<>();
    private final Map<String, List<String>> multipleParameters = new HashMap<>();

    /**
     * Put one parameter.
     * @param parameterName The name of the parameter
     * @param value The value of the parameter
     * @return The previous value associated with that name, or null, it there is no such value.
     */
    public String put(String parameterName, String value) {
        return singleParameters.put(parameterName, value);
    }

    /**
     * Put list of parameters.
     * @param parameterName The name of the parameters
     * @param value The list of the parameters
     * @return The previous list of parameters associated with that name, or null, it there is no such list.
     */
    public List<String> put(String parameterName, List<String> value) {
        return multipleParameters.put(parameterName, value);
    }

    /**
     * Get a parameter.
     * @param parameterName The name of the parameter
     * @return The value associated with that name, or null, it there is no such value.
     */
    public String get(String parameterName) {
        return singleParameters.get(parameterName);
    }

    /**
     * Get a list of parameters.
     * @param parameterName The name of the parameters
     * @return The list of parameters associated with that name, or null, it there is no such list.
     */
    public List<String> getMultiple(String parameterName) {
        return multipleParameters.get(parameterName);
    }

    /**
     * Fills request with stored inside RequestParameters class object validation messages
     * @param request The request
     * @return true, if there was any validation message, otherwise false.
     */
    public boolean fillRequestWithValidations(HttpServletRequest request) {
        List<String> messages = getMultiple(ValidationMessage.VALIDATION_MESSAGES);
        boolean isNotEmpty = messages != null && !messages.isEmpty();
        if (isNotEmpty) {
            for (String message : messages) {
                request.setAttribute(message, true);
            }
        }

        return isNotEmpty;
    }
}

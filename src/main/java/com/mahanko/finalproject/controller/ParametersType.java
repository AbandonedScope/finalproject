package com.mahanko.finalproject.controller;

import java.util.Locale;

public enum ParametersType {
    NAME,
    SURNAME,
    LOGIN,
    PASSWORD,
    CONFIRM_PASSWORD,
    COMMAND;
    @Override
    public String toString() {
        return super.toString().toLowerCase(Locale.ROOT).replace('_', '-');
    }
}

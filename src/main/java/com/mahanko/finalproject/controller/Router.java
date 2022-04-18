package com.mahanko.finalproject.controller;

public class Router {
    private String page = "index.jsp";
    private Type type = Type.REDIRECT;

    public enum Type {
        FORWARD,
        REDIRECT
    }

    public Router() {
    }

    public Router(String page) {
        this.page = page;
    }

    public Router(String page, Type type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public Type getType() {
        return type;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setType(Type type) {
        this.type = type;
    }
}

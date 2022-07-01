package com.mahanko.finalproject.util;

public interface ToJsonConverter<T> {
    String convert(T object);
}

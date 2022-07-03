package com.mahanko.finalproject.util;

/**
 * The interface ToJsonConverter.
 *
 * @param <T> the type of entity to convert.
 */
public interface ToJsonConverter<T> {
    /**
     * Convert object into json string.
     *
     * @param object the object to convert.
     * @return the json string representation of the object.
     */
    String convert(T object);
}

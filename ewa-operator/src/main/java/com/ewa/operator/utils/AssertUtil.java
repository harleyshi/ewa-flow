package com.ewa.operator.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * assert util class
 * @author harley.shi
 * @date 2024/7/3
 */
public final class AssertUtil {

    public static void notNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertNull(Object value, String message) {
        if (value != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notBlank(String value, String message) {
        if (value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean value, String message) {
        if (!value) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean value, String message) {
        if (value) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void assertEquals(Object expect, Object actual, String message) {
        if (!Objects.equals(expect, actual)) {
            throw new IllegalArgumentException(message);
        }
    }
}

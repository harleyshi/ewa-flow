package com.ewa.operator.utils;

/**
 * @author harley.shi
 * @date 2024/7/1
 */
public final class AuxiliaryUtils {

    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static boolean isType(String typename) {
        return asClass(typename) != null;
    }

    public static Class<?> asClass(String typename) {
        if (isBlank(typename)) {
            return null;
        }
        try {
            return Class.forName(typename, false, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            return null;
        }
    }
}

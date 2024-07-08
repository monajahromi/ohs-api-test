package com.vgcslabs.ohs.util;

import java.lang.reflect.Field;
import java.util.Arrays;

public class BatchMappingUtils {

    public static String[] getFieldNames(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .map(Field::getName)
                .toArray(String[]::new);
    }
}

package com.codingrecipe.member;

import java.lang.reflect.Field;

public class StringUtils {
    private StringUtils() {}

    // 문자열에서 공백 제거
    public static String removeSpaces(String str) {
        return (str != null) ? str.replace(" ", "") : str;
    }

    // DTO의 모든 문자열 필드에서 공백 제거
    public static void removeSpacesFromDtoFields(Object dto) {
        // Reflection을 사용하여 모든 필드 접근
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(dto);
                    field.set(dto, removeSpaces(value));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

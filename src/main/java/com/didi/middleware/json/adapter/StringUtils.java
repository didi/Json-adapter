package com.didi.middleware.json.adapter;

public class StringUtils {

    public static boolean isEmpty(String str) {
        return (str == null || "".equals(str) || "".equals(str.trim()));
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}

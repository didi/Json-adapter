package com.didi.middleware.json.adapter;

import java.io.Serializable;
import java.util.*;

public class JSONArray implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private final List<Object> list;

    public JSONArray(int initialCapacity) {
        list = new ArrayList<>(initialCapacity);
    }

    public JSONArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public JSONArray(List<Object> list) {
        if (list == null) {
            throw new IllegalArgumentException("list is null.");
        }
        this.list = list;
    }

    public void add(Object o) {
        this.list.add(o);
    }

    public String toJSONString() {
        return JSON.toJSONString(this.list);
    }

    public List<Object> getList() {
        return list;
    }


    public String toString() {
        return this.toJSONString();
    }

    public static String toJSONString(Object o) {
        return JSON.toJSONString(o);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

}

package com.didi.middleware.json.adapter;

import com.didi.middleware.json.adapter.serializer.JSONArraySerializer;
import com.didi.middleware.json.adapter.serializer.JSONObjectSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JSON {

    private static SimpleModule simpleModule = new SimpleModule();

    static {
        simpleModule.addSerializer(JSONObject.class, new JSONObjectSerializer());
        simpleModule.addSerializer(JSONArray.class, new JSONArraySerializer());
    }

    private static ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(simpleModule);

    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static byte[] toJSONBytes(Object object) {
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return objectMapper.readValue(text, clazz);
        } catch (IOException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return objectMapper.readValue(text, typeReference);
        } catch (Exception e) {
            throw new JSONException("Json exception", e);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            return objectMapper.readValue(text, type);
        } catch (IOException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static JSONArray parseArray(String text) {
        return new JSONArray(parseArray(text, Object.class));
    }

    public static JsonNode parseJsonNode(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        try {
            return objectMapper.readTree(text);
        } catch (IOException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static JSONObject parseObject(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(text, new TypeReference<Map<String, Object>>() {
            });
            return new JSONObject(map);
        } catch (IOException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static Map<String, Object> parseObjectMap(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(text, new TypeReference<Map<String, Object>>() {
            });
            return map;
        } catch (IOException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

    public static List<Object> parseObjectList(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        List<Object> list = null;
        try {
            list = objectMapper.readValue(text, new TypeReference<List<Object>>() {
            });
            return list;
        } catch (IOException e) {
            throw new JSONException("Json parse exception: ", e);
        }
    }

}

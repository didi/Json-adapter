package com.didi.middleware.json.adapter;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static com.didi.middleware.json.adapter.TypeUtils.*;


public class JSONObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private final Map<String, Object> map;

    public JSONObject(int initialCapacity, boolean ordered) {
        if (ordered) {
            map = new LinkedHashMap<String, Object>(initialCapacity);
        } else {
            map = new HashMap<String, Object>(initialCapacity);
        }
    }

    public JSONObject(boolean ordered) {
        this(DEFAULT_INITIAL_CAPACITY, ordered);
    }

    public JSONObject() {
        this(DEFAULT_INITIAL_CAPACITY, false);
    }

    public JSONObject(Map<String, Object> map) {
        this.map = map;
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Map<String, Object> getInnerMap() {
        return this.map;
    }

    public static JSONObject parseObject(String text) {
        return JSON.parseObject(text);
    }

    public JSONObject getJSONObject(String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof JSONObject) {
            return (JSONObject) value;
        }

        if (value instanceof Map) {
            return new JSONObject((Map) value);
        }

        if (value instanceof String) {
            return JSON.parseObject((String) value);
        } else {
            String data = JSON.toJSONString(value);
            return JSON.parseObject(data);
        }
    }

    public List<Object> getJSONObjectList(String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof List) {
            return (List<Object>) value;
        }

        if (value instanceof String) {
            return JSON.parseObjectList((String) value);
        } else {
            String data = JSON.toJSONString(value);
            return JSON.parseObjectList(data);
        }
    }

    public <T> List<T> getJSONList(String key, Class<T> clazz) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }

        if (value instanceof String) {
            return JSON.parseArray((String) value, clazz);
        } else {
            String data = JSON.toJSONString(value);
            return JSON.parseArray(data, clazz);
        }
    }

    public <T> T toJavaObject(Class<T> clazz) {
        String data = JSON.toJSONString(map);
        return JSON.parseObject(data, clazz);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object obj = map.get(key);

        if (obj == null) {
            if (clazz == int.class) {
                return (T) Integer.valueOf(0);
            } else if (clazz == long.class) {
                return (T) Long.valueOf(0);
            } else if (clazz == short.class) {
                return (T) Short.valueOf((short) 0);
            } else if (clazz == byte.class) {
                return (T) Byte.valueOf((byte) 0);
            } else if (clazz == float.class) {
                return (T) Float.valueOf(0);
            } else if (clazz == double.class) {
                return (T) Double.valueOf(0);
            } else if (clazz == boolean.class) {
                return (T) Boolean.FALSE;
            }
            return null;
        }

        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        }

        if (clazz == obj.getClass()) {
            return (T) obj;
        }

        if (obj instanceof String) {
            return JSON.parseObject((String) obj, clazz);
        } else {
            String data = JSON.toJSONString(obj);
            return JSON.parseObject(data, clazz);
        }
    }

    public Object get(Object key) {
        Object val = map.get(key);

        if (val == null && key instanceof Number) {
            val = map.get(key.toString());
        }

        return val;
    }

    public Boolean getBoolean(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return castToBoolean(value);
    }

    public boolean getBooleanValue(String key) {
        Object value = get(key);

        Boolean booleanVal = castToBoolean(value);
        if (booleanVal == null) {
            return false;
        }

        return booleanVal.booleanValue();
    }

    public Short getShort(String key) {
        Object value = get(key);

        return castToShort(value);
    }

    public short getShortValue(String key) {
        Object value = get(key);

        Short shortVal = castToShort(value);
        if (shortVal == null) {
            return 0;
        }

        return shortVal.shortValue();
    }

    public Integer getInteger(String key) {
        Object value = get(key);

        return castToInt(value);
    }

    public int getIntValue(String key) {
        Object value = get(key);

        Integer intVal = castToInt(value);
        if (intVal == null) {
            return 0;
        }

        return intVal.intValue();
    }

    public Long getLong(String key) {
        Object value = get(key);

        return castToLong(value);
    }

    public long getLongValue(String key) {
        Object value = get(key);

        Long longVal = castToLong(value);
        if (longVal == null) {
            return 0L;
        }

        return longVal.longValue();
    }

    public Float getFloat(String key) {
        Object value = get(key);

        return castToFloat(value);
    }

    public float getFloatValue(String key) {
        Object value = get(key);

        Float floatValue = castToFloat(value);
        if (floatValue == null) {
            return 0F;
        }

        return floatValue.floatValue();
    }

    public Double getDouble(String key) {
        Object value = get(key);

        return castToDouble(value);
    }

    public double getDoubleValue(String key) {
        Object value = get(key);

        Double doubleValue = castToDouble(value);
        if (doubleValue == null) {
            return 0D;
        }

        return doubleValue.doubleValue();
    }

    public BigDecimal getBigDecimal(String key) {
        Object value = get(key);

        return castToBigDecimal(value);
    }

    public BigInteger getBigInteger(String key) {
        Object value = get(key);

        return castToBigInteger(value);
    }

    public String getString(String key) {
        Object value = get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    public JSONObject fluentPut(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public void putAll(Map<? extends String, ? extends Object> m) {
        map.putAll(m);
    }

    public JSONObject fluentPutAll(Map<? extends String, ? extends Object> m) {
        map.putAll(m);
        return this;
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public Collection<Object> values() {
        return map.values();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return map.entrySet();
    }

    public void clear() {
        map.clear();
    }

    public String toJSONString() {
        return JSON.toJSONString(this.map);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        return JSON.parseObject(text, typeReference);
    }

    @Override
    public String toString() {
        return this.toJSONString();
    }

}

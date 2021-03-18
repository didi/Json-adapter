package com.didi.middleware.json.adapter;

import com.didi.middleware.json.adapter.model.Animal;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONTest {

    @Test
    public void testToJSONString() {
        Animal dog = new Animal();
        dog.setName("Dog");
        Assert.assertEquals("{\"name\":\"Dog\"}", JSON.toJSONString(dog));

        Map<String, Object> map = new HashMap<>();
        map.put("dog", dog);
        JSONObject jsonObject = new JSONObject(map);
        Assert.assertEquals("{\"dog\":{\"name\":\"Dog\"}}", JSON.toJSONString(jsonObject));

        List<Object> list = new ArrayList<>();
        list.add(dog);
        JSONArray jsonArray = new JSONArray(list);
        Assert.assertEquals("[{\"name\":\"Dog\"}]", JSON.toJSONString(jsonArray));
    }

    @Test
    public void testParseObjectType() {
        String text = "[{\"name\":\"Dog\"}]";
        List<Animal> animals = JSON.parseObject(text, new TypeReference<List<Animal>>() {
        });
        Assert.assertEquals(ArrayList.class,animals.getClass());
        Assert.assertEquals(Animal.class,animals.get(0).getClass());
        Assert.assertEquals("Dog",animals.get(0).getName());
    }

    @Test
    public void testToJSONBytes() {

    }


    @Test
    public void testParseObject() {
        Assert.assertEquals(Animal.class, JSON.parseObject("{\"name\":\"Dog\"}", Animal.class).getClass());
        Assert.assertEquals("Dog", JSON.parseObject("{\"name\":\"Dog\"}", Animal.class).getName());
    }

    @Test
    public void testParseArrayClass() {
        String text = "[{\"name\":\"Dog\"}, {\"name\":\"Cat\"}]";
        Assert.assertEquals(2, JSON.parseArray(text, Animal.class).size());
        Assert.assertEquals(Animal.class, JSON.parseArray(text, Animal.class).get(0).getClass());

        Assert.assertEquals("Dog", JSON.parseArray(text, Animal.class).get(0).getName());
    }

    @Test
    public void testParseArray() {
        String text = "[{\"name\":\"Dog\"},{\"name\":\"Cat\"}]";
        JSONArray jsonArray = JSON.parseArray(text);
        Assert.assertEquals(2, jsonArray.getList().size());
        Assert.assertEquals(text, jsonArray.toJSONString());
    }

    @Test
    public void testParseJsonNode() {
        Assert.assertEquals("Dog", JSON.parseJsonNode("{\"name\":\"Dog\"}").get("name").asText());
    }

    @Test
    public void testParseJsonObject() {
        Assert.assertEquals("Dog", JSON.parseObject("{\"name\":\"Dog\"}").get("name"));
    }

    @Test
    public void testParseObjectMap() {
        Map<String, Object> map = JSON.parseObjectMap("{\"name\":\"Dog\"}");
        Assert.assertEquals("Dog", map.get("name"));
        map.remove("name");
        Assert.assertTrue(map.isEmpty());

    }

    @Test
    public void testParseObjectList() {
        Assert.assertEquals(2, JSON.parseObjectList("[{\"name\":\"Dog\"}, {\"name\":\"Cat\"}]").size());
    }

    @Test
    public void testJsonIgnore() {
        Animal dog = new Animal();
        dog.setName("Dog");
        dog.setLegs(4);
        Assert.assertEquals("{\"name\":\"Dog\"}", JSON.toJSONString(dog));
    }


    @Test
    public void testParseBase() {
        String nullStr = null;
        Assert.assertNull(JSON.parseObject(nullStr));
        Assert.assertNull(com.alibaba.fastjson.JSON.parseObject(nullStr));

        //json-adapter当前不支持parse方法，不支持转换 布尔，number，字符串
        String trueStr = "true";
        Object trueParse = com.alibaba.fastjson.JSON.parse(trueStr);
        Assert.assertTrue(trueParse instanceof Boolean);
        Assert.assertEquals(trueStr, trueParse.toString());

        String intStr = "1111";
        Object intParse = com.alibaba.fastjson.JSON.parse(intStr);
        Assert.assertTrue(intParse instanceof Integer);
        Assert.assertEquals(intStr, intParse.toString());

        String text = "\"text\"";
        Object textParse = com.alibaba.fastjson.JSON.parse(text);
        Assert.assertTrue(textParse instanceof String);
        Assert.assertTrue(text.contains(textParse.toString()));

    }

}

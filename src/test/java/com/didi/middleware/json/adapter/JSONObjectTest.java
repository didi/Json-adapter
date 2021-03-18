package com.didi.middleware.json.adapter;

import com.didi.middleware.json.adapter.model.Animal;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JSONObjectTest {

    @Test
    public void testGetJSONList() {
        String text = "{\n" +
                "\t\"key\": [\n" +
                "\t{\"name\":\"Dog\"},{\"name\":\"Cat\"}\n" +
                "\t]\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(text);
        Assert.assertEquals(2, jsonObject.getJSONList("key", Animal.class).size());
    }

    @Test
    public void testGetInteger() {
        String text = "{\"key\":1}";
        JSONObject body = JSON.parseObject(text);
        Assert.assertEquals(new Integer(1), body.getInteger("key"));
        Assert.assertEquals(1, body.getIntValue("key"));
    }

    @Test
    public void testParseObjectType() {
        String text = "[{\"name\":\"Dog\"}]";
        List<Animal> animals = JSONObject.parseObject(text, new TypeReference<List<Animal>>() {
        });
        Assert.assertEquals(ArrayList.class,animals.getClass());
        Assert.assertEquals(Animal.class,animals.get(0).getClass());
        Assert.assertEquals("Dog",animals.get(0).getName());
    }

    @Test
    public void testParseObject() {
        String text = "{\"name\":\"Dog\"}";
        Animal animal = JSONObject.parseObject(text, Animal.class);
        Assert.assertEquals(Animal.class,animal.getClass());
        Assert.assertEquals("Dog",animal.getName());
    }


}

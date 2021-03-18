package com.didi.middleware.json.adapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.didi.middleware.json.adapter.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class RegressTest {
    @Test
    public void testJSONArray() {
        String data = "[{\"predicateItems\":[{\"pattern\":\"apiPattern\"}]}]";
        Set<ApiDefinition> apiDefinitions = new HashSet<>();
        com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSON.parseArray(data);
        for (Object obj : array) {
            com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) obj;
            ApiDefinition apiDefinition = new ApiDefinition((o.getString("apiName")));
            Set<ApiPredicateItem> predicateItems = new HashSet<>();
            com.alibaba.fastjson.JSONArray itemArray = o.getJSONArray("predicateItems");
            if (itemArray != null) {
                predicateItems.addAll(itemArray.toJavaList(ApiPathPredicateItem.class));
            }
            apiDefinition.setPredicateItems(predicateItems);
            apiDefinitions.add(apiDefinition);
        }


        Set<ApiDefinition> apiDefinitionsN = new HashSet<>();
        List<Object> arrayN = com.didi.middleware.json.adapter.JSON.parseArray(data, Object.class);
        for (Object obj : arrayN) {
            com.didi.middleware.json.adapter.JSONObject o = new com.didi.middleware.json.adapter.JSONObject((Map) obj);
            ApiDefinition apiDefinition = new ApiDefinition((o.getString("apiName")));
            Set<ApiPredicateItem> predicateItems = new HashSet<>();
            List<ApiPathPredicateItem> itemArray = o.getJSONList("predicateItems", ApiPathPredicateItem.class);
            if (itemArray != null) {
                predicateItems.addAll(itemArray);
            }
            apiDefinition.setPredicateItems(predicateItems);
            apiDefinitionsN.add(apiDefinition);
        }

        Assert.assertEquals(apiDefinitions.size(), apiDefinitionsN.size());

        ApiDefinition[] apiDefinitionArr = new ApiDefinition[10];
        apiDefinitions.toArray(apiDefinitionArr);
        ApiDefinition[] apiDefinitionArrN = new ApiDefinition[10];
        apiDefinitionsN.toArray(apiDefinitionArrN);

        Assert.assertEquals(apiDefinitionArr[0].getPredicateItems().size(),
                apiDefinitionArrN[0].getPredicateItems().size());

        ApiPathPredicateItem[] apiPredicateItemArr = new ApiPathPredicateItem[10];
        apiDefinitionArr[0].getPredicateItems().toArray(apiPredicateItemArr);

        ApiPathPredicateItem[] apiPredicateItemArrN = new ApiPathPredicateItem[10];
        apiDefinitionArrN[0].getPredicateItems().toArray(apiPredicateItemArrN);
        Assert.assertEquals(apiPredicateItemArr[0].getPattern(), apiPredicateItemArrN[0].getPattern());
    }

    @Test
    public void testJSONObject() {
        Animal dog = new Animal();
        dog.setName("Dog");

        com.alibaba.fastjson.JSONObject config = new com.alibaba.fastjson.JSONObject()
                .fluentPut("dog", dog);

        Map<String, Object> map = new HashMap<>();
        map.put("dog", dog);

        Assert.assertEquals(config.toJSONString(), com.didi.middleware.json.adapter.JSON.toJSONString(map));
    }

    @Test
    public void testParseArray2() {
        String text = "[{\"name\":\"Dog\"}, {\"name\":\"Cat\"}]";
        List<Animal> animals = com.alibaba.fastjson.JSONArray.parseArray(text, Animal.class);

        List<Animal> animalsN = com.didi.middleware.json.adapter.JSONArray.parseArray(text, Animal.class);

        Assert.assertEquals(animals.size(), animalsN.size());
        Assert.assertEquals(animals.get(0).getClass(), animalsN.get(0).getClass());
        Assert.assertEquals(animals.get(0).getName(), animalsN.get(0).getName());
    }

    @Test
    public void testToJSONBytes() {
        Animal dog = new Animal();
        dog.setName("Dog");
        byte[] bytes = com.alibaba.fastjson.JSON.toJSONBytes(dog);
        byte[] bytesN = com.didi.middleware.json.adapter.JSON.toJSONBytes(dog);

        Assert.assertEquals(bytes.length, bytesN.length);

        for (int i = 0; i < bytes.length; i++) {
            Assert.assertEquals(bytes[i], bytesN[i]);
        }
    }

    @Test
    public void testHumpKey() {
        String jsonStr = "{\"service_name\":\"provider-demo.arch.automarket.didi.com\"}";
        Animal fastDog = com.alibaba.fastjson.JSON.parseObject(jsonStr, Animal.class);
        Animal jackDog = com.didi.middleware.json.adapter.JSON.parseObject(jsonStr, Animal.class);
        System.out.println(fastDog.getServiceName());
        System.out.println(jackDog.getServiceName());
    }

    @Test
    public void testFeature() {
        Animal dog = new Animal();
        dog.setName("Dog");
        dog.setNum(1);
        Map<Object, Object> map = new HashMap<>();
        map.put("aa", null);
        map.put(1, "bb");
        dog.setMap(map);
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(dog, SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.WriteMapNullValue));
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(dog));
        System.out.println("------jackson");
        System.out.println(com.didi.middleware.json.adapter.JSON.toJSONString(dog));

//        JSON.parseObject(JSON.toJSONString(oilValue),

    }

    @Test
    public void testKey() {
        String json= "{\"bBT\":\"aaa\"}";
//        TestV testV = com.alibaba.fastjson.JSONObject.parseObject(json, TestV.class);
        System.out.println("------fastjson");
    }

    @Test
    public void testParseEmpty() {
        String jsonStrNull = null;
        com.alibaba.fastjson.JSONObject fastObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStrNull);
        Assert.assertNull(fastObject);
        JSONObject jsonObject = JSONObject.parseObject(jsonStrNull);
        Assert.assertNull(jsonObject);


        String jsonStrEmpty = "";
        fastObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStrEmpty);
        Assert.assertNull(fastObject);
        jsonObject = JSONObject.parseObject(jsonStrEmpty);
        Assert.assertNull(jsonObject);


        String jsonStrWhite = "   ";
        fastObject = com.alibaba.fastjson.JSONObject.parseObject(jsonStrWhite);
        Assert.assertNull(fastObject);
        jsonObject = JSONObject.parseObject(jsonStrWhite);
        Assert.assertNull(jsonObject);
    }

    @Test
    public void testEmpty() {
        String jsonStrWhite = "   ";
        Animal animal;
        animal = com.alibaba.fastjson.JSON.parseObject(jsonStrWhite, Animal.class);
        Assert.assertNull(animal);
        animal = JSON.parseObject(jsonStrWhite, Animal.class);
        Assert.assertNull(animal);

        Map map = JSON.parseObjectMap(jsonStrWhite);
        Assert.assertNull(map);

        List list = JSON.parseObjectList(jsonStrWhite);
        Assert.assertNull(list);

        List<Animal> animalList = JSON.parseArray(jsonStrWhite, Animal.class);
        Assert.assertNull(animalList);
        animalList = com.alibaba.fastjson.JSON.parseArray(jsonStrWhite, Animal.class);
        Assert.assertNull(animalList);
    }

}

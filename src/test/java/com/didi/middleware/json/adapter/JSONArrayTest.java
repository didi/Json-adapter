package com.didi.middleware.json.adapter;

import com.didi.middleware.json.adapter.model.Animal;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JSONArrayTest {

    @Test
    public void testGetJSONList() {
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArrayDefault = new JSONArray(10);

        List<Object> list = new ArrayList<>(5);
        JSONArray jsonArrayList = new JSONArray(list);

        Assert.assertEquals(16, getArrayListCapacity((ArrayList<?>) jsonArray.getList()));
        Assert.assertEquals(10, getArrayListCapacity((ArrayList<?>) jsonArrayDefault.getList()));
        Assert.assertEquals(5, getArrayListCapacity((ArrayList<?>) jsonArrayList.getList()));
    }

    @Test
    public void testAdd() {
        JSONArray jsonArray = new JSONArray();
        Animal animal = new Animal();
        animal.setName("Dog");
        jsonArray.add(animal);

        Assert.assertEquals("[{\"name\":\"Dog\"}]", jsonArray.toJSONString());
        Assert.assertEquals("[{\"name\":\"Dog\"}]", jsonArray.toString());
        Assert.assertEquals("[{\"name\":\"Dog\"}]", JSONArray.toJSONString(jsonArray));
    }

    public static int getArrayListCapacity(ArrayList<?> arrayList) {
        Class<ArrayList> arrayListClass = ArrayList.class;
        try {
            Field field = arrayListClass.getDeclaredField("elementData");
            field.setAccessible(true);
            Object[] objects = (Object[]) field.get(arrayList);
            return objects.length;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -1;
        }
    }

}

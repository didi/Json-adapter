package com.didi.middleware.json.adapter;

import org.junit.Assert;
import org.junit.Test;

public class SerializerTest {
    @Test
    public void testJSONObject() {
        JSONObject inner = new JSONObject();
        inner.put("k", "v");

        JSONObject outer = new JSONObject();
        outer.put("inner", inner);

        Assert.assertEquals("{\"inner\":{\"k\":\"v\"}}",JSON.toJSONString(outer));
    }

    @Test
    public void testJSONArray() {
        JSONArray inner = new JSONArray();
        inner.add("v");

        JSONObject outer = new JSONObject();
        outer.put("inner", inner);

        Assert.assertEquals("{\"inner\":[\"v\"]}",JSON.toJSONString(outer));
    }
}

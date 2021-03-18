package com.didi.middleware.json.adapter.serializer;

import com.didi.middleware.json.adapter.JSONArray;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class JSONArraySerializer extends JsonSerializer<JSONArray> {
    @Override
    public void serialize(JSONArray value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeObject(value.getList());
        }
    }
}

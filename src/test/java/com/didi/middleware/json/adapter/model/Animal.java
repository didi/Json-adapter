package com.didi.middleware.json.adapter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Map;

@Data
public class Animal {
    private String name;

    private String serviceName;

    private Integer num;

    private Map<Object, Object> map;

    @JsonIgnore
    private Integer legs;

    public boolean equals(Animal animal) {
        return animal != null && this.name.equals(animal.getName());
    }


}

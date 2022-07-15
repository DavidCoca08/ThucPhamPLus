package com.example.myapplication;

import java.util.HashMap;
import java.util.Map;

public class Model {
    int id;
    String name;

    public Model() {
    }

    public Model(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object > reslut = new HashMap<>();
        reslut.put("name",name);
        return reslut;
    }
}

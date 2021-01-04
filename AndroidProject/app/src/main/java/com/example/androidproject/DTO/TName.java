package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class TName {
    @SerializedName("name")
    private String name;

    @Override
    public String toString() {
        return "TName{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

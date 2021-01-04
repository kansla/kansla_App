package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class TName {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;

    @Override
    public String toString() {
        return "TName{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

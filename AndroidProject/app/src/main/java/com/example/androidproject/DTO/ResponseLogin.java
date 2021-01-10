package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("name")
    private String name;
    @SerializedName("birth")
    private String birth;
    @SerializedName("status_msg")
    private String status_msg;
    @SerializedName("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                ", status_msg='" + status_msg + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

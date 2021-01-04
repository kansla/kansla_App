package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRoomDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("count")
    private int count;
    @SerializedName("msg")
    private List<Msg> msg;
    @SerializedName("name")
    private List<TName> name;

    public ChatRoomDTO(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ChatRoomDTO{" +
                "email='" + email + '\'' +
                ", count=" + count +
                ", msg=" + msg +
                ", name=" + name +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Msg> getMsg() {
        return msg;
    }

    public void setMsg(List<Msg> msg) {
        this.msg = msg;
    }

    public List<TName> getTName() {
        return name;
    }

    public void setTName(List<TName> name) {
        this.name = name;
    }
}

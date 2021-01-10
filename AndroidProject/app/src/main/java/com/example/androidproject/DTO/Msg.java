package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class Msg {
    @SerializedName("room_id")
    private int id;
    @SerializedName("last_msg")
    private String msg;

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

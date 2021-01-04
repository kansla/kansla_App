package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("status_msg")
    private String status_msg;

    @Override
    public String toString() {
        return "Room{" +
                "status_msg='" + status_msg + '\'' +
                '}';
    }

    public String getStatus_msg() {
        return status_msg;
    }

    public void setStatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }
}

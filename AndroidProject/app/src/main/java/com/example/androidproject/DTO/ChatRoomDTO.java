package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRoomDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("count")
    private int count;
    @SerializedName("friend")
    private List<Room> rooms;

    public ChatRoomDTO(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ChatRoomDTO{" +
                "email='" + email + '\'' +
                ", count=" + count +
                ", rooms=" + rooms +
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

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}

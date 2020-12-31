package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FriendsDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("count")
    private int count;
    @SerializedName("friend")
    private List<Friend> friends;

    @Override
    public String toString() {
        return "FriendsDTO{" +
                "email='" + email + '\'' +
                ", count=" + count +
                ", friends=" + friends +
                '}';
    }

    public FriendsDTO(String email) {
        this.email = email;
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

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }


}

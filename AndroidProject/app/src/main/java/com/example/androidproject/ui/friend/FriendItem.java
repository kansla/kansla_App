package com.example.androidproject.ui.friend;

import com.google.gson.annotations.SerializedName;

public class FriendItem {
    @SerializedName("userId")
    private String name;
    @SerializedName("title")
    private String contents;
    @SerializedName("id")
    private byte[] data;
    @SerializedName("body")
    private String body;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

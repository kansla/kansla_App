package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class AddFriend {
    @SerializedName("first_email")
    private String first_email;
    @SerializedName("second_email")
    private String second_email;

    public AddFriend(String first_email, String second_email) {
        this.first_email = first_email;
        this.second_email = second_email;
    }

    @Override
    public String toString() {
        return "AddFriend{" +
                "first_email='" + first_email + '\'' +
                ", second_email='" + second_email + '\'' +
                '}';
    }

    public String getFirst_email() {
        return first_email;
    }

    public void setFirst_email(String first_email) {
        this.first_email = first_email;
    }

    public String getSecond_email() {
        return second_email;
    }

    public void setSecond_email(String second_email) {
        this.second_email = second_email;
    }
}

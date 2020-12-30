package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class UserModifyDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("pwd")
    private String pwd;
    @SerializedName("birth")
    private String birth;
    @SerializedName("base64")
    private String base64;
    @SerializedName("contents")
    private String contenst;
}

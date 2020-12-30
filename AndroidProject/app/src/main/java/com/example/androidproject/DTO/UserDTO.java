package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class UserDTO {
    @SerializedName("email")
    private String email;
    @SerializedName("pwd")
    private String pwd;
    @SerializedName("name")
    private String name;
    @SerializedName("birth")
    private String birth;

    public UserDTO(String email, String pwd, String name, String birth) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birth = birth;
    }

    public UserDTO(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

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

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}

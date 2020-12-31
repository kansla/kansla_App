package com.example.androidproject.DTO;

import com.google.gson.annotations.SerializedName;

public class UserModifyDTO {
    @SerializedName("origin_email")
    private String ori_email;
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
    @SerializedName("status_msg")
    private String status_msg;

    public UserModifyDTO(String ori_email, String email, String name, String pwd, String birth, String base64, String status_msg) {
        this.ori_email = ori_email;
        this.email = email;
        this.name = name;
        this.pwd = pwd;
        this.birth = birth;
        this.base64 = base64;
        this.status_msg = status_msg;
    }

    public String getOri_email() {
        return ori_email;
    }

    public void setOri_email(String ori_email) {
        this.ori_email = ori_email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getstatus_msg() {
        return status_msg;
    }

    public void setstatus_msg(String status_msg) {
        this.status_msg = status_msg;
    }

    @Override
    public String toString() {
        return "UserModifyDTO{" +
                "ori_email='" + ori_email + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", birth='" + birth + '\'' +
                ", base64='" + base64 + '\'' +
                ", status_msg='" + status_msg + '\'' +
                '}';
    }
}

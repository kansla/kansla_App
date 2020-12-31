package com.example.androidproject.API;

import com.example.androidproject.DTO.ResponseLogin;
import com.example.androidproject.DTO.UserDTO;
import com.example.androidproject.DTO.UserModifyDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/register")
    Call<UserDTO> signup(@Body UserDTO userDTO);
    @POST("/login")
    Call<ResponseLogin> login(@Body UserDTO userDTO);
    @POST("/modify")
    Call<UserModifyDTO> modify(@Body UserModifyDTO userModifyDTO);
    @POST("/register/check_email")
    Call<UserDTO> check_email(@Body UserDTO userDTO);
}

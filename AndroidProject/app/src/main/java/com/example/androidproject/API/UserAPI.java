package com.example.androidproject.API;

import com.example.androidproject.DTO.UserDTO;
import com.example.androidproject.DTO.UserModifyDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI {
    @POST("/register")
    Call<UserDTO> signup(@Body UserDTO userDTO);
    @POST("/login")
    Call<UserDTO> login(@Body UserDTO userDTO);
    @POST("/modify")
    Call<UserModifyDTO> modify(@Body UserModifyDTO userModifyDTO);
}

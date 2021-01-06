package com.example.androidproject.API;

import com.example.androidproject.DTO.AddFriend;
import com.example.androidproject.DTO.ChatRoomDTO;
import com.example.androidproject.DTO.FriendsDTO;
import com.example.androidproject.DTO.LoadMsgDTO;
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
    @POST("/friend/friend_list")
    Call<FriendsDTO> friend_list(@Body FriendsDTO email);
    @POST("/chat_room")
    Call<ChatRoomDTO> chat_room(@Body ChatRoomDTO chatRoomDTO);
    @POST("/password")
    Call<UserDTO> find_password(@Body UserDTO userDTO);
    @POST("/chat_load")
    Call<LoadMsgDTO> chat_load(@Body LoadMsgDTO loadMsgDTO);
    @POST("/friend/friend_req")
    Call<AddFriend> friend_add(@Body AddFriend addFriend);
    @POST("/chat_start")
    Call<AddFriend> chat_start(@Body AddFriend addFriend);
}

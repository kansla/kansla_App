package com.example.androidproject.API;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @GET("/posts/")
    Call<Posts[]> getPosts();
    @POST("posts")
    Call<Posts> createPost(@Body Posts posts);
    /*@FormUrlEncoded
    @POST("Posts")
    Call<Posts> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );*/
    @FormUrlEncoded
    @POST("Posts")
    Call<Posts> createPost(@FieldMap Map<String, String> fields);
}

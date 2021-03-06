package com.example.androidproject.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static final String API_URL="http://1c83c8ff4a50.ngrok.io/";
    public static UserAPI getApiService(){return  getInstance().create(UserAPI.class);}

    private static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setDateFormat("HH:mm").setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}

package com.example.syrus_media_mobile.Service_Api;

import com.example.syrus_media_mobile.Models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
        @POST("SignUpController")
        Call<ResponseBody> createUser(@Body User user);
        @POST("Login_user")
        Call<ResponseBody> loginUser(@Body User user);
}


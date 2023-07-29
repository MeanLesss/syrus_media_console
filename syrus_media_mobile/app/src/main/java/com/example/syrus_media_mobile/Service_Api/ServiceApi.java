package com.example.syrus_media_mobile.Service_Api;

import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.Models.Video;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceApi {
        @POST("SignUpController")
        Call<ResponseBody> createUser(@Body User user);
        @POST("Login_user")
        Call<ResponseBody> loginUser(@Body User user);
        @GET("getAllVideo")
        Call<ResponseBody> getAllVideo(@Query("user_id") int user_id);

}


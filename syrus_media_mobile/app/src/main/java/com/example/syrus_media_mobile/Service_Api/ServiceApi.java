package com.example.syrus_media_mobile.Service_Api;

import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.Models.Video;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ServiceApi {
        @POST("SignUpController")
        Call<ResponseBody> createUser(@Body User user);
        @POST("Login_user")
        Call<ResponseBody> loginUser(@Body User user);
        @GET("VideoController")
        Call<ResponseBody> getAllVideo(@Query("user_id") int user_id);

        @Multipart
        @POST("VideoController")
        Call<ResponseBody> upload(
                @Part MultipartBody.Part filePart,
                @Part("title") RequestBody title,
                @Part("description") RequestBody description,
                @Part("file_path") RequestBody file_path,
                @Part("thumbnail_path") RequestBody thumbnail_path,
                @Part("genre") RequestBody genre,
                @Part("user_id") RequestBody user_id
        );

}


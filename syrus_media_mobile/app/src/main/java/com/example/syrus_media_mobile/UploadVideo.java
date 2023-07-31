package com.example.syrus_media_mobile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.syrus_media_mobile.Models.User;
import com.example.syrus_media_mobile.Service_Api.ServiceApi;

import java.io.File;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadVideo extends AppCompatActivity {
    private static final int PICK_VIDEO_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int REQUEST_CODE = 1;
    private static final String BASE_URL = Global_var.FULL_PATH_URL;

    private Uri videoUri;
    private Uri imageUri;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText genreEditText;
    private ActivityResultLauncher<Intent> pickVideoLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        genreEditText = findViewById(R.id.genreEditText);

        pickVideoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    videoUri = result.getData().getData();
                    // TODO: Display a preview of the selected video
                }
            }
        });

        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    imageUri = result.getData().getData();
                    // TODO: Display a preview of the selected image
                }
            }
        });

        Button pickVideoButton = findViewById(R.id.pickVideoButton);
        pickVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickVideo();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        Button pickImageButton = findViewById(R.id.pickImageButton);
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        Button uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    private void pickVideo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        pickVideoLauncher.launch(intent);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }
    private void upload() {
        user = (User) getIntent().getSerializableExtra("loggedUser");
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String genre = genreEditText.getText().toString();
        String userId = String.valueOf(user.getID());

        // Get the URIs of the selected video and image
        Uri videoUri = this.videoUri;
        Uri imageUri = this.imageUri;

        // Get the paths of the selected video and image
        String videoPath = getPathFromUri(this, videoUri);
        String imagePath = getPathFromUri(this, imageUri);

        // Create a Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create a service instance
        ServiceApi service = retrofit.create(ServiceApi.class);

        // Create RequestBody instances for the title, description, file path, thumbnail path, genre, and user ID
        RequestBody titlePart = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), description);

        RequestBody filePathPart = RequestBody.create(MediaType.parse("text/plain"), videoPath);

        RequestBody thumbnailPathPart = RequestBody.create(MediaType.parse("text/plain"), imagePath);
        RequestBody genrePart = RequestBody.create(MediaType.parse("text/plain"), genre);
        RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), userId);

        // Create a MultipartBody.Part instance for the video
        File videoFile = new File(videoPath);
        RequestBody videoRequestBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("file", videoFile.getName(), videoRequestBody);

        // Call the upload method on the service instance
        Call<ResponseBody> call = service.upload(videoPart, titlePart, descriptionPart, filePathPart, thumbnailPathPart, genrePart, userIdPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    // For example, you can display a success message or update the UI
                    Log.e("Upload","Upload Success : " + response.raw());
                    Toast.makeText(UploadVideo.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle unsuccessful response
                    // For example, you can display an error message or update the UI
                    Toast.makeText(UploadVideo.this, "Upload failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failed request
                // For example, you can display an error message or update the UI
                Log.e("Upload","Upload error : " + t.getMessage());
                Toast.makeText(UploadVideo.this, "Upload failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private String getPathFromUri(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

}
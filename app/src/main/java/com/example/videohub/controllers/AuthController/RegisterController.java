package com.example.videohub.controllers.AuthController;

import android.content.Context;
import android.net.Uri;

import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.videohub.models.User;
import com.example.videohub.providers.BusinessServiceProvider;
import com.example.videohub.services.interfaces.ICloudinaryService;
import com.example.videohub.services.interfaces.IRegisterService;
import com.example.videohub.utils.Callback;

import java.util.Map;

public class RegisterController {
    private final IRegisterService registerService;
    private final ICloudinaryService cloudinaryService;

    public RegisterController(Context context) {
        this.registerService = BusinessServiceProvider.provideRegisterService(context);
        this.cloudinaryService = BusinessServiceProvider.provideCloudinaryServices(context);
    }

    public void registerSystemAccount(User user, Callback<Boolean> callback) {
        registerService.registerSystemAccount(user, new Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    callback.onSuccess(true);
                } else {
                    callback.onError(new Exception("Registration failed"));
                }
            }

            @Override
            public void onError(Exception e) {
                callback.onError(e);
            }
        });
    }

    public void uploadImageToCloudinary(Uri imageUri, Callback<String> callback) {
        cloudinaryService.uploadImageFileToCloudinary(imageUri, new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                // Optionally, show a progress dialog
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                // Track progress if necessary
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String imageUrl = resultData.get("url").toString();
                if (!imageUrl.startsWith("https://")) {
                    imageUrl = imageUrl.replace("http://", "https://");
                }
                callback.onSuccess(imageUrl);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                callback.onError(new Exception(error.getDescription()));
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                // Handle rescheduling if needed
            }
        });
    }
}

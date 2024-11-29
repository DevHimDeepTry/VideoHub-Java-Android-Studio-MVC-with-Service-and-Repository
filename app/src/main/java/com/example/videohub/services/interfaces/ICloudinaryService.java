package com.example.videohub.services.interfaces;

import android.net.Uri;

import com.cloudinary.android.callback.UploadCallback;

public interface ICloudinaryService {
    void uploadImageFileToCloudinary(Uri imageUri, UploadCallback callback);
    void uploadVideoFileToCloudinary(Uri imageUri, UploadCallback callback);
}
